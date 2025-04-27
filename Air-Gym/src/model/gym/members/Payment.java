package model.gym.members;

import java.sql.Timestamp;

public class Payment {
    private int paymentID;
    private String category;
    private int memberID;
    private String paymentMethod;
    private Timestamp paymentDate;
    private double amount;
    private String status;
//================================================Constructor=============================================
    public Payment(int paymentID, String category, int memberID, String paymentMethod, Timestamp paymentDate, double amount, String status) {
        this.paymentID = paymentID;
        this.category = category;
        this.memberID = memberID;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.status = status;
    }
//==================================================Get&Set===============================================
    public int getPaymentID() {
        return paymentID;
    }
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
    public int getMemberID() {
        return memberID;
    }
    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
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
