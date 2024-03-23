package edu.a1.system.context;

import edu.a1.book.Book;
import edu.a1.system.User;

/**
 * The SystemAdministration class provides all methods related to the administration of the library system.
 * 
 * @author Guanyuming He
 */
public final class SystemAdminContext {

    /**
     * Registers a new normal user (non-admin) into the library.
     * Precondition:
     *  1. user with username doesn't exist. In particular, username != admin.
     *  2. password is approved by UserPwdChangeStrategy
     * Postcondition:
     *  the user is added into the database.
     * 
     * @param username
     * @param password
     * @return the user registered
     */
    public User registerNormalUser(String username, String password) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Removes a normal user from the library
     * Precondition:
     *  1. user with username exists. 
     *  2. username != admin.
     *  3. the user doesn't have any unreturned book.
     * Postcondition:
     *  the user is removed from the database.
     * 
     * @param username
     */
    public void removeNormalUser(String username) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Adds copies of a book into the library
     * Precondition:
     *  NONE.
     * Postcondition
     *  1. If the library already has copies of the book (already has an entry in the database for it),
     *  then the number of copies is added
     *  2. Otherwise, a database entry for the book is created, 
     *  and the arguments given are written to the entry.
     * 
     * @param book all information of the book to add
     * @return true if the book did not exist; false if the book already existed.
     */
    public boolean addBook(Book book) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Removes all copies of a book from the library
     * Precondition:
     *  1. The book specified by the arguments exists.
     *  2. No copy of the book is currently borrowed.
     * Postcondition:
     *  the book is removed from the database.
     * 
     * @param the unique identifier of the book, ISBN.
     * @return true iff the book is successfully removed.
     */
    public boolean removeBook(String ISBN) {
        throw new RuntimeException("Not implemented");
    }
}
