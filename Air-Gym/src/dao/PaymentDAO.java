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
            Connection conn = SqlServerConnect.getConnection();
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
    public void createPayment(int memberID, String category, String paymentMethod, double amount) throws SQLException{
        Connection conn = SqlServerConnect.getConnection();
        String sql = "EXEC AddPayment ?, ?, ?, ?";
        PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, memberID);
            ps.setString(2, category);
            ps.setString(3, paymentMethod);
            ps.setDouble(4, amount);
            ps.executeUpdate();
    }

    public boolean cancelPayment(int paymentID) {
        
        String sql = "EXEC CancelPayment ?";
        try (Connection conn = SqlServerConnect.getConnection();PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, paymentID);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
