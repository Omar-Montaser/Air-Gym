package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.SqlServerConnect;
import model.accounts.Member;

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
        // int maxUserId = memberDAO.getMaxUserId();
        // Create a new Member object with the necessary parameters
        // Member member = new Member(
        //     maxUserId,                // userId (incremented from max)
        //     "TEST23",                       // firstName
        //     "Doe",                        // lastName
        //     "password123",                // password
        //     "1234567890",                 // phoneNumber
        //     "Male",                       // gender
        //     java.sql.Date.valueOf("1990-01-01"), // dateOfBirth
        //     1,                            // membershipId
        //     1,                            // branchId
        //     9,                            // trainerId
        //     "1990-01-01",                 // subscriptionStartDate
        //     "1990-01-01",                 // subscriptionEndDate
        //     0,                            // sessionsAvailable
        //     "Active",                     // subscriptionStatus
        //     0,                            // freezeAvailable
        //     java.sql.Date.valueOf("1990-01-01") // freezeEndDate
        // );
        // memberDAO.createMember(member, "Cash",12);
        // member.setFirstName("Jane");
        // memberDAO.updateMember(conn, member);
        // List<Member> members = memberDAO.getAllMembers();
        // for (Member m : members) {
        //     System.out.println(m.getFirstName());
        // }
        // Member member = memberDAO.getMemberByPhoneNumber("01055667788");
        // System.out.println(member.getFirstName());
        // System.out.println(member.getLastName());
        Member member = memberDAO.getMemberById(56);
        memberDAO.freezeSubscription(member.getUserId(),1);
        
    }
}
