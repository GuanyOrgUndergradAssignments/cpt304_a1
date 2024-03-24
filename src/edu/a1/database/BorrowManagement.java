package edu.a1.database;
import java.sql.Date;
import java.util.List;

import edu.a1.borrow.Borrow;
import edu.a1.system.User;

public class BorrowManagement implements BorrowManager {

    private DatabaseConnection databaseConnection;

    public BorrowManagement() {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    
    // Save a borrow object in database
    @Override
    public void save(Borrow borrow) {
        
    }

    // Delete a borrow object from database
    @Override
    public void delete(Borrow borrow) {
        
    }

    // Replace an existed borrow object in database
    @Override
    public void replace(Borrow borrow){

    }

    /**
     * Finds borrow record by username.
     * 
     * @param username the username provided.
     * @return a list of borrow record with the specific username.
     */
    @Override
    public List<Borrow> findByUsername(String username){
        throw new RuntimeException("Not implemented.");
    }

    /**
     * Finds borrow record by isbn.
     * 
     * @param isbn the isbn provided.
     * @return a list of borrow record with the specific isbn.
     */
    @Override
    public List<Borrow> findByISBN(String isbn){
        throw new RuntimeException("Not implemented.");
    }
    
    /**
     * Check if borrow record is existed.
     * 
     * @param id the id provided.
     * @return boolean 
     */
    @Override
    public boolean existId(String id){
        return true;
    }


    /**
     * Finds borrow record by Start time and username.
     * 
     * @param username the username provided.
     * @param start the start time.
     * @return the specific record.
     */
    @Override
    public Borrow findByStartTimeAndUsername(User user, Date start) {
        throw new RuntimeException("Not implemented.");
    }


    /**
     * Finds borrow record by End time and username.
     * 
     * @param username the username provided.
     * @param end the end time.
     * @return the specific record.
     */
    @Override
    public Borrow findByEndTimeAndUsername(User user, Date end) {
        throw new RuntimeException("Not implemented.");
    }
}
