package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
