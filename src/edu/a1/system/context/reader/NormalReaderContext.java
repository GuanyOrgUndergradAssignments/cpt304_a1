package edu.a1.system.context.reader;

import java.util.Date;
import java.util.List;


import edu.a1.book.Book;
import edu.a1.borrow.Borrow;

/**
 * Precondition: A reader is logged in
 * 
 * @author Guanyuming He, Kemu Xu
 */
public class NormalReaderContext implements ReaderContext  {

    protected final List<Borrow> borrowHistory;

    /**
     * @param history loaded by the system from the database.
     */
    public NormalReaderContext(List<Borrow> history) {
        this.borrowHistory = history;
    }

    /**
     * Precondition:
     *  1. the book exists in the library
     *  2. the number of copies to borrow <= the number of available copies
     *  3. an internal algorithm deems that the user is able to borrow this many books for this long. 
     *  Possible implementation may take past borrow history and currently unreturned books into account.
     * 
     * Postcondition:
     *  1. number of available copies of the book in the system -= number of copies to borrow
     *  2. ALL of the information about the borrow is stored in the reader's database entry in some way.
     */
    @Override
    public void borrowBook(Book book, int numCopies, Date declaredReturnDate) {
        throw new RuntimeException("Unimplemented method 'borrowBook'");
    }

    /**
     * Precondition
     *  1. the user's borrow history includes the book
     *  2. the number of copies to return <= the number of copies borrowed
     * 
     * Postcondition
     *  1. number of available copies of the book in the system `+=` number of copies to return
     *  2. in the borrow history, the number of copies borrowed `-=` number of copies to return
     */
    @Override
    public void returnBook(Book book, int numCopies) {
        throw new RuntimeException("Unimplemented method 'returnBook'");
    }
    
}
