package edu.a1.system.auth;

import edu.a1.system.User;

/**
 * Algorithm to determine if the new pwd for reader can be approved.
 * 
 * @author Guanyuming He
 */
public class ReaderPwdChangeStrategy implements PwdChangeStrategy {

    /**
     * A reader's password must be at least 6 characters long.
     * @return true if so && pwd != old pwd
     */
    @Override
    public boolean approveNewPassword(User usr, String pwd) {
        assert(!usr.getUsername().equals("admin"));

        if(pwd.equals(usr.getPwd())) {
            throw new IllegalArgumentException("pwd is the same as the current one.");
        }

        // A reader's password must be at least 6 characters long.
        return pwd.length() >= 6;
    }

    @Override
    public String getPasswordPolicy() {
        return "A reader's password must be at least 6 characters long.";
    }
    
}
