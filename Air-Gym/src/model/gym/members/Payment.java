package model.gym.members;

import java.sql.Timestamp;

public class Payment {
    private int paymentID;
    private int userID;
    private Timestamp paymentDate;
    private double amount;
    private String paymentMethod;
    private String status;
//================================================Constructor=============================================
    public Payment(int paymentID, int userID, Timestamp paymentDate, double amount, String paymentMethod, String status) {
        this.paymentID = paymentID;
        this.userID = userID;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }
//==================================================Get&Set===============================================
    public int getPaymentID() {
        return paymentID;
    }
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public Timestamp getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    
}
