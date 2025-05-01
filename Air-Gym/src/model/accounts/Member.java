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
}