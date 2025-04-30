package controller;

public class Validation {
    public static String validatePhoneNumber(String phoneNumber) throws Exception{
        String digitsOnly = phoneNumber.replaceAll("[^0-9]", "");
        
        if (digitsOnly.length() != 11) {
            throw new Exception("Phone number must be exactly 11 digits");
        }
        if (!digitsOnly.startsWith("01")) {
             throw new Exception("Phone number must start with 01");
        }
        char thirdDigit = digitsOnly.charAt(2);
        if (thirdDigit != '0' && thirdDigit != '1' && thirdDigit != '2' && thirdDigit != '5') {
             throw new Exception("Third digit must be 0, 1, 2, or 5");
        }
        if (!phoneNumber.matches("[0-9()\\- ]+")) {
             throw new Exception("Phone number can only contain digits, spaces, dashes, and parentheses");
        }
        if (phoneNumber.matches(".*[A-Za-z].*")) {
             throw new Exception("Phone number cannot contain letters");
        }
        
        return phoneNumber;
    }
    
    public static String validatePassword(String password) throws Exception{
        if(password=="12345678"){ //for testing flow
            return password;
        }
        if(password.length()<8){
            throw new Exception("Password must be at least 8 characters long");
        }
        return password;
    }
}
