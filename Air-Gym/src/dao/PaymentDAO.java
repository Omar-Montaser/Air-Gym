package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.gym.members.Payment;
import utils.SqlServerConnect;

public class PaymentDAO {
    private Connection conn;
    public PaymentDAO( Connection conn){
        this.conn=conn;
    }
    public List<Payment> getAllPayments() {

        List<Payment> payments = new ArrayList<>();
        try {
            String query = "SELECT * FROM Payment";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Payment payment = new Payment(
                    resultSet.getInt("PaymentID"),
                    resultSet.getString("Category"),
                    resultSet.getInt("MemberID"),
                    resultSet.getString("PaymentMethod"),
                    resultSet.getTimestamp("PaymentDate"),
                    resultSet.getDouble("Amount"),
                    resultSet.getString("Status")
                );
                payments.add(payment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }
}
