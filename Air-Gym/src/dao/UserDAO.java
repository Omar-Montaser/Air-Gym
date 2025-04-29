package dao;

import java.sql.*;

public class UserDAO {
    private Connection conn;
    
    public UserDAO(Connection conn) {
        this.conn = conn;
    }
    
    public String validateLogin(String phoneNumber, String password) {
        String sql = "SELECT Role FROM Users WHERE PhoneNumber = ? AND Password = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            pstmt.setString(2, password);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Role");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public int getUserIdByPhoneNumber(String phoneNumber) {
        String sql = "SELECT UserID FROM Users WHERE PhoneNumber = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("UserID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return -1;
    }

    public boolean userExists(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM Users WHERE PhoneNumber = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    public model.accounts.User getUserById(int userId) {
        String sql = "SELECT UserID, FirstName, LastName, Password, PhoneNumber, Gender, DateOfBirth, Role " +
                     "FROM Users WHERE UserID = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new model.accounts.User(
                        rs.getInt("UserID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Password"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Gender"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("Role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE Users SET Password = ? WHERE UserID = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, userId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
