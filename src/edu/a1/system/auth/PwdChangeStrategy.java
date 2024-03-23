package edu.a1.system.auth;

import edu.a1.system.User;

/**
 * Algorithm to determine if the new pwd can be approved.
 * 
 * @author Guanyuming He
 */
public interface PwdChangeStrategy {
    /**
     * @param usr
     * @param pwd
     * @return true iff the new password can be approved for the usr.
     */
    public boolean approveNewPassword(User usr, String pwd);

    /**
     * @param usr
     * @return a string of the password policy for the usr.
     */
    public String getPasswordPolicy(User usr);
}
