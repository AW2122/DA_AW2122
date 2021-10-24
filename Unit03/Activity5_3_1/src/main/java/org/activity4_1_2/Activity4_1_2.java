package org.activity4_1_2;

import java.sql.*;

public class Activity4_1_2 {
    static final String url = "jdbc:postgresql://localhost:5432/" + "VTInstitue";
    static final String user = "postgres";
    static final String pwd = "0023";
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection con = DriverManager.getConnection(url, user, pwd); Statement statement = con.createStatement()) {
            String insert = "INSERT INTO subjects(name, year) VALUES('Markup Languages', 1)";
            statement.execute(insert);
        }
    }
}