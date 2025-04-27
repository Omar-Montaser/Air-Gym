package model.gym.members;

import java.sql.Timestamp;

public class Booking {
    private int bookingID;
    private int userID;
    private int sessionID;
    private String paymentID;
    private String status;
    private Timestamp bookingDate;

//================================================Constructor=============================================
    public Booking(int bookingID, int userID, int sessionID, String status, String paymentID,
            Timestamp bookingDate) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.sessionID = sessionID;
        this.status = status;
        this.paymentID = paymentID;
        this.bookingDate = bookingDate;
    }
//==================================================Get&Set===============================================
    public int getBookingID() {
        return bookingID;
    }
    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public int getSessionID() {
        return sessionID;
    }
    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getPaymentID() {
        return paymentID;
    }
    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }
    public Timestamp getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }
    


}
