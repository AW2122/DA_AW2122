package org.example;
import java.sql.*;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws ClassNotFoundException, SQLException {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/" + "VTInstitue";
            String user = "postgres";
            String pwd = "0023";
            con = DriverManager.getConnection(url, user, pwd);
            Statement statement = con.createStatement();
            statement.execute("ALTER TABLE subjects ADD COLUMN hours INT");
            statement.close();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Cannot load PostgreSQL Driver.");
        }
        catch (SQLException e) {
            System.out.println("Connection couldn't be establisehd.");
        }
        finally {
            if (con != null)
                con.close();
        }
    }
}
