package edu.a1.system.context.reader;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import edu.a1.book.Book;
import edu.a1.borrow.Borrow;
import edu.a1.database.BookManagement;
import edu.a1.database.BorrowManagement;
import edu.a1.database.UserManagement;
import edu.a1.system.LibrarySystem;
import edu.a1.system.User;

/**
 * Precondition: A reader is logged in
 * 
 * @author Guanyuming He, Kemu Xu
 */
public class NormalReaderContext implements ReaderContext  {

    protected final List<Borrow> borrowHistory;
    private User reader;
    UserManagement userStorage;
    BookManagement bookStorage;
    BorrowManagement borrowStorage;

    /**
     * @param history loaded by the system from the database.
     */
    public NormalReaderContext(List<Borrow> history) {
        this.borrowHistory = history;
        userStorage = new UserManagement();
        bookStorage = new BookManagement();
        borrowStorage = new BorrowManagement();
        reader = userStorage.findByUsername(history.get(0).getUsername());
    }

    public User getReader() {
        return reader;
    }

    public List<Borrow> getBorrowHistory() {
        return borrowHistory;
    }

    public UserManagement getUserStorage() {
        return userStorage;
    }

    public BookManagement getBookStorage() {
        return bookStorage;
    }

    public BorrowManagement getBorrowStorage() {
        return borrowStorage;
    }

    /**
     * Precondition:
     *  1. the book exists in the library
     *  2. the number of copies to borrow <= the number of available copies
     *  3. an internal algorithm deems that the user is able to borrow this many books for this long. (this time is the same for everyone)
     *  Possible implementation may take past borrow history and currently unreturned books into account.
     * 
     * Postcondition:
     *  1. number of available copies of the book in the system -= number of copies to borrow
     *  2. ALL of the information about the borrow is stored in the reader's database entry in some way.
     */
    @Override
    public void borrowBook(Book book, int numCopies) {
        List<Book> statisfiedBooks = bookStorage.findByBookName(book.getBookName());
        boolean bookExits = (statisfiedBooks != null);

        // check existence
        if (bookExits){
            Book instoreBook = statisfiedBooks.get(0);
            int availableNum = instoreBook.getQuantity();

            // check num
            if (availableNum >= numCopies){

                //update num
                Book newBook = instoreBook;
                newBook.setQuantity(availableNum - numCopies);
                bookStorage.replace(instoreBook, newBook);

                //save in storage
                Borrow borrow = new Borrow(reader.getUsername(), book.getISBN(), numCopies);
                borrowStorage.save(borrow);

                System.out.println("success borrow");
            }else{
                System.out.println("numCopies exceeds availableNum");
            }
        }else{
            System.out.println("this book is not in our library");
        }
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
        List<Borrow> borrowHistory = borrowStorage.findByISBN(book.getISBN());

        // check the user's borrow history includes the book
        if(borrowHistory == null){
            System.out.println("no borrow history");
        }else{
            List<Borrow> borrows = new ArrayList<>();

            // check whether the book has all been returned
            for (Borrow borrow : borrowHistory) {
                if (borrow.getNumReturned() <= borrow.getNumBorrowed()) {
                    borrows.add(borrow);
                }
            }
            if (borrows == null){
                System.out.println("borrow books all returned");
            }else{
                // update the return book in borrow storage
                Borrow borrowOrigin = borrows.get(0);
                Borrow borrowUpdate = borrowOrigin;
                borrowUpdate.setNumReturned(borrowUpdate.getNumReturned() + numCopies);
                borrowStorage.replace(borrowOrigin, borrowUpdate);

                //update book in book storage
                Book bookOrigin = bookStorage.findByISBN(book.getISBN());
                Book bookUpdte = bookOrigin;
                bookUpdte.setQuantity(bookUpdte.getQuantity() + numCopies);
                bookStorage.replace(bookOrigin, bookUpdte);
            }
        }
    }
    
}
