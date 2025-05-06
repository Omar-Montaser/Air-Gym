package dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                    resultSet.getString("AdminID")
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
                    resultSet.getString("AdminID")
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
                    resultSet.getString("AdminID")
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
}
