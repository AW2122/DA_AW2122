package org.example;

import java.sql.*;
import java.util.Scanner;

public class App {
    static final String url = "jdbc:postgresql://localhost:5432/" + "VTInstitue";
    static final String user = "postgres";
    static final String pwd = "0023";
    private static Scanner sc = new Scanner(System.in);
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection con = DriverManager.getConnection(url, user, pwd); Statement statement = con.createStatement();
             PreparedStatement pstmt = con.prepareStatement("INSERT INTO subjects (name, year, hours) VALUES (?, ?, ?)")) {
            String name = null;
            String year = null;
            String hours = null;
            do {
                System.out.println("Please enter the subject name: ");
                name = sc.nextLine();

                if (!name.equals("exit")) {
                    System.out.println("Please enter the grade the subject is taught in: ");
                    year = sc.nextLine();
                    System.out.println("Enter the amount of hours the subject has: ");
                    hours = sc.nextLine();

                    pstmt.setString(1, name);
                    pstmt.setInt(2, Integer.parseInt(year));
                    pstmt.setInt(3, Integer.parseInt(hours));
                    pstmt.executeUpdate();
                }
            } while (!name.equals("exit"));
        }
    }
}