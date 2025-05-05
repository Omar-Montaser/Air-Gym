package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.SqlServerConnect;
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
    public List<Trainer> getAllTrainerDetails() {
        List<Trainer> trainers = new ArrayList<>();
        try {
            String sql = "SELECT * FROM GetTrainerDetails()";
            Connection conn = SqlServerConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                trainers.add(new Trainer(
                    rs.getInt("UserID"),
                    rs.getString("TrainerName"),
                    rs.getString("PhoneNumber"),
                    rs.getString("Gender"),
                    rs.getString("Specialization"),
                    rs.getInt("ExperienceYears"),
                    rs.getDouble("Salary"),
                    rs.getString("BranchName"),
                    rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trainers;
    }
    public int addNewTrainer(Trainer trainer)throws SQLException{
            String sql = "EXEC AddNewTrainer ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, trainer.getPassword());
            stmt.setString(2, trainer.getFirstName());
            stmt.setString(3, trainer.getLastName());
            stmt.setString(4, trainer.getPhoneNumber());
            stmt.setString(5, trainer.getGender());
            stmt.setDate(6, new java.sql.Date(trainer.getBirthDate().getTime()));
            stmt.setString(7, trainer.getSpecialization());
            stmt.setInt(8, trainer.getExperienceYears());
            stmt.setDouble(9, trainer.getSalary());
            stmt.setInt(10, trainer.getBranchId());
            stmt.setString(11, trainer.getStatus());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("NewTrainerID");
            }
        return -1;
    }
    public boolean updateTrainer(Trainer trainer) throws SQLException{
            Connection conn = SqlServerConnect.getConnection();
            String sql = "EXEC UpdateTrainer ?, ?, ?, ?, ?, ?, ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, trainer.getUserId());
            
            String phoneNumber = trainer.getPhoneNumber();
            if (phoneNumber != null) stmt.setString(2, phoneNumber);
            else stmt.setNull(2, java.sql.Types.VARCHAR);
            
            Integer experienceYears = trainer.getExperienceYears();
            if (experienceYears != null) stmt.setInt(3, experienceYears);
            else stmt.setNull(3, java.sql.Types.INTEGER);
            
            Double salary = trainer.getSalary();
            if (salary != null) stmt.setDouble(4, salary);
            else stmt.setNull(4, java.sql.Types.DECIMAL);
            
            Integer branchId = trainer.getBranchId();
            if (branchId != null) stmt.setInt(5, branchId);
            else stmt.setNull(5, java.sql.Types.INTEGER);
            
            String status = trainer.getStatus();
            if (status != null) stmt.setString(6, status);
            else stmt.setNull(6, java.sql.Types.VARCHAR);
            
            String specialization =trainer.getSpecialization();
            if(specialization!=null) stmt.setString(7, specialization);
            else stmt.setNull(7, java.sql.Types.VARCHAR);
            
            stmt.executeUpdate();
            return true;
    }
}
