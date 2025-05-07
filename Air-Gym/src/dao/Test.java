package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import model.accounts.Member;
import model.accounts.Trainer;
import model.gym.members.Session;
import utils.SqlServerConnect;
import java.sql.Date;


public class Test {
    public static void main(String[] args) {
        Connection conn = null;
        try{
        conn = SqlServerConnect.getConnection();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    MemberDAO memberDAO = new MemberDAO(conn);
    UserDAO userDAO = new UserDAO(conn);
    // Member member = new Member(
    //     userDAO.getMaxUserId()+1, 
    //     "John", 
    //     "Doe", 
    //     "password123", 
    //     "1234567890", 
    //     "Male", 
    //     Date.valueOf("1990-01-01"), 
    //     1, 
    //     1, 
    //     null,                      // trainerId
    //     Date.valueOf("2023-01-01"), // subscriptionStartDate
    //     Date.valueOf("2024-01-01"), // subscriptionEndDate
    //     12,                        // sessionsAvailable
    //     "Active",                  // subscriptionStatus
    //     0,                         // freezeAvailable
    //     null                       // freezeEndDate
    // );

    // int duration = 12;
    // double paymentAmount = 120.00;

    // try {
    //     memberDAO.createMember(member, duration, paymentAmount);
    //     System.out.println("Member created successfully.");
    // } catch (Exception e) {
    //     e.printStackTrace();
    // }
    Member member = memberDAO.getMemberById(25);
    // Step 5: Check the result
    if (member != null) {
        System.out.println("✅ Member Retrieved:");
        System.out.println("User ID: " + member.getUserId());
        System.out.println("First Name: " + member.getFirstName());
        System.out.println("Last Name: " + member.getLastName());
        System.out.println("Phone Number: " + member.getPhoneNumber());
        System.out.println("Gender: " + member.getGender());
        System.out.println("DOB: " + member.getBirthDateAsString());
        System.out.println("Membership Type ID: " + member.getMembershipId());
        System.out.println("Branch ID: " + member.getBranchId());
        System.out.println("Trainer ID: " + member.getTrainerId());
        System.out.println("Subscription Start: " + member.getSubscriptionStartDate());
        System.out.println("Subscription End: " + member.getSubscriptionEndDate());
        System.out.println("Sessions Available: " + member.getSessionsAvailable());
        System.out.println("Subscription Status: " + member.getSubscriptionStatus());
        System.out.println("Freezes Available: " + member.getFreezeAvailable());
        System.out.println("Freeze End Date: " + member.getFreezeEndDate());
    } else {
        System.out.println("❌ No member found for user ID " + 25);
    }
    
    }
}