package edu.a1.system.auth;

import edu.a1.system.LibrarySystem;
import edu.a1.system.User;

/**
 * The SystemAuthenticator stores and handles the system's
 * authentication state.
 * 
 * @author Guanyuming He
 */
public final class SystemAuthenticator {
    private User loggedInUser;

    /**
     * Initial state: no user is logged in.
     */
    public SystemAuthenticator() {
        loggedInUser = null;
    }

    /** @return the logged in user, or null if not logged in */
    public User getLoggedInUser() { return loggedInUser; }

    /** @return true iff a user is logged in */
    public boolean loggedIn() { return getLoggedInUser() != null; }

    /** @return true if admin is logged in */
    public boolean isAdmin() { return loggedIn() && getLoggedInUser().getUsername().equals("admin"); }

    /** @return true if a normal reader is logged in */
    public boolean isReader() { return loggedIn() && !getLoggedInUser().getUsername().equals("admin"); }

    /**
     * Log a user in.
     * Precondition:
     *  1. No user is logged in
     *  2. username exists
     *  3. password is correct
     * Postcondition:
     *  user with that username is logged in.
     *      In particular, the reader context should be set if a reader has logged in.
     *  If `username = admin`, then the administrator is logged in
     * 
     * @param username
     * @param password
     * @throws IllegalArgumentException on incorrect credentials
     * @throws IllegalStateException if already logged in
     */
    public void login(String username, String password) {

        if(loggedIn()) {
            throw new IllegalStateException("already logged in.");
        }
     
        // check username and password.
        User usr = LibrarySystem.userStorage.findByUsername(username);
        if(usr == null) {
            throw new IllegalArgumentException("username or password is incorrect.");
        }
        if(!usr.getPwd().equals(password)) {
            throw new IllegalArgumentException("username or password is incorrect.");
        }

        // success
        loggedInUser = usr;

    }

    /**
     * Log the user out.
     * Precondition:
     *  a user is logged in.
     * Postcondition
     *  the user is logged out.
     * 
     * @throws IllegalStateException if not logged in
     */
    public void logout() {

        if(!loggedIn()) {
            throw new IllegalStateException("not logged in.");
        }

        // success.
        loggedInUser = null;
    }

    /**
     * Changes the currently logged in user's password
     * Precondition:
     *  1. a user is logged in
     *  2. new password != old password.
     *  3. the new password is approved based on a `PwdChangeStrategy` 
     *  (Naturally, we might require that the admin MUST have a strong password, but for normal users we might allow weaker ones for convenience.)
     * Postcondition:
     *  1. the password for that user is reset to new password.
     *  2. the user is logged out. You must log in again.
     * 
     * @param newPassword
     * @throws IllegalArgumentException if newPassword is not accepted.
     * @throws IllegalStateException if not logged in
     */
    public void changePassword(String newPassword) {
        
        // create the strategy based on the logged in user at runtime.
        // If none is logged in, then it will remain null.
        PwdChangeStrategy strategy = null;
        if(isAdmin()) {
            strategy = new AdminPwdChangeStrategy();
        }
        else if(isReader()) {
            strategy = new ReaderPwdChangeStrategy();
        }

        if(strategy == null) {
            throw new IllegalStateException("Not logged in.");
        }

        if(!strategy.approveNewPassword(loggedInUser, newPassword)) {
            // failure
            throw new IllegalArgumentException(strategy.getPasswordPolicy());
        }

        // success
        // change the password and replace the user in the database.
        loggedInUser.setPwd(newPassword);
        LibrarySystem.userStorage.replace(loggedInUser);
        // log out
        loggedInUser = null;
        
    }
}
