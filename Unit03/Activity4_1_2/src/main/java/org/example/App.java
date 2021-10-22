package org.example;

import java.sql.*;

public class App {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/" + "VTInstitue";
            String user = "postgres";
            String pwd = "0023";
            con = DriverManager.getConnection(url, user, pwd);
            Statement statement = con.createStatement();
            String insert = "INSERT INTO subjects(name, year) VALUES('Markup Languages', 1)";
            statement.execute(insert);
            String SQLsentence = "SELECT * FROM subjects ORDER BY code";
            ResultSet rs = statement.executeQuery(SQLsentence);
            System.out.println("Code" + "\t" + "Name" + "\t\t\t\t\t\t\t" + "Year");
            System.out.println("---------------------------------------------");
            while (rs.next()) {
                System.out.printf("%s \t %-30s \t %s \n", rs.getString(1), rs.getString(2), rs.getString(3));
            }
            rs.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load PostgreSQL Driver.");
        } catch (SQLException e) {
            System.out.println("Connection couldn't be establisehd.");
        } finally {
            if (con != null)
                con.close();
        }
    }
}