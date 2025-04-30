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
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM MembershipType");
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

    private MembershipType mapResultSetToMembershipType(ResultSet rs) throws SQLException{
        return new MembershipType(
            rs.getInt("MembershipID"),
            rs.getString("Name"),
            rs.getString("Description"),
            rs.getString("AccessLevel"),
            rs.getDouble("MonthlyPrice"),
            rs.getInt("NoOfSessions"),
            rs.getBoolean("PrivateTrainer"),
            rs.getInt("FreezeDuration"),
            rs.getInt("InBody"),
            rs.getString("ColorHex")
        );
    }
}
