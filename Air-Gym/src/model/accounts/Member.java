package model.accounts;


public class Member extends User{
    private java.sql.Date joinDate;
    private int membershipTypeId;
    private String subscriptionStartDate;
    private String subscriptionEndDate;
    private int sessionsAvailable;
    private int branchId;
    private Integer trainerId;
    private String subscriptionStatus;
//=======================================Constructor===================================
public Member(String userId, String firstName, String lastName,
    String password, String phoneNumber, String gender, java.sql.Date dateOfBirth,
    java.sql.Date joinDate, int membershipId, int branchId, Integer trainerId, 
    String paymentMethod, String subscriptionStartDate, String subscriptionEndDate,
    int sessionAvailable,String subscriptionStatus) {
    super('M'+userId, firstName, lastName,password, phoneNumber, gender, dateOfBirth, "Member"); 
    this.joinDate = joinDate;
    this.membershipTypeId = membershipId;
    this.branchId = branchId;
    this.trainerId = trainerId;
    this.subscriptionStatus = subscriptionStatus;
    this.subscriptionStartDate = subscriptionStartDate;
    this.subscriptionEndDate = subscriptionEndDate;
    this.sessionsAvailable = sessionAvailable;
}
//=======================================Get&Set=======================================
public java.sql.Date getJoinDate() {
    return joinDate;
}

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

public void setJoinDate(java.sql.Date joinDate) {
    this.joinDate = joinDate;
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
public String getSubscriptionStartDate() {
    return subscriptionStartDate;
}
public void setSubscriptionStartDate(String subscriptionStartDate) {
    this.subscriptionStartDate = subscriptionStartDate;
}
public String getSubscriptionEndDate() {
    return subscriptionEndDate;
}
public void setSubscriptionEndDate(String subscriptionEndDate) {
    this.subscriptionEndDate = subscriptionEndDate;
}
public int getSessionsAvailable() {
    return sessionsAvailable;
}
public void setSessionsAvailable(int sessionAvailable) {
    this.sessionsAvailable = sessionAvailable;
}
public int getSubscriptionDuration() {
    String[] start = subscriptionStartDate.split("-");
    String[] end = subscriptionEndDate.split("-");
    int startYear = Integer.parseInt(start[0]);
    int startMonth = Integer.parseInt(start[1]);
    int endYear = Integer.parseInt(end[0]);
    int endMonth = Integer.parseInt(end[1]);
    int duration = (endYear - startYear) * 12 + (endMonth - startMonth);
    return duration;
}
}
