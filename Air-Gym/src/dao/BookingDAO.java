package dao;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.gym.members.Booking;
import utils.SqlServerConnect;

import java.sql.ResultSet;

public class BookingDAO {
    private Connection conn;
    public BookingDAO(Connection conn){
        this.conn = conn;
    }
    public List<Booking> getAllMemberBookings(int userId) {
        String sql = "SELECT * FROM dbo.GetAllMemberBookings(?)";
        try (Connection conn = SqlServerConnect.getConnection();CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, userId);
            ResultSet rs = cstmt.executeQuery();
            List<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("BookingID"),
                    rs.getInt("UserID"),
                    rs.getInt("SessionID"),
                    rs.getString("BookingStatus"),
                    rs.getTimestamp("BookingDate"),
                    rs.getString("SessionType"),
                    rs.getTimestamp("DateTime"),
                    rs.getInt("Duration"),
                    rs.getString("SessionStatus"),
                    rs.getString("TrainerName"),
                    rs.getString("BranchName")
                ));
            }
            return bookings;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    

    public void createBooking(int userId, int sessionId) throws SQLException{
        String sql = "EXEC CreateBooking ?, ?";
        Connection conn = SqlServerConnect.getConnection();
        CallableStatement cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, userId);
            cstmt.setInt(2, sessionId);
            cstmt.executeQuery();
    }
    public boolean cancelBooking(int bookingId) {
        String sql = "EXEC CancelBooking ?";
        try (Connection conn = SqlServerConnect.getConnection();
        CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, bookingId);
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteBooking(int bookingId) {
        String sql = "EXEC DeleteBooking ?";
        try (Connection conn = SqlServerConnect.getConnection();
        CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, bookingId);
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateBookingStatus() {
        String sql = "EXEC UpdateBookingStatus";
        try (Connection conn = SqlServerConnect.getConnection();
        CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
