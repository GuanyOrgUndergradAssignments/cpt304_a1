package edu.a1.tests;

import edu.a1.book.Book;
import edu.a1.borrow.Borrow;
import edu.a1.borrow.FinedReaderCtxDecorator;
import edu.a1.database.BookManagement;
import edu.a1.database.BorrowManagement;
import edu.a1.database.UserManagement;
import edu.a1.system.LibrarySystem;
import edu.a1.system.User;
import edu.a1.system.auth.SystemAuthenticator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestFinedReaderCtxDecorator {

    UserManagement userStorage = new UserManagement();
    BookManagement bookStorage = new BookManagement();
    BorrowManagement borrowStorage = new BorrowManagement();

    private static StringBuilder inputBuffer;

    @BeforeAll
    public static void initLibrarySystem() {
        inputBuffer = new StringBuilder("");
        LibrarySystem.initLibrarySystem(new IOInteractionForTests(inputBuffer));
    }
    @Test
    public void testDefault() {
        User reader = new User("kemu", "456");
        userStorage.save(reader);
        Book book = new Book("Foundations of Computer Vision", "9780262048972", 1, "Computer Science", "2024", "Antonio Torralba, Phillip Isola and William T. Freeman", 90.00);
        bookStorage.save(book);
        Borrow borrow = new Borrow("kemu", "9780262048972", 1);

        List<Borrow> borrowHistory = new ArrayList<>();
        borrowHistory.add(borrow);

        FinedReaderCtxDecorator finedReaderCtxDecorator = new FinedReaderCtxDecorator(borrowHistory);
        finedReaderCtxDecorator.borrowBook(book, 1);
        finedReaderCtxDecorator.returnBook(book, 1);
    }
}
