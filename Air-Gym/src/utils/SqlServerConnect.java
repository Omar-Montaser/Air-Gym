package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlServerConnect {
    // Database connection details
    private static final String URL = "jdbc:sqlserver://localhost\\OMAR-MONTASER:1433;databaseName=AirGym2;encrypt=true;trustServerCertificate=true";
    private static final String USER = "Montaser";
    private static final String PASSWORD = "Montaser1";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}