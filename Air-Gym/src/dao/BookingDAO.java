package dao;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.gym.members.Booking;

import java.sql.ResultSet;

public class BookingDAO {
    private Connection conn;
    public BookingDAO(Connection conn){
        this.conn = conn;
    }
    public List<Booking> getAllMemberBookings(int userId) {
        String sql = "SELECT * FROM dbo.GetAllMemberBookings(?)";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, userId);
            ResultSet rs = cstmt.executeQuery();
            List<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                bookings.add(new 
                Booking(rs.getInt("BookingID"),
                 rs.getInt("UserID"), 
                 rs.getInt("SessionID"), 
                 rs.getString("Status"), 
                 rs.getTimestamp("BookingDate")));
            }
            return bookings;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int createBooking(int userId, int sessionId) {
        String sql = "EXEC CreateBooking ?, ?";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, userId);
            cstmt.setInt(2, sessionId);
            ResultSet rs = cstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("BookingID");
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public boolean cancelBooking(int bookingId) {
        String sql = "EXEC CancelBooking ?";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
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
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
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
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
