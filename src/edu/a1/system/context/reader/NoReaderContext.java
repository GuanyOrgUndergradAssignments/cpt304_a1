package edu.a1.system.context.reader;

import java.sql.Date;

import edu.a1.book.Book;

/**
 * The reader context if no reader is logged in.
 * @author Guanyuming He
 */
public final class NoReaderContext implements ReaderContext {

    /**
     * Not supported if no reader is logged in.
     */
    @Override
    public void borrowBook(Book book, int numCopies, Date declaredReturnDate) {
        throw new UnsupportedOperationException("A reader is not logged in.");
    }

    /**
     * Not supported if no reader is logged in.
     */
    @Override
    public void returnBook(Book book, int numCopies) {
        throw new UnsupportedOperationException("A reader is not logged in.");
    }
    
}
