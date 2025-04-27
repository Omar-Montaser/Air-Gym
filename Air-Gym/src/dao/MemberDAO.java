package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import db.SqlServerConnect;
import model.accounts.Member;

public class MemberDAO {
    // Create a new member
    public boolean createMember(Member member) {
        String sql = "INSERT INTO Member (user_id, first_name, last_name, password, phone_number, " +
                    "gender, date_of_birth, join_date, membership_type_id, branch_id, trainer_id, " +
                    "subscription_start_date, subscription_end_date, sessions_available, subscription_status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = SqlServerConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, member.getUserId());
            pstmt.setString(2, member.getFirstName());
            pstmt.setString(3, member.getLastName());
            pstmt.setString(4, member.getPassword());
            pstmt.setString(5, member.getPhoneNumber());
            pstmt.setString(6, member.getGender());
            pstmt.setDate(7, member.getBirthDate());
            pstmt.setDate(8, member.getJoinDate());
            pstmt.setInt(9, member.getMembershipId());
            pstmt.setInt(10, member.getBranchId());
            pstmt.setObject(11, member.getTrainerId());
            pstmt.setDate(12, java.sql.Date.valueOf(member.getSubscriptionStartDate()));
            pstmt.setDate(13, java.sql.Date.valueOf(member.getSubscriptionEndDate()));
            pstmt.setInt(14, member.getSessionsAvailable());
            pstmt.setString(15, member.getSubscriptionStatus());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get a member by ID
    public Member getMemberById(String userId) {
        String sql = "SELECT * FROM Member WHERE user_id = ?";
        
        try (Connection conn = SqlServerConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToMember(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all members
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM Member";
        
        try (Connection conn = SqlServerConnect.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                members.add(mapResultSetToMember(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    // Update a member
    public boolean updateMember(Member member) {
        String sql = "UPDATE Member SET first_name = ?, last_name = ?, password = ?, " +
                    "phone_number = ?, gender = ?, date_of_birth = ?, join_date = ?, " +
                    "membership_type_id = ?, branch_id = ?, trainer_id = ?, " +
                    "subscription_start_date = ?, subscription_end_date = ?, " +
                    "sessions_available = ?, subscription_status = ? WHERE user_id = ?";
        
        try (Connection conn = SqlServerConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, member.getFirstName());
            pstmt.setString(2, member.getLastName());
            pstmt.setString(3, member.getPassword());
            pstmt.setString(4, member.getPhoneNumber());
            pstmt.setString(5, member.getGender());
            pstmt.setDate(6, member.getBirthDate());
            pstmt.setDate(7, member.getJoinDate());
            pstmt.setInt(8, member.getMembershipId());
            pstmt.setInt(9, member.getBranchId());
            pstmt.setObject(10, member.getTrainerId());
            pstmt.setDate(11, java.sql.Date.valueOf(member.getSubscriptionStartDate()));
            pstmt.setDate(12, java.sql.Date.valueOf(member.getSubscriptionEndDate()));
            pstmt.setInt(13, member.getSessionsAvailable());
            pstmt.setString(14, member.getSubscriptionStatus());
            pstmt.setString(15, member.getUserId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete a member
    public boolean deleteMember(String userId) {
        String sql = "DELETE FROM Member WHERE user_id = ?";
        
        try (Connection conn = SqlServerConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to map ResultSet to Member object
    private Member mapResultSetToMember(ResultSet rs) throws SQLException {
        return new Member(
            rs.getString("user_id").substring(1), // Remove 'M' prefix
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("password"),
            rs.getString("phone_number"),
            rs.getString("gender"),
            rs.getDate("date_of_birth"),
            rs.getDate("join_date"),
            rs.getInt("membership_type_id"),
            rs.getInt("branch_id"),
            rs.getObject("trainer_id", Integer.class),
            "Credit Card", // Default payment method
            rs.getDate("subscription_start_date").toString(),
            rs.getDate("subscription_end_date").toString(),
            rs.getInt("sessions_available"),
            rs.getString("subscription_status")
        );
    }
} 