package dao;

import java.sql.Connection;
import java.sql.SQLException;

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
        MemberDAO memberDAO = new MemberDAO();
        int maxUserId = memberDAO.getMaxUserId(conn);
        // Create a new Member object with the necessary parameters
        Member member = new Member(
            maxUserId,                // userId (incremented from max)
            "John",                       // firstName
            "Doe",                        // lastName
            "password123",                // password
            "1234567890",                 // phoneNumber
            "Male",                       // gender
            java.sql.Date.valueOf("1990-01-01"), // dateOfBirth
            1,                            // membershipId
            1,                            // branchId
            9,                            // trainerId
            "1990-01-01",                 // subscriptionStartDate
            "1990-01-01",                 // subscriptionEndDate
            0,                            // sessionsAvailable
            "Active",                     // subscriptionStatus
            0,                            // freezeAvailable
            java.sql.Date.valueOf("1990-01-01") // freezeEndDate
        );
        // memberDAO.createMember(conn, member, "Cash", 12);
        // member.setFirstName("Jane");
        // memberDAO.updateMember(conn, member);

    }
}
