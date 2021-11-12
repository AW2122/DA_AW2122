package org.example;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    static final String url = "jdbc:postgresql://localhost:5432/VTInstitue";
    static final String user = "postgres";
    static final String pwd = "0023";
    PreparedStatement pstmt = null;

    /*
    public ArrayList<String> getData(String table, int index, String condition, String column) {
        ArrayList<String> idList = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(url, user, pwd);
            if (condition.equals(""))
                pstmt = conn.prepareStatement("SELECT * FROM " + table);
            else {
                pstmt = conn.prepareStatement("SELECT * FROM " + table + " WHERE " + column + " = ?");
                pstmt.setString(1, condition);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                idList.add(rs.getString(index));
            }
            return idList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idList;
    }
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

    public int addStudent(Student student) {
        try {
            Connection conn = DriverManager.getConnection(url, user, pwd);
            pstmt = conn.prepareStatement("INSERT INTO student (firstname, lastname, idcard, email, phone) VALUES (?, ?, ?, ?, ?)");
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getIdCard());
            pstmt.setString(4, student.getEmail());
            pstmt.setString(5, student.getPhone());
            pstmt.executeUpdate();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String GetScores (String studentId) {
        try {
            String result = "";
            Connection conn = DriverManager.getConnection(url, user, pwd);
            pstmt = conn.prepareStatement("select format('%s - %s: %s', c.name, s.name, score) " +
                    "from scores inner join subjects s on s.code = scores.subjectid " +
                    "inner join enrollment e on e.code = scores.enrollmentid " +
                    "inner join courses c on c.code = e.course WHERE student = '" + studentId + "';"); // uwu
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result += rs.getString(1) + "\n";
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
