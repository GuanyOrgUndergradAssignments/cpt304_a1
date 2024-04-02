package edu.a1.borrow;
import edu.a1.database.BorrowManagement;
import edu.a1.database.BorrowManager;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Borrow implements Serializable {
    // attributes
    private int borrowID;
    private String username;
    private String ISBN;
    private int numBorrowed;
    // -1 stands for no return
    private int numReturned;
    private LocalDateTime borrowedDate;
    private LocalDateTime declaredReturnDate;
    // Null if not returned
    private LocalDateTime returnedDate;
    // Stored to database
    // but only meaningful if fine != 0.0f
    private boolean finePaid = false;

    // Not stored in database
    // calculated whenever used.
    private float fine;
    private final float FINEPERDAY = 0.5f;
    private final int BORROWDAY = 30;

    private BorrowManagement borrowManagement;

    /**
     * SQL needs the default ctor.
     */
    public Borrow(String username, String ISBN, int numBorrowed) {
        borrowManagement = new BorrowManagement();
        // set ID
        int maxID = Integer.MIN_VALUE;

        for (Borrow borrow : borrowManagement.findAll()) {
            int currentID = borrow.getBorrowID();
            if (currentID > maxID) {
                maxID = currentID;
            }
        }
        this.borrowID = maxID + 1;
        this.username = username;
        this.ISBN = ISBN;
        this.numBorrowed = numBorrowed;
        this.numReturned = -1;
        this.borrowedDate = LocalDateTime.now();
        this.declaredReturnDate = borrowedDate.plus(BORROWDAY, ChronoUnit.DAYS);
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
        setReturnedDate(LocalDateTime.now());
        calculateFine();
        finePaid = true;
    }

    /**
     * After a Borrow is loaded from database,
     * call this to calcualte the fine.
     * 
     * If no fine is needed, then fine := 0.0f.
     */
    public void calculateFine() {
        if(returnedDate == null) {
            returnedDate = LocalDateTime.now();
        }
        if(ChronoUnit.DAYS.between(declaredReturnDate, returnedDate) > 0){
            fine = ChronoUnit.DAYS.between(declaredReturnDate, returnedDate) * FINEPERDAY;
        }else{
            fine = 0;
        }

    }
    public float getFine() {
        return fine;
    }
    /** @return if fine != 0.0f */
    public boolean isFined() {
        calculateFine();
        return getFine() != 0.0f;
    }

    public LocalDateTime getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDateTime returnedDate) {
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

    public LocalDateTime getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDateTime startTime) {
        this.borrowedDate = startTime;
    }

    public LocalDateTime getDeclaredReturnDate() {
        return declaredReturnDate;
    }

    public void setDeclaredReturnDate(LocalDateTime endTime) {
        this.declaredReturnDate = endTime;
    }
}
