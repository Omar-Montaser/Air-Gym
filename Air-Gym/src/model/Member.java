package model;

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
}
