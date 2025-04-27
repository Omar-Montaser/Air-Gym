package model.accounts;

public class User {
    private final String USER_ID;
    //TODO add AD, MEM and PT before each id when retrieved from database
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String gender;
    private java.sql.Date dateOfBirth;
    private final String role;
//=======================================Constructor===================================
    public User(String USER_ID,  String firstName, String lastName, String password,String phoneNumber, String gender, java.sql.Date dateOfBirth,String role) {
        this.USER_ID = USER_ID;
        this.password=password; 
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.role=role;
    }
    public User(String USER_ID, String firstName, String lastName,String password, String phoneNumber, String gender, String role) {
        this.USER_ID = USER_ID;
        this.password=password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.role = role;
        this.dateOfBirth = null;
    }
//=======================================Get&Set=======================================
    public String getUserId() {
        return USER_ID;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getFullName(){
        return firstName + " " + lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getGender() {
        return gender;
    }
    public java.sql.Date getBirthDate() {
        return dateOfBirth;
    }
    public String getBirthDateAsString() {
        return dateOfBirth.toString();
    }
    public String getRole() {
        return role;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setDateOfBirth(java.sql.Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
