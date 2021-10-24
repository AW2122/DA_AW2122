package org.activity4_1_1;

import java.sql.*;

public class Activity4_1_1 {
    static final String url = "jdbc:postgresql://localhost:5432/" + "VTInstitue";
    static final String user = "postgres";
    static final String pwd = "0023";
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String SQLsentence = "SELECT * FROM subjects ORDER BY code";
        try (Connection con = DriverManager.getConnection(url, user, pwd); Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(SQLsentence)) {
            System.out.printf("%s \t %-30s %s \n", "Code", "Name", "Year");
            System.out.println("---------------------------------------------");
            while (rs.next()) {
                System.out.printf("%s \t %-30s \t %s \n", rs.getString(1), rs.getString(2), rs.getString(3));
            }
        }
    }
}
