package model.gym.members;

import java.sql.Timestamp;

public class Session {
    private int sessionID;
    private int trainerID;
    private int branchID;
    private String sessionType;
    private int maxCapacity;
    private Timestamp dateTime;
    private int duration;
    private String status;
//================================================Constructor=============================================
    public Session(int sessionID, int trainerID, int branchID, String sessionType, int maxCapacity, Timestamp dateTime,
    int duration, String status) {
        this.sessionID = sessionID;
        this.trainerID = trainerID;
        this.branchID = branchID;
        this.sessionType = sessionType;
        this.maxCapacity = maxCapacity;
        this.dateTime = dateTime;
        this.duration = duration;
        this.status = status;
    }
//==================================================Get&Set===============================================

    public int getSessionID() {
        return sessionID;
    }
    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }
    public int getTrainerID() {
        return trainerID;
    }
    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }
    public int getBranchID() {
        return branchID;
    }
    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }
    public String getSessionType() {
        return sessionType;
    }
    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    public Timestamp getDateTime() {
        return dateTime;
    }
    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
