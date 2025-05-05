package dao;

import java.sql.*;
import java.sql.PreparedStatement;

import model.accounts.User;

public class UserDAO {
    private Connection conn;
    
    public UserDAO(Connection conn) {
        this.conn = conn;
    }
    public User getUserByPhoneNumber(String phoneNumber){
        String sql = "SELECT * FROM Users WHERE PhoneNumber = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
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
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
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
    public int getMaxUserId(){
        String sql = "SELECT MAX(UserID) FROM Users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) return rs.getInt(1);
                else return 0; 
        }
        catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    public boolean deleteUser(int userId) {
        String sql = "EXEC DeleteUser ?";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, userId);
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
