package model.gym.members;

public class MembershipType {
    private int membershipID;
    private String name;
    private String description;
    private int accessLevel;
    private double monthlyPrice;
    private int noOfSessions;
    private boolean privateTrainer;
    private int freezeDuration;
    private int inBody;
    private String colorHex;

//================================================Constructor=============================================
    public MembershipType(int membershipID, String name, String description, String accessLevel, double monthlyPrice,
        int noOfSessions,boolean privateTrainer, int freezeDuration, int inBody, String colorHex) {
            this.membershipID = membershipID;
            this.name = name;
            this.description = description;
            this.accessLevel = Integer.parseInt(accessLevel);
            this.monthlyPrice = monthlyPrice;
            this.noOfSessions = noOfSessions;
            this.privateTrainer = privateTrainer;
            this.freezeDuration = freezeDuration;
            this.inBody = inBody;
            this.colorHex = colorHex;
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
    public int getNoOfSessions() {
        return noOfSessions;
    }
    public void setNoOfSessions(int noOfSessions) {
        this.noOfSessions = noOfSessions;
    }
    public int getFreezeDuration() {
        return freezeDuration;
    }
    public void setFreezeDuration(int freezeDuration) {
        this.freezeDuration = freezeDuration;
    }
    public int getInBody() {
        return inBody;
    }
    public void setInBody(int inBody) {
        this.inBody = inBody;
    }
    public int getAccessLevel() {
        return accessLevel;
    }
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
    public boolean hasPrivateTrainer() {
        return privateTrainer;
    }
    public void setPrivateTrainer(boolean privateTrainer) {
        this.privateTrainer = privateTrainer;
    }
    public String getColorHex() {
        return colorHex;
    }
    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    
}
