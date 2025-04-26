package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlServerConnect {
    // Database connection details
    private static final String URL = "jdbc:sqlserver://localhost\\OMAR-MONTASER:1433;databaseName=AirGym;encrypt=true;trustServerCertificate=true";
    private static final String USER = "Montaser";
    private static final String PASSWORD = "Montaser1";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void testQuery(String query) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next())
                System.out.println("Row: " + rs.getString(1)); 
        } catch (SQLException e) {
            System.err.println("Query failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Connection successful!");
            }
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }

        String testQuery = "SELECT TOP 5 * FROM AirGym"; // Replace with your table name
        testQuery(testQuery);
    }
}
