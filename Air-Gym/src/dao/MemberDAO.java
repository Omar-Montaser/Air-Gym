package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.accounts.Member;

public class MemberDAO {
    public boolean createMember(Connection conn, Member member, String paymentMethod, int duration) {
        String sql = "EXEC AddNewMember ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            
            // Set parameters for the stored procedure
            cstmt.setString(1, paymentMethod);
            cstmt.setInt(2, duration);
            cstmt.setString(3, member.getPassword());
            cstmt.setString(4, member.getFirstName());
            cstmt.setString(5, member.getLastName());
            cstmt.setString(6, member.getPhoneNumber());
            cstmt.setString(7, member.getGender());
            cstmt.setDate(8, member.getBirthDate());
            cstmt.setInt(9, member.getMembershipId());
            cstmt.setInt(10, member.getBranchId());
            cstmt.setInt(11, member.getTrainerId());

            cstmt.execute();
            return true;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public int getMaxUserId(Connection conn){
        String sql = "USE AirGym; SELECT MAX(UserID) FROM Users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {  // Always call rs.next() first!
                    return rs.getInt(1);
                } else {
                    return 0; // no rows
                }
        }
        catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    public boolean updateMember(Connection conn, Member member){
    
        String sql = "EXEC UpdateMember ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            // Set parameters for the stored procedure
            cstmt.setInt(1, member.getUserId());
            cstmt.setString(2, member.getFirstName());
            cstmt.setString(3, member.getLastName());
            cstmt.setString(4, member.getPhoneNumber());
            cstmt.setString(5, member.getGender());
            cstmt.setDate(6, member.getBirthDate());
            cstmt.setInt(7, member.getMembershipId());
            cstmt.setString(8, member.getSubscriptionStartDate());
            cstmt.setString(9, member.getSubscriptionEndDate());
            cstmt.setInt(10, member.getSessionsAvailable());
            cstmt.setString(11, member.getSubscriptionStatus());
            cstmt.setInt(12, member.getTrainerId());
            cstmt.setInt(13, member.getBranchId());
            cstmt.setInt(14, member.getFreezeAvailable());
            cstmt.setDate(15, member.getFreezeEndDate());

            cstmt.execute();
            return true;
        } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    public boolean deleteMember(Connection conn, String userId) {
        String sql = "EXEC DeleteUser ?";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, userId);
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean extendSubscription(Connection conn, String userId, int duration, String paymentMethod) {
        String sql = "EXEC ExtendSubscription ?, ?, ?";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, userId);
            cstmt.setInt(2, duration);
            cstmt.setString(3, paymentMethod);
            
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean renewSubscription(Connection conn, String userId,int duration, int membershipTypeId, String paymentMethod) {
        String sql = "EXEC RenewSubscription ?, ?, ?, ?";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, userId);
            cstmt.setInt(2, duration);
            cstmt.setInt(3, membershipTypeId);
            cstmt.setString(4, paymentMethod);
            
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }    
    }
    public boolean cancelSubscription(Connection conn, String userId) {
        String sql = "EXEC CancelSubscription ?";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, userId);
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateMemberStatus(Connection conn, String userId, String status) {
        String sql = "EXEC UpdateMemberStatus";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    
    }
    public boolean freezeSubscription(Connection conn, String userId, int duration) {
        String sql = "EXEC FreezeSubscription ?, ?";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, userId);
            cstmt.setInt(2, duration);
            
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public Member getMemberById(Connection conn, int userId) {
        String sql = "SELECT u.UserID, u.FirstName, u.LastName, u.Password, u.PhoneNumber, u.Gender, u.DateOfBirth, " +
                     "m.MembershipTypeID, m.BranchID, m.TrainerID, m.SubscriptionStartDate, m.SubscriptionEndDate, " +
                     "m.SessionsAvailable, m.SubscriptionStatus, m.FreezesAvailable, m.FreezeEndDate " +
                     "FROM Users u JOIN Member m ON u.UserID = m.UserID WHERE u.UserID = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Member member = new Member(
                    rs.getInt("UserID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Password"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Gender"),
                    rs.getDate("DateOfBirth"),
                    rs.getInt("MembershipTypeID"),
                    rs.getInt("BranchID"),
                    rs.getInt("TrainerID"),
                    rs.getDate("SubscriptionStartDate").toString(),
                    rs.getDate("SubscriptionEndDate").toString(),
                    rs.getInt("SessionsAvailable"),
                    rs.getString("SubscriptionStatus"),
                    rs.getInt("FreezesAvailable"),
                    rs.getDate("FreezeEndDate")
                );
                return member;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Member> getAllMembers(Connection conn) {
        String sql = "SELECT * FROM Member";
        List<Member> members = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                members.add(mapResultSetToMember(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    private Member mapResultSetToMember(ResultSet rs) throws SQLException {
        return new Member(
            rs.getInt("UserID"),
            rs.getString("FirstName"),
            rs.getString("LastName"),
            rs.getString("Password"),
            rs.getString("PhoneNumber"),
            rs.getString("Gender"),
            rs.getDate("DateOfBirth"),
            rs.getInt("MembershipTypeID"),
            rs.getInt("BranchID"),
            rs.getInt("TrainerID"),
            rs.getDate("SubscriptionStartDate").toString(),
            rs.getDate("SubscriptionEndDate").toString(),
            rs.getInt("SessionsAvailable"),
            rs.getString("SubscriptionStatus"),
            rs.getInt("FreezesAvailable"),
            rs.getDate("FreezeEndDate")
        );
    }
}
//     Get a member by ID
//     public Member getMemberById(String userId) {
//         String sql = "SELECT * FROM Member WHERE user_id = ?";
        
//         try (Connection conn = SqlServerConnect.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
//             pstmt.setString(1, userId);
//             ResultSet rs = pstmt.executeQuery();
            
//             if (rs.next()) {
//                 return mapResultSetToMember(rs);
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return null;
//     }

//     // Get all members
//     public List<Member> getAllMembers() {
//         List<Member> members = new ArrayList<>();
//         String sql = "SELECT * FROM Member";
        
//         try (Connection conn = SqlServerConnect.getConnection();
//              Statement stmt = conn.createStatement();
//              ResultSet rs = stmt.executeQuery(sql)) {
            
//             while (rs.next()) {
//                 members.add(mapResultSetToMember(rs));
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//         return members;
//     }

//     // Update a member
//     public boolean updateMember(Member member) {
//         String sql = "UPDATE Member SET first_name = ?, last_name = ?, password = ?, " +
//                     "phone_number = ?, gender = ?, date_of_birth = ?, join_date = ?, " +
//                     "membership_type_id = ?, branch_id = ?, trainer_id = ?, " +
//                     "subscription_start_date = ?, subscription_end_date = ?, " +
//                     "sessions_available = ?, subscription_status = ? WHERE user_id = ?";
        
//         try (Connection conn = SqlServerConnect.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
//             pstmt.setString(1, member.getFirstName());
//             pstmt.setString(2, member.getLastName());
//             pstmt.setString(3, member.getPassword());
//             pstmt.setString(4, member.getPhoneNumber());
//             pstmt.setString(5, member.getGender());
//             pstmt.setDate(6, member.getBirthDate());
//             pstmt.setDate(7, member.getJoinDate());
//             pstmt.setInt(8, member.getMembershipId());
//             pstmt.setInt(9, member.getBranchId());
//             pstmt.setObject(10, member.getTrainerId());
//             pstmt.setDate(11, java.sql.Date.valueOf(member.getSubscriptionStartDate()));
//             pstmt.setDate(12, java.sql.Date.valueOf(member.getSubscriptionEndDate()));
//             pstmt.setInt(13, member.getSessionsAvailable());
//             pstmt.setString(14, member.getSubscriptionStatus());
//             pstmt.setString(15, member.getUserId());
            
//             return pstmt.executeUpdate() > 0;
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     // Delete a member
//     public boolean deleteMember(String userId) {
//         String sql = "DELETE FROM Member WHERE user_id = ?";
        
//         try (Connection conn = SqlServerConnect.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
//             pstmt.setString(1, userId);
//             return pstmt.executeUpdate() > 0;
//         } catch (SQLException e) {
//             e.printStackTrace();
//             return false;
//         }
//     }

//     // Helper method to map ResultSet to Member object
//     private Member mapResultSetToMember(ResultSet rs) throws SQLException {
//         return new Member(
//             rs.getString("user_id").substring(1), // Remove 'M' prefix  
//             rs.getString("first_name"),
//             rs.getString("last_name"),
//             rs.getString("password"),
//             rs.getString("phone_number"),
//             rs.getString("gender"),
//             rs.getDate("date_of_birth"),
//             rs.getDate("join_date"),
//             rs.getInt("membership_type_id"),
//             rs.getInt("branch_id"),
//             rs.getObject("trainer_id", Integer.class),
//             "Credit Card", // Default payment method
//             rs.getDate("subscription_start_date").toString(),
//             rs.getDate("subscription_end_date").toString(),
//             rs.getInt("sessions_available"),
//             rs.getString("subscription_status")
//         );
//     }
// } 