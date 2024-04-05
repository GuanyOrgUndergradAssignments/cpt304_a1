package edu.a1.borrow;
import edu.a1.database.BorrowManagement;
import edu.a1.database.BorrowManager;
import edu.a1.system.LibrarySystem;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class Borrow implements Serializable {
    // attributes
    private int borrowID;
    private String username;
    private String ISBN;
    private int numBorrowed;
    // -1 stands for no return
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
    private float fine;
    private final float FINEPERDAY = 0.5f;
    private final int BORROWDAY = 30;

    private BorrowManager borrowStorage;

    /**
     * SQL needs the default ctor.
     */
    public Borrow(String username, String ISBN, int numBorrowed, Date declaredReturnDate) {
        borrowStorage = LibrarySystem.borrowStorage;
        // set ID
        int maxID = 0;

        for (Borrow borrow : borrowStorage.findAll()) {
            int currentID = borrow.getBorrowID();
            if (currentID > maxID) {
                maxID = currentID;
            }
        }
        this.borrowID = maxID + 1;
        this.username = username;
        this.ISBN = ISBN;
        this.numBorrowed = numBorrowed;
        this.numReturned = 0;
        this.borrowedDate = Date.from(Instant.now());
        this.declaredReturnDate = declaredReturnDate;

        this.returnedDate = null;
        this.finePaid = false;
    }

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
     * call this to calculate the fine.
     * 
     * If no fine is needed, then fine := 0.0f.
     */
    public void calculateFine() {
        // check the existence of return day
        if(returnedDate != null) {
            // fine by day
            if(ChronoUnit.DAYS.between(declaredReturnDate.toInstant(), returnedDate.toInstant()) > 0){
                fine = ChronoUnit.DAYS.between(declaredReturnDate.toInstant(), returnedDate.toInstant()) * FINEPERDAY;
            }else{
                fine = 0;
            }
        }
    }
    public float getFine() {
        return fine;
    }
    /** @return if fine != 0.0f */
    public boolean isFined() {
        return getFine() != 0.0f;
    }

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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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
