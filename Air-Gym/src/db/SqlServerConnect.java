package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlServerConnect {
    public static void main(String[] args) {
        String url = 
        "jdbc:sqlserver://localhost\\OMAR-MONTASER:1433;databaseName=AirGym;encrypt=true;trustServerCertificate=true";
        String user = "Montaser";
        String password = "Montaser1";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
