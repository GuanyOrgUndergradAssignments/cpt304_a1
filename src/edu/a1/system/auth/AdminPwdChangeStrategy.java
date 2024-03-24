package edu.a1.system.auth;

import edu.a1.system.User;

/**
 * Algorithm to determine if the new pwd for admin can be approved.
 * 
 * @author Guanyuming He
 */
public class AdminPwdChangeStrategy implements PwdChangeStrategy {

    /**
     * The admin's password must be at least 12 characters long, and must have both letters and numbers.
     * @return true if so && pwd != old pwd
     */
    @Override
    public boolean approveNewPassword(User usr, String pwd) {
        assert(usr.getUsername().equals("admin"));

        if(pwd.equals(usr.getPwd())) {
            throw new IllegalArgumentException("pwd is the same as the current one.");
        }

        // The admin's password must be at least 12 characters long
        if(pwd.length() < 12) {
            return false;
        }

        // and must have both letters and numbers.
        boolean hasLetters = false;
        boolean hasNumbers = false;
        for(int i = 0; i < pwd.length(); ++i) {
            if(Character.isAlphabetic(pwd.charAt(i))) {
                hasLetters = true;
            }
            else if(Character.isDigit(pwd.charAt(i))) {
                hasNumbers = true;
            }
        }

        return hasLetters && hasNumbers;
    }

    @Override
    public String getPasswordPolicy() {
        return "The admin's password must be at least 12 characters long, and must have both letters and numbers.";
    }
    
}
