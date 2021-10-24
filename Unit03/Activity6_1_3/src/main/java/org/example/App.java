package org.example;

import java.sql.*;
import java.util.Scanner;

public class App {
    static final String url = "jdbc:postgresql://localhost:5432/" + "VTInstitue";
    static final String user = "postgres";
    static final String pwd = "0023";
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection con = DriverManager.getConnection(url, user, pwd); Statement statement = con.createStatement()) {
            PreparedStatement pstmt = con.prepareStatement("ALTER TABLE subjects ADD COLUMN IF NOT EXISTS course INT");
            pstmt.executeUpdate();
            pstmt = con.prepareStatement("UPDATE subjects SET course = ?");
            pstmt.setInt(1, 2);
            pstmt.executeUpdate();
            pstmt = con.prepareStatement("ALTER TABLE subjects ADD FOREIGN KEY(course) REFERENCES courses(code)");
            pstmt.executeUpdate();
        }
    }
}