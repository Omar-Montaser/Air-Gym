package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.accounts.Trainer;

public class TrainerDAO {
    private Connection conn;
    public TrainerDAO(Connection conn){
        this.conn = conn;
    }
    public Trainer getTrainerById(int id){
        try{
            String sql = "SELECT u.UserID, u.FirstName, u.LastName, u.Password, u.PhoneNumber, u.Gender, u.DateOfBirth, " +
                         "t.Specialization, t.ExperienceYears, t.Salary, t.BranchID, t.Status " +
                         "FROM Users u JOIN Trainer t ON u.UserID = t.UserID WHERE u.UserID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new Trainer(
                    rs.getInt("UserID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Password"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Gender"),
                    rs.getDate("DateOfBirth"),
                    rs.getString("Specialization"),
                    rs.getInt("ExperienceYears"),
                    rs.getDouble("Salary"),
                    rs.getInt("BranchID"),
                    rs.getString("Status")
                );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
