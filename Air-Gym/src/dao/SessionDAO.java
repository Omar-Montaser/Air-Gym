package dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import model.gym.members.Session;
import utils.SqlServerConnect;

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
            Connection conn = SqlServerConnect.getConnection();
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
    public void updateSession(Session session) throws SQLException{
        Connection conn = SqlServerConnect.getConnection();
        String sql = "EXEC UpdateSession ?, ?, ?, ?, ?, ?, ?, ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, session.getSessionID());
            if (session.getTrainerID() != null) {
                stmt.setInt(2, session.getTrainerID());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            if (session.getBranchID() != null) {
                stmt.setInt(3, session.getBranchID());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }
            if (session.getSessionType() != null) {
                stmt.setString(4, session.getSessionType());
            } else {
                stmt.setNull(4, java.sql.Types.VARCHAR);
            }
            if (session.getMaxCapacity() != null) {
                stmt.setInt(5, session.getMaxCapacity());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            if (session.getDateTime() != null) {
                stmt.setTimestamp(6, session.getDateTime());
            } else {
                stmt.setNull(6, java.sql.Types.TIMESTAMP);
            }
            if (session.getDuration() != null) {
                stmt.setInt(7, session.getDuration());
            } else {
                stmt.setNull(7, java.sql.Types.INTEGER);
            }
            if (session.getStatus() != null) {
                stmt.setString(8, session.getStatus());
            } else {
                stmt.setNull(8, java.sql.Types.VARCHAR);
            }
            stmt.executeUpdate();
    }
    public void createSession(Session session) throws SQLException {
        Connection conn = SqlServerConnect.getConnection();
        String sql = "EXEC AddSession "
                   + "@TrainerID = ?, "
                   + "@BranchID = ?, "
                   + "@SessionType = ?, "
                   + "@MaxCapacity = ?, "
                   + "@DateTime = ?, "
                   + "@Duration = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, session.getTrainerID());
        stmt.setInt(2, session.getBranchID());
        stmt.setString(3, session.getSessionType());
        stmt.setInt(4, session.getMaxCapacity());
        stmt.setTimestamp(5, session.getDateTime());
        stmt.setInt(6, session.getDuration());
        
        stmt.executeQuery();
    }
    public void deleteSession(int sessionId) {
        try {
            String query = "EXEC DeleteSession @SessionID = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, sessionId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getMaxSessionId() {
        String sql = "SELECT MAX(SessionID) FROM Session";
        try (Connection conn = SqlServerConnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}