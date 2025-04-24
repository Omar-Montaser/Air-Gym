package model.accounts;

public class Member {
    private java.sql.Date joinDate;
    private int membershipId;
    private int branchId;
    private Integer trainerId;
    private String paymentMethod;
    private String subscriptionStatus;
//=======================================Constructor===================================
public Member(String userId, String firstName,java.sql.Date joinDate, int membershipId, int branchId, Integer trainerId, String paymentMethod, String subscriptionStatus) {
    this.joinDate = joinDate;
    this.membershipId = membershipId;
    this.branchId = branchId;
    this.trainerId = trainerId;
    this.paymentMethod = paymentMethod;
    this.subscriptionStatus = subscriptionStatus;
}
//=======================================Get&Set=======================================
public java.sql.Date getJoinDate() {
    return joinDate;
}

public int getMembershipId() {
    return membershipId;
}

public int getBranchId() {
    return branchId;
}

public Integer getTrainerId() {
    return trainerId;
}

public String getPaymentMethod() {
    return paymentMethod;
}

public String getSubscriptionStatus() {
    return subscriptionStatus;
}

public void setJoinDate(java.sql.Date joinDate) {
    this.joinDate = joinDate;
}

public void setMembershipId(int membershipId) {
    this.membershipId = membershipId;
}

public void setBranchId(int branchId) {
    this.branchId = branchId;
}

public void setTrainerId(Integer trainerId) {
    this.trainerId = trainerId;
}

public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
}

public void setSubscriptionStatus(String subscriptionStatus) {
    this.subscriptionStatus = subscriptionStatus;
}
}
