package model.gym.members;

public class MembershipType {
    private int membershipID;
    private String name;
    private String description;
    private double monthlyPrice;
    private int duration;
    private int noOfSessions;
    private int noOfPrivateSessions;
    private int freezeDuration;
    private boolean inBody;
    private int accessLevel;
//================================================Constructor=============================================
    public MembershipType(int membershipID, String name, String description, double monthlyPrice, int duration,
        int noOfSessions, int noOfPrivateSessions, int freezeDuration, boolean inBody, int accessLevel) {
        this.membershipID = membershipID;
        this.name = name;
        this.description = description;
        this.monthlyPrice = monthlyPrice;
        this.duration = duration;
        this.noOfSessions = noOfSessions;
        this.noOfPrivateSessions = noOfPrivateSessions;
        this.freezeDuration = freezeDuration;
        this.inBody = inBody;
        this.accessLevel = accessLevel;
    }
//==================================================Get&Set=============================================
    public int getMembershipID() {
        return membershipID;
    }
    public void setMembershipID(int membershipID) {
        this.membershipID = membershipID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getMonthlyPrice() {
        return monthlyPrice;
    }
    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public int getNoOfSessions() {
        return noOfSessions;
    }
    public void setNoOfSessions(int noOfSessions) {
        this.noOfSessions = noOfSessions;
    }
    public int getNoOfPrivateSessions() {
        return noOfPrivateSessions;
    }
    public void setNoOfPrivateSessions(int noOfPrivateSessions) {
        this.noOfPrivateSessions = noOfPrivateSessions;
    }
    public int getFreezeDuration() {
        return freezeDuration;
    }
    public void setFreezeDuration(int freezeDuration) {
        this.freezeDuration = freezeDuration;
    }
    public boolean isInBody() {
        return inBody;
    }
    public void setInBody(boolean inBody) {
        this.inBody = inBody;
    }
    public int getAccessLevel() {
        return accessLevel;
    }
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    
}
