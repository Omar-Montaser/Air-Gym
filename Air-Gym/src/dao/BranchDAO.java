package dao;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.gym.Branch;
import utils.SqlServerConnect;

public class BranchDAO {
    private Connection connection;
    public BranchDAO(Connection connection){
        this.connection = connection;
    }
    public List<Branch> getBranches(){
        List<Branch> branches = new ArrayList<>();

        try{
            Connection conn = SqlServerConnect.getConnection();
            String query = "SELECT * FROM Branch";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Branch branch = new Branch(
                    resultSet.getInt("BranchID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Location"),
                    resultSet.getDate("OpeningDate"),
                    resultSet.getString("Status"),
                    resultSet.getInt("AdminID")
                );
                branches.add(branch);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return branches;
    }
    public Branch getBranchById(int branchId){
        Branch branch=null;
        try{
            String query = "SELECT * FROM Branch WHERE BranchID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, branchId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                branch = new Branch(
                    resultSet.getInt("BranchID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Location"),
                    resultSet.getDate("OpeningDate"),
                    resultSet.getString("Status"),
                    resultSet.getInt("AdminID")
                );
                }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return branch;
    } 
    public Branch getBranchByName(String name){
        Branch branch=null;
        try{
            String query = "SELECT * FROM Branch WHERE Name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                branch = new Branch(
                    resultSet.getInt("BranchID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Location"),
                    resultSet.getDate("OpeningDate"),
                    resultSet.getString("Status"),
                    resultSet.getInt("AdminID")
                );
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return branch;
    }
    
    public void deleteBranch(int branchId) {
        try {
            String query = "EXEC DeleteBranch @BranchID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, branchId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getMaxBranchId(){
        String sql = "SELECT MAX(BranchID) FROM Branch";
     
        try (Connection conn = SqlServerConnect.getConnection();Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) return rs.getInt(1);
                else return 0; 
        }
        catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    public void createBranch(Branch branch) throws SQLException{
            String query = "EXEC AddNewBranch @Name = ?, @Location = ?, @Status = ?, @AdminID = ?";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, branch.getName());
            statement.setString(2, branch.getLocation());
            statement.setString(3, branch.getStatus());
            statement.setInt(4, branch.getAdminID());
    }
    public void updateBranch(Branch branch) throws SQLException {
        String query = "EXEC UpdateBranch @BranchID = ?, @Name = ?, @Location = ?, @Status = ?, @AdminID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, branch.getBranchID());
            statement.setString(2, branch.getName());
            statement.setString(3, branch.getLocation());
            statement.setString(4, branch.getStatus());
            statement.setInt(5, branch.getAdminID());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
