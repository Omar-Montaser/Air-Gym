package model.gym;

import java.sql.Date;

public class Equipment {
    private int equipmentID;
    private String name;
    private Date purchaseDate;
    private Date maintenanceDate;
    private String status;
    private int branchID;
//================================================Constructor=============================================
    public Equipment(int equipmentID, String name, Date purchaseDate, Date maintenanceDate, String status,
    int branchID) {
        this.equipmentID = equipmentID;
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.maintenanceDate = maintenanceDate;
        this.status = status;
        this.branchID = branchID;
    }
//==================================================Get&Set===============================================
    public int getEquipmentID() {
        return equipmentID;
    }
    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public Date getMaintenanceDate() {
        return maintenanceDate;
    }
    public void setMaintenanceDate(Date maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getBranchID() {
        return branchID;
    }
    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }    
}
