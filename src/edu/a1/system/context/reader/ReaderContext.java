package edu.a1.system.context.reader;

import java.util.Date;

import edu.a1.book.Book;

/**
 * Defines all that a reader can do:
 *  borrowBook and returnBook
 * 
 * @author Guanyuming He
 */
public interface ReaderContext {

    /**
     * Try to borrow numCopies of book for the currently logged in user, and declare to return them 
     * before declaredReturnDate.
     * 
     * @param book before this method is called, the book to be borrowed is found from the database.
     * @param numCopies
     * @param declaredReturnDate
     */
    public void borrowBook(Book book, int numCopies, Date declaredReturnDate);

    /**
     * Try to return numCopies of book.
     * 
     * @param book before this method is called, the book to be returned is found from the database.
     * @param numCopies
     */
    public void returnBook(Book book, int numCopies);
    
}
