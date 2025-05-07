package dao;
import java.sql.Connection;
import java.sql.SQLException;

import model.accounts.Member;
import utils.SqlServerConnect;

public class Test {
        public static void main(String[] args) {
            Connection conn = null;
            try {
                conn = SqlServerConnect.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            // Test MemberDAO
            MemberDAO memberDAO = new MemberDAO(conn);
            Member member = memberDAO.getMemberById(25);
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
            
            // New tests for AdminDAO
            AdminService adminDAO = new AdminService(conn);
            System.out.println("✅ AdminDAO Tests:");
            System.out.println("Active Members Count: " + adminDAO.countActiveMembers());
            System.out.println("All Members Count: " + adminDAO.countAllMembers());
            System.out.println("Active Trainers Count: " + adminDAO.countActiveTrainers());
            System.out.println("All Trainers Count: " + adminDAO.countAllTrainers());
            System.out.println("All Admins Count: " + adminDAO.countAllAdmins());
            System.out.println("All Branches Count: " + adminDAO.countAllBranches());
            System.out.println("All Equipment Count: " + adminDAO.countAllEquipment());
            System.out.println("Available Equipment Count: " + adminDAO.countAllAvailableEquipment());
            System.out.println("Most Dense Branch ID: " + adminDAO.getMostDenseBranch());
            System.out.println("Best Income Branch ID: " + adminDAO.getBestIncomeBranch());
            System.out.println("Worst Income Branch ID: " + adminDAO.getWorstIncomeBranch());
            System.out.println("Best Selling Membership ID: " + adminDAO.getBestSellingMembership());
        }
}