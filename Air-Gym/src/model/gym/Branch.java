package model.gym;

import java.sql.Date;

import dao.UserDAO;
import utils.SqlServerConnect;

public class Branch {
    private int branchID;
    private String name;
    private String location;
    private Date openingDate;
    private String status;
    private int adminID;
//================================================Constructor=============================================
public Branch(int branchID, String name, String location, Date openingDate, String status, int adminID) {
    this.branchID = branchID;
    this.name = name;
    this.location = location;
    this.openingDate = openingDate;
    this.status = status;
    this.adminID = adminID;
}
public Branch(int branchID, String name, String location, String status, int adminID) {
    this.branchID = branchID;
    this.name = name;
    this.location = location;
    this.status = status;
    this.adminID = adminID;
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
    public int getAdminID() {
        return adminID;
    }
    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }
}
