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
            PreparedStatement pstmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS courses (code SERIAL PRIMARY KEY, name VARCHAR(90))");
            pstmt.executeUpdate();
            pstmt = con.prepareStatement("INSERT INTO courses (name) VALUES (?)");
            pstmt.setString(1, "Multiplatform App Development");
            pstmt.executeUpdate();
            pstmt.setString(1, "Web Development");
            pstmt.executeUpdate();
        }
    }
}
