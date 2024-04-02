package edu.a1.borrow;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import edu.a1.book.Book;
import edu.a1.system.context.reader.NormalReaderContext;
import edu.a1.system.context.reader.ReaderCtxDecorator;

/**
 * Precondition:
 *  A reader with unpaid fines is logged in.
 * @author Guanyuming He, Kemu Xu
 */
public class FinedReaderCtxDecorator extends ReaderCtxDecorator {

    // borrow histories that have unpaid fines.
    List<Borrow> unpaidBorrows;

    /**
     * @param history loaded by the system from the database.
     */
    public FinedReaderCtxDecorator(List<Borrow> history) {
        super(new NormalReaderContext(history));

        // TODO: find unpaid borrows
        unpaidBorrows = new ArrayList<>();

        throw new RuntimeException("Not implemented.");
    }

    /**
     * Ask the reader to pay the fine.
     * if he has paid, then allow him to borrow
     */
    @Override
    public void borrowBook(Book book, int numCopies) {

        // TODO: ask the reader to pay the fine.

        // if he has paid all, then allow him to borrow
        // if(unpaidBorrows.isEmpty()) 
        // {
        //     wrapped.borrowBook(book, numCopies, declaredReturnDate);
        // }

        throw new RuntimeException("Not implemented.");
    }

    /**
     * Ask the reader to pay the fine.
     * if he has paid, then allow him to return
     */
    @Override
    public void returnBook(Book book, int numCopies) {

        // TODO: ask the reader to pay the fine.

        // if he has paid all, then allow him to return
        // if(unpaidBorrows.isEmpty()) 
        // {
        //     wrapped.returnBook(book, numCopies);
        // }

        throw new RuntimeException("Not implemented.");
    }
    
}
