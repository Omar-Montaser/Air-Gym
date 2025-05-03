package model.accounts;

public class Trainer extends User {
    private String specialization;
    private int experienceYears;
    private double salary;
    private int branchId;
    private String status;
    private String fullName;
    private String branchName;
//=======================================Constructor===================================
    public Trainer(int userId, String firstName, String lastName,String password, String phoneNumber, String gender, java.sql.Date dateOfBirth,
                   String specialization, int experienceYears, double salary, int branchId, String status) {
        super(userId, firstName, lastName,password, phoneNumber, gender, dateOfBirth, "Trainer"); 
        this.specialization = specialization;
        this.experienceYears = experienceYears;
        this.salary = salary;
        this.branchId = branchId;
        this.status = status;
    }
    public Trainer(int userId, String fullName, String phoneNumber, 
    String gender, String specialization, int experience, double Salary, 
    String branchName, String Status){
        //for admin view only
        super(userId, null, null,null, phoneNumber, gender, null, "Trainer"); 
        this.fullName = fullName;
        this.experienceYears = experience;
        this.specialization = specialization;
        this.salary = Salary;
        this.status = Status;
        this.branchName=branchName;
    }

//=======================================Get&Set=======================================
    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public int getExperienceYears() {
        return experienceYears;
    }
    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }
    public double getSalary() {
        return salary;
    }
    public void setSalary(double salary) {
        this.salary = salary;
    }
    public int getBranchId() {
        return branchId;
    }
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getBranchName() {
        return branchName;
    }
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
    
}
