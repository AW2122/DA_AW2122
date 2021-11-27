package org.example;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitue";
    static final String user = "postgres";
    static final String pwd = "0023";
    PreparedStatement pstmt = null;

    public ArrayList<String> getData(String sqlStatement) {
        ArrayList<String> idList = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(url, user, pwd);
            pstmt = conn.prepareStatement(sqlStatement);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                idList.add(rs.getString(1));
            }
            return idList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idList;
    }

    private void InsertStudent(Connection conn, Student student) throws SQLException {
        pstmt = conn.prepareStatement("INSERT INTO student (firstname, lastname, idcard, email, phone) VALUES (?, ?, ?, ?, ?)");
        pstmt.setString(1, student.getFirstName());
        pstmt.setString(2, student.getLastName());
        pstmt.setString(3, student.getIdCard());
        pstmt.setString(4, student.getEmail());
        pstmt.setString(5, student.getPhone());
        pstmt.executeUpdate();
    }

    public int addStudent(Student student) {
        try {
            Connection conn = DriverManager.getConnection(url, user, pwd);
            InsertStudent(conn, student);
            conn.close();
            return 0;
        } catch (SQLException e) {
            int errorCode = Integer.parseInt(e.getSQLState());
            return errorCode;
        }
    }

    /**
     * This method
     * @param studentId
     * @param courseCode
     */
    public void enrollStudent(String studentId, int courseCode) {
        try {
            int enrollmentId = 0;
            Connection conn = DriverManager.getConnection(url, user, pwd);
            pstmt = conn.prepareStatement("INSERT INTO enrollment (student, course) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, studentId);
            pstmt.setInt(2, courseCode);
            pstmt.executeUpdate();
            ResultSet keys = pstmt.getGeneratedKeys();
            if (keys.next()) {
                enrollmentId = keys.getInt(1);
            }
            pstmt = conn.prepareStatement("INSERT INTO scores (enrollmentid, subjectid, score) " +
                    "SELECT e.code, subjects.code, 0 FROM subjects INNER JOIN courses c on c.code = subjects.course " +
                    "INNER JOIN enrollment e on c.code = e.course WHERE e.code = " + enrollmentId + " " +
                    "AND e.course = " + courseCode);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method executes a select query to obtain a specific student's scores,
     * with its corresponding course and subject name.
     * @param studentId is the identification code of the student, of type String
     * @return a String containing the select query result
     */
    public String GetScores (String studentId) {
        try {
            String result = "";
            Connection conn = DriverManager.getConnection(url, user, pwd);
            pstmt = conn.prepareStatement("select format('%s - %s: %s \n', c.name, s.name, score) " +
                    "from scores inner join subjects s on s.code = scores.subjectid " +
                    "inner join enrollment e on e.code = scores.enrollmentid " +
                    "inner join courses c on c.code = e.course WHERE student = '" + studentId + "';"); // uwu
            ResultSet rs = pstmt.executeQuery();
            conn.close();
            while (rs.next()) {
                result += rs.getString(1);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method executes the SQL transaction from importing an XML file. All inserts must be successfully completed
     * for the transaction to take place.
     * @param studentList a list of objects of type Student received from XMLReader
     * @param courseList a list of objects of type Course received from XMLReader
     * @param subjectList a list of objects of type Subjects received from XMLReader
     * @return an integer: 0 if transaction is successful, SQL error code otherwise.
     * @throws SQLException
     */
    public int XMLTransaction (ArrayList<Student> studentList, ArrayList<Course> courseList,
                                ArrayList<Subject> subjectList) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            conn.setAutoCommit(false);
            for (Student student: studentList) {
                InsertStudent(conn, student);
            }
            for (Course course: courseList) {
                pstmt = conn.prepareStatement("INSERT INTO courses (code, name) VALUES (?, ?)");
                pstmt.setInt(1, course.getId());
                pstmt.setString(2, course.getName());
                pstmt.executeUpdate();
            }
            for (Subject subject: subjectList) {
                pstmt = conn.prepareStatement("INSERT INTO subjects (code, name, year, hours, course) VALUES (?, ?, ?, ?, ?)");
                pstmt. setInt(1, subject.getId());
                pstmt. setString(2, subject.getName());
                pstmt. setInt(3, subject.getYear());
                pstmt. setInt(4, subject.getHours());
                pstmt. setInt(5, subject.getCourseId());
                pstmt.executeUpdate();
            }
            conn.commit();
            conn.close();
            return 0;
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
            int errorCode = Integer.parseInt(e.getSQLState());
            return errorCode;
        }
    }

}
