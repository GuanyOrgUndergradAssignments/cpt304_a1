package edu.a1.database;

import edu.a1.system.User;

public class UserManagement implements UserManager {

    private DatabaseConnection databaseConnection;

    public UserManagement() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    // Save a user object in database
    @Override
    public void save(User user) {
        
    }

    // Delete a user object from database
    @Override
    public void delete(User user) {
        
    }

    // Replace an existed user in database
    @Override
    public void replace(User user){

    }

    /**
     * Finds user by username.
     * 
     * @param username the user provided.
     * @return the specific user.
     */
    @Override
    public User findByUsername(String username){

    }

    /**
     * Check if user is existed.
     * 
     * @param username the username provided.
     * @return boolean 
     */
    @Override
    public boolean existUser(String username){
        return true;
    }
}
