package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;             
import java.sql.*;

import model.gym.members.Session;

public class SessionDAO {
    private Connection conn;
    public SessionDAO(Connection conn){
        this.conn = conn;
    }
    public Session getEarliestSessionByType(String sessionType) {
        Session session = null;
        try {
            String query = "SELECT * FROM GetEarliestSessionByType(?)";
            java.sql.PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, sessionType);
            java.sql.ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                session = new Session(
                    resultSet.getInt("SessionID"),
                    resultSet.getInt("TrainerID"),
                    resultSet.getInt("BranchID"),
                    resultSet.getString("SessionType"),
                    resultSet.getInt("MaxCapacity"),
                    resultSet.getTimestamp("DateTime"),
                    resultSet.getInt("Duration"),
                    resultSet.getString("Status")
                );
                session.setTrainerName(resultSet.getString("TrainerName"));
                session.setBranchName(resultSet.getString("BranchName"));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return session;
    }
    public Session getSessionByID(int sessionID) {
        Session session = null;
        try {
            String query = "SELECT s.SessionID, s.TrainerID, s.BranchID, s.SessionType, s.MaxCapacity, " +
                           "s.DateTime, s.Duration, s.Status, " +
                           "u.FirstName + ' ' + u.LastName AS TrainerName, " +
                           "b.Name AS BranchName " +
                           "FROM Session s " +
                           "INNER JOIN Users u ON s.TrainerID = u.UserID " +
                           "INNER JOIN Branch b ON s.BranchID = b.BranchID " +
                           "WHERE s.SessionID = ?";
            java.sql.PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, sessionID);
            java.sql.ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                session = new Session(
                    resultSet.getInt("SessionID"),
                    resultSet.getInt("TrainerID"),
                    resultSet.getInt("BranchID"),
                    resultSet.getString("SessionType"),
                    resultSet.getInt("MaxCapacity"),
                    resultSet.getTimestamp("DateTime"),
                    resultSet.getInt("Duration"),
                    resultSet.getString("Status")
                );
                session.setTrainerName(resultSet.getString("TrainerName"));
                session.setBranchName(resultSet.getString("BranchName"));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return session;
    }
    public void updateSessionStatus(int sessionID, String status) {
        String sql = "EXEC UpdateSessionStatus";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.executeUpdate();   
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Session> getAllSessions() {
        List<Session> sessions = new ArrayList<>();
        try {
            String query = "SELECT * FROM Session";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Session session = new Session(
                    resultSet.getInt("SessionID"),
                    resultSet.getInt("TrainerID"),
                    resultSet.getInt("BranchID"),
                    resultSet.getString("SessionType"),
                    resultSet.getInt("MaxCapacity"),
                    resultSet.getTimestamp("DateTime"),
                    resultSet.getInt("Duration"),
                    resultSet.getString("Status")
                );
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

}
