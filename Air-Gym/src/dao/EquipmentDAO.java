package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.gym.Equipment;
import utils.SqlServerConnect;



public class EquipmentDAO {
    private Connection conn;
    public EquipmentDAO( Connection conn){
        this.conn=conn;
    }
    public List<Equipment> getEquipment() {
        List<Equipment> equipments = new ArrayList<>();
        try {
            Connection conn = SqlServerConnect.getConnection();
            String query = "SELECT * FROM Equipment";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Equipment equipment = new Equipment(
                    resultSet.getInt("EquipmentID"),
                    resultSet.getString("Name"),
                    resultSet.getDate("PurchaseDate"),
                    resultSet.getDate("MaintenanceDate"),
                    resultSet.getString("Status"),
                    resultSet.getInt("BranchID")
                );
                equipments.add(equipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipments;
    }
    public void addNewEquipment(Equipment equipment) throws SQLException {
        String query = "EXEC AddNewEquipment @Name = ?, @Status = ?, @BranchID = ?";
        Connection conn = SqlServerConnect.getConnection();
        PreparedStatement statement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
        statement.setString(1, equipment.getName());
        statement.setString(2, equipment.getStatus());
        statement.setInt(3, equipment.getBranchID());
        statement.executeQuery();
    }
    public void updateEquipment(Equipment equipment) throws SQLException {
        Connection conn = SqlServerConnect.getConnection();
        String query = "EXEC UpdateEquipment @EquipmentID = ?, @Name = ?, @BranchID = ?, @Status = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, equipment.getEquipmentID());
        statement.setString(2, equipment.getName());
        statement.setInt(3, equipment.getBranchID());
        statement.setString(4, equipment.getStatus());
        statement.executeUpdate();
    }
    public void deleteEquipment(int equipmentID) {
        try{
        Connection conn = SqlServerConnect.getConnection();
        String query = "DELETE FROM Equipment WHERE EquipmentID = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, equipmentID);
        statement.executeUpdate();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public int getMaxEquipmentId(){
        String sql = "SELECT MAX(EquipmentID) FROM Equipment";
        
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
}
