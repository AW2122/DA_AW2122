package org.example;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitue";
    static final String user = "postgres";
    static final String pwd = "0023";
    PreparedStatement pstmt = null;

    /**
     * This method retrieves data from that database and stores them in a list.
     * @param sqlStatement the SQL statement that you wish to execute (should be a SELECT).
     * @return an arraylist of type String.
     */
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

    /**
     * This method creates a prepared statement to insert a student into the database.
     * @param conn recives a connection that has already been established (this is to ensure that the method
     *             XMLTransaction is executed as a whole using a single connection).
     * @param student the object Student to be inserted.
     * @throws SQLException
     */
    private void insertStudent(Connection conn, Student student) throws SQLException {
        pstmt = conn.prepareStatement("INSERT INTO student (firstname, lastname, idcard, email, phone) VALUES (?, ?, ?, ?, ?)");
        pstmt.setString(1, student.getFirstName());
        pstmt.setString(2, student.getLastName());
        pstmt.setString(3, student.getIdCard());
        pstmt.setString(4, student.getEmail());
        pstmt.setString(5, student.getPhone());
        pstmt.executeUpdate();
    }

    /**
     * This method creates a connection to insert a student, calling the method InsertStudent.
     * @param student object Student.
     * @return the SQL error code if the execution were to fail and 0 if there are no issues.
     */
    public int addStudent(Student student) {
        try {
            Connection conn = DriverManager.getConnection(url, user, pwd);
            insertStudent(conn, student);
            conn.close();
            return 0;
        } catch (SQLException e) {
            int errorCode = Integer.parseInt(e.getSQLState());
            return errorCode;
        }
    }

    /**
     * This method executes a query that inserts the specified student into a specific course.
     * @param studentId the id code of the student to enroll.
     * @param courseCode the course in which the student will be enrolled.
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
     * @return a String containing the select query result.
     */
    public String getScores(String studentId) {
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
    public int xmlTransaction(ArrayList<Student> studentList, ArrayList<Course> courseList,
                              ArrayList<Subject> subjectList) throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            conn.setAutoCommit(false);
            for (Student student: studentList) {
                insertStudent(conn, student);
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