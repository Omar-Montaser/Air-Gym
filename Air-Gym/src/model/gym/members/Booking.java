package model.gym.members;

import java.sql.Timestamp;

public class Booking {
    private String sessionName;
    private String branchName;
    private String trainerName;
    private Timestamp sessionDate;
    private Integer duration;
    private String sessionStatus;
    private Timestamp bookingDate;
    private int bookingID;
    private int userID;
    private int sessionID;
    private String status;



//================================================Constructor=============================================
    public Booking(int bookingID, int userID, int sessionID, String status,
            Timestamp bookingDate) {
        this.bookingID = bookingID;
        this.userID = userID;
        this.sessionID = sessionID;
        this.status = status;
        this.bookingDate = bookingDate;
    }
    public Booking(int bookingID, int userID, int sessionID, String status,
        Timestamp bookingDate, String sessionName, Timestamp sessionDate,
        int duration, String sessionStatus, String trainerName, String branchName) {
            this.bookingID = bookingID;
            this.userID = userID;
            this.sessionID = sessionID;
            this.status = status;
            this.bookingDate = bookingDate;
            this.sessionName = sessionName;
            this.sessionDate = sessionDate;
            this.duration = (Integer)duration;
            this.sessionStatus = sessionStatus;
            this.trainerName = trainerName;
            this.branchName = branchName;
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
    public Timestamp getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public void setSessionStatus(String sessionStatus) {
        this.sessionStatus = sessionStatus;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getBranchName() {
        return branchName;
    }
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    public String getSessionName() {
        return sessionName;
    }
    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }
    public Timestamp getSessionDate() {
        return sessionDate;
    }
    public void setSessionDate(Timestamp sessionDate) {
        this.sessionDate = sessionDate;
    }
    
}
