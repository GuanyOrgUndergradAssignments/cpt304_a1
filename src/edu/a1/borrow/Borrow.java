package edu.a1.borrow;
import java.sql.Date;

public class Borrow {
    private int borrowID;
    private String username;
    private int bookID;
    private int numBorrowed;
    private int numReturned;
    private Date borrowedDate;
    private Date declaredReturnDate;
    // Null if not returned
    private Date returnedDate;
    // Stored to database
    // but only meaningful if fine != 0.0f
    private boolean finePaid = false;

    // Not stored in database
    // calculated whenever used.
    private float fine = 0.0f;

    /**
     * SQL needs the default ctor.
     */
    public Borrow() {}

    public boolean getFinePaid() {
        return finePaid;
    }
    public void setFinePaid(boolean finePaid) {
        this.finePaid = finePaid;
    }
    /**
     * If the system decides that the fine is paid, then call this.
     */
    public void payFine() {
        finePaid = true;
    }

    /**
     * After a Borrow is loaded from database,
     * call this to calcualte the fine.
     * 
     * If no fine is needed, then fine := 0.0f.
     */
    public void calculateFine() {
        throw new RuntimeException("Not implemented");
    }
    public float getFine() {
        return fine;
    }
    /** @return if fine != 0.0f */
    public boolean isFined() { return getFine() != 0.0f; }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public int getBorrowID() {
        return borrowID;
    }

    public void setBorrowID(int borrowID) {
        this.borrowID = borrowID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getNumReturned() {
        return numReturned;
    }

    public void setNumReturned(int numReturned) {
        this.numReturned = numReturned;
    }

    public int getNumBorrowed() {
        return numBorrowed;
    }

    public void setNumBorrowed(int number) {
        this.numBorrowed = number;
    }

    /** @return numBorrowed == numReturned */
    public boolean returnedAllBooks() {
        return numBorrowed == numReturned;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date startTime) {
        this.borrowedDate = startTime;
    }

    public Date getDeclaredReturnDate() {
        return declaredReturnDate;
    }

    public void setDeclaredReturnDate(Date endTime) {
        this.declaredReturnDate = endTime;
    }
}
