package edu.a1.system.context.reader;

import edu.a1.book.Book;
import edu.a1.borrow.Borrow;

import java.util.Date;
import java.util.List;

/**
 * Decorates the reader context to add more responsibilities/behaviours 
 * to borrowBook and returnBook
 * 
 * @author Guanyuming He, Kemu Xu
 */
public abstract class ReaderCtxDecorator implements ReaderContext {
    private ReaderContext wrapped;

    public ReaderContext getWrapped() {
        return wrapped;
    }

    public ReaderCtxDecorator(ReaderContext wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void borrowBook(Book book, int numCopies, Date declaredReturnDate){
        wrapped.borrowBook(book, numCopies, declaredReturnDate);
    }

    @Override
    public void returnBook(Book book, int numCopies){
        wrapped.returnBook(book, numCopies);
    }
}
