package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import db.SqlServerConnect;
import model.accounts.Member;
import model.gym.members.Session;

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
        List<Member> members = memberDAO.getAllMemberDetails();
        if (members.isEmpty()) {
            System.out.println("The members list is empty. Check if data exists in the database.");
        } else {
            System.out.println("All Members: " + members.size() + " found");
            for (int i = 0; i < members.size(); i++) {
            System.out.println("Member " + i + ": " + members.get(i).getFullName());
            }
        }
    }
}