package model.gym.members;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Session {
    private Integer sessionID;
    private Integer trainerID;
    private Integer branchID;
    private String sessionType;
    private Integer maxCapacity;
    private Timestamp dateTime;
    private Integer duration;
    private String status;
    private String trainerName;
    private String branchName;
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

    public Integer getSessionID() {
        return sessionID;
    }
    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }
    public Integer getTrainerID() {
        return trainerID;
    }
    public void setTrainerID(int trainerID) {
        this.trainerID = trainerID;
    }
    public Integer getBranchID() {
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
    public Integer getMaxCapacity() {
        return maxCapacity;
    }
    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    public Timestamp getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = Timestamp.valueOf(dateTime);
    }
    public Integer getDuration() {
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
    
}
