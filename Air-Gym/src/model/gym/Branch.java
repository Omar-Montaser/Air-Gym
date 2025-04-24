package model.gym;

import java.sql.Date;

public class Branch {
    private int branchID;
    private String name;
    private String location;
    private String phoneNumber;
    private Date openingDate;
    private String status;
//================================================Constructor=============================================
public Branch(int branchID, String name, String location, String phoneNumber, Date openingDate, String status) {
    this.branchID = branchID;
    this.name = name;
    this.location = location;
    this.phoneNumber = phoneNumber;
    this.openingDate = openingDate;
    this.status = status;
}
//==================================================Get&Set===============================================
    public int getBranchID() {
        return branchID;
    }
    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public java.sql.Date getOpeningDate() {
        return openingDate;
    }
    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    
}
