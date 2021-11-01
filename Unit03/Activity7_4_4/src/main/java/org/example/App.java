package org.example;

import java.sql.*;
import java.util.Scanner;

public class App
{
    static final Scanner sc = new Scanner(System.in);
    static final String url = "jdbc:postgresql://localhost:5432/Employees";
    static final String user = "postgres";
    static final String pwd = "0023";
    public static int MainMenu() {
        String input = "";
        System.out.println(">>> MENU <<<");
        System.out.println("[1] List all employees that do a specific job");
        System.out.println("[2] List all employees that belong to a specific department");
        System.out.println("[3] List employees whose name matches the pattern");
        System.out.println("[0] Exit");
        do {
            System.out.println("Please enter an option [0-3]");
            input = sc.nextLine();
        } while(!input.matches("[0-3]"));
        return Integer.parseInt(input);
    }
    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {
        try (Connection conn = DriverManager.getConnection(url, user, pwd)) {
            CallableStatement stmnt;
            ResultSet rs;
            String input = "";
            do {
                switch (MainMenu()) {
                    case 1:
                        System.out.println("Please enter the search term: ");
                        input = sc.nextLine();
                        stmnt = conn.prepareCall("SELECT employeelist('"+ input +"')");
                        rs = stmnt.executeQuery();
                        while (rs.next()) {
                            String[] result = rs.getString(1).split(",");
                            System.out.printf("%-7s %-7s %-10s %-7s \n", "ID", "Name", "Jobname", "Dept. Num.");
                            System.out.printf("%-7s %-7s %-10s %-7s \n", result[0], result[1], result [2], result [3]);
                        }
                        break;
                    case 2:
                        System.out.println("Please enter the search term: ");
                        input = sc.nextLine();
                        stmnt = conn.prepareCall("SELECT departmentSearch('"+ input +"')");
                        rs = stmnt.executeQuery();
                        while (rs.next()) {
                            String[] result = rs.getString(1).split(",");
                            System.out.printf("%-12s %-12s %-10s \n", "Dept. Num.", "Dept. Name", "Location");
                            System.out.println(result[0] + "\t" + result[1] + "\t" + result [2]);
                        }
                        break;
                    case 3:
                        System.out.println("Please enter the search term: ");
                        input = sc.nextLine();
                        stmnt = conn.prepareCall("SELECT namePattern('"+ input +"')");
                        rs = stmnt.executeQuery();
                        while (rs.next()) {
                            String[] result = rs.getString(1).split(",");
                            System.out.printf("%-7s %-7s %-10s %-7s \n", "ID", "Name", "Jobname", "Dept. Num.");
                            System.out.printf("%-7s %-7s %-10s %-7s \n", result[0], result[1], result [2], result [3]);
                        }
                        break;
                    case 0:
                        break;
                }
            } while (MainMenu() != 0);

        }
    }
}
