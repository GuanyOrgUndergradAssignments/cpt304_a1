package edu.a1.database;
import java.util.Date;
import java.util.List;

import edu.a1.borrow.Borrow;
import edu.a1.system.User;
public interface BorrowManager {
    void saveRecords(String fileName);
    void loadRecords(String fileName);
    void save(Borrow borrow);
    void delete(Borrow borrow);
    void replace(Borrow originalBorrow, Borrow newBorrow);
    List<Borrow> findAll();
    Borrow findByBorrowID(int borrowID);
    List<Borrow> findByUsername(String username);
    List<Borrow> findByISBN(String isbn);
    List<Borrow> findByBorrowedDate(Date borrowedDate);
    List<Borrow> findByReturnedDate(Date returnedDate);
    List<Borrow> findByDeclaredReturnDate(Date declaredReturnDate);
    Borrow findByStartTimeAndUsername(String username, Date start); /////
    Borrow findByEndTimeAndUsername(String username, Date end);/////
    boolean existRecord(int borrowID);
}



