package org.activity4_1_3;

import java.sql.*;

public class Activity4_1_3 {
    static final String url = "jdbc:postgresql://localhost:5432/" + "VTInstitue";
    static final String user = "postgres";
    static final String pwd = "0023";
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        try (Connection con = DriverManager.getConnection(url, user, pwd); Statement statement = con.createStatement()) {
            statement.execute("ALTER TABLE subjects ADD COLUMN hours INT");
        }
    }
}