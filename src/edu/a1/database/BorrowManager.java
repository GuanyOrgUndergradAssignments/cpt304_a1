package edu.a1.database;
import java.sql.Date;
import java.util.List;

import edu.a1.borrow.Borrow;
import edu.a1.system.User;
public interface BorrowManager {
    void save(Borrow borrow);
    void delete(Borrow borrow);
    void replace(Borrow borrow);
    Borrow findByStartTimeAndUsername(User user, Date start);
    Borrow findByEndTimeAndUsername(User user, Date end);
    List<Borrow> findByUsername(String username);
    List<Borrow> findByISBN(String isbn);
    boolean existId(String id);
}



