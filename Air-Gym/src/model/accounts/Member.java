package model.accounts;

import java.sql.Date;

public class Member extends User{
    private int membershipTypeId;
    private Date subscriptionStartDate;
    private Date subscriptionEndDate;
    private int sessionsAvailable;
    private int freezeAvailable;
    private Date freezeEndDate;
    private int branchId;
    private Integer trainerId;
    private String subscriptionStatus;
    private String fullName;
    private int age;
    private String branchName;
    private String membershipName;
    private String trainerName;
//=======================================Constructor===================================
public Member(
    int userId, String firstName, String lastName,
    String password, String phoneNumber, String gender, 
    Date dateOfBirth, int membershipId, int branchId, Integer trainerId, 
    Date subscriptionStartDate, Date subscriptionEndDate,
    int sessionAvailable, String subscriptionStatus, int freezeAvailable, Date freezeEndDate) {
    super(userId, firstName, lastName,password, phoneNumber, gender, dateOfBirth, "Member"); 
    this.membershipTypeId = membershipId;
    this.branchId = branchId;
    this.trainerId = trainerId;
    this.subscriptionStatus = subscriptionStatus;
    this.subscriptionStartDate = subscriptionStartDate;
    this.subscriptionEndDate = subscriptionEndDate;
    this.sessionsAvailable = sessionAvailable;
    this.freezeAvailable = freezeAvailable;
    this.freezeEndDate = subscriptionStartDate;
}
public Member(
    int userId, String firstName, String lastName,
    String password, String phoneNumber, String gender, 
    Date dateOfBirth, int membershipTypeId, int branchId) {
    super(userId, firstName, lastName, password, phoneNumber, gender, dateOfBirth, "Member");
    this.membershipTypeId = membershipTypeId;
    this.branchId = branchId;
}
public Member( //only for admin's member view
    int userId,String fullName,String phoneNumber, int age, String gender, 
    String branchName,String trainerName,  String mtName, String subscriptionStatus,Date subscriptionEndDate,
    int sessionAvailable, int freezeAvailable, Date freezeEndDate
){
    super(userId, null, null,null, phoneNumber, gender, null, "Member"); 
    this.fullName = fullName;
    this.subscriptionEndDate = subscriptionEndDate;
    this.sessionsAvailable = sessionAvailable;
    this.subscriptionStatus = subscriptionStatus;
    this.freezeAvailable = freezeAvailable;
    this.freezeEndDate = freezeEndDate;
    this.branchName = branchName;
    this.membershipName = mtName;
    this.trainerName = trainerName;
    this.age=age;
}

//=======================================Get&Set=======================================

public int getMembershipId() {
    return membershipTypeId;
}

public int getBranchId() {
    return branchId;
}

public Integer getTrainerId() {
    return trainerId;
}

public String getSubscriptionStatus() {
    return subscriptionStatus;
}

public void setMembershipId(int membershipId) {
    this.membershipTypeId = membershipId;
}

public void setBranchId(int branchId) {
    this.branchId = branchId;
}

public void setTrainerId(Integer trainerId) {
    this.trainerId = trainerId;
}

public void setSubscriptionStatus(String subscriptionStatus) {
    this.subscriptionStatus = subscriptionStatus;
}
public Date getSubscriptionStartDate() {
    return subscriptionStartDate;
}
public void setSubscriptionStartDate(Date subscriptionStartDate) {
    this.subscriptionStartDate = subscriptionStartDate;
}
public Date getSubscriptionEndDate() {
    return subscriptionEndDate;
}
public void setSubscriptionEndDate(Date subscriptionEndDate) {
    this.subscriptionEndDate = subscriptionEndDate;
}
public int getSessionsAvailable() {
    return sessionsAvailable;
}
public void setSessionsAvailable(int sessionAvailable) {
    this.sessionsAvailable = sessionAvailable;
}
public int getFreezeAvailable() {
    return freezeAvailable;
}
public java.sql.Date getFreezeEndDate() {
    return freezeEndDate;
}
public void setFreezeAvailable(int freezeAvailable) {
    this.freezeAvailable = freezeAvailable;
}
public void setFreezeEndDate(java.sql.Date freezeEndDate) {
    this.freezeEndDate = freezeEndDate;
}
public int getMembershipTypeId() {
    return membershipTypeId;
}
public void setMembershipTypeId(int membershipTypeId) {
    this.membershipTypeId = membershipTypeId;
}
public String getFullName() {
    return fullName;
}
public void setFullName(String fullName) {
    this.fullName = fullName;
}
public int getAge() {
    return age;
}
public void setAge(int age) {
    this.age = age;
}
public String getBranchName() {
    return branchName;
}
public void setBranchName(String branchName) {
    this.branchName = branchName;
}
public String getMembershipName() {
    return membershipName;
}
public void setMembershipName(String membershipName) {
    this.membershipName = membershipName;
}
public String getTrainerName() {
    return trainerName;
}
public void setTrainerName(String trainerName) {
    this.trainerName = trainerName;
}
}