package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;


import model.gym.members.MembershipType;

public class MembershipTypeDAO {
    private Connection conn;
    public MembershipTypeDAO(Connection conn){
        this.conn = conn;
    }

    public List<MembershipType> getAllMembershipTypes(){
        List<MembershipType> membershipTypes = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM MembershipType ORDER BY AccessLevel");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                membershipTypes.add(mapResultSetToMembershipType(rs));
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return membershipTypes;
    }

    public String getMembershipTypeName(int membershipId){
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT Name FROM MembershipType WHERE MembershipTypeID = ?");
            stmt.setInt(1, membershipId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getString("Name");
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public MembershipType getMembershipTypeById(int membershipId){
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM MembershipType WHERE MembershipTypeID = ?");
            stmt.setInt(1, membershipId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return mapResultSetToMembershipType(rs);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    private MembershipType mapResultSetToMembershipType(ResultSet rs) throws SQLException{
        return new MembershipType(
            rs.getInt("MembershipTypeID"),
            rs.getString("Name"),
            rs.getString("Description"),
            rs.getString("AccessLevel"),
            rs.getDouble("MonthlyPrice"),
            rs.getInt("Sessions"),
            rs.getBoolean("PrivateTrainer"),
            rs.getInt("FreezeDuration"),
            rs.getInt("InBody"),
            rs.getString("ColorHex")
        );
    }

}
