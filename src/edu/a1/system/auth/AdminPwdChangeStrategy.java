package edu.a1.system.auth;

import edu.a1.system.User;

/**
 * Algorithm to determine if the new pwd for admin can be approved.
 * 
 * @author Guanyuming He
 */
public class AdminPwdChangeStrategy implements PwdChangeStrategy {

    @Override
    public boolean approveNewPassword(User usr, String pwd) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'approveNewPassword'");
    }

    @Override
    public String getPasswordPolicy(User usr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPasswordPolicy'");
    }
    
}
