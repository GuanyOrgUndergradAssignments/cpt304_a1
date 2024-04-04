package edu.a1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import edu.a1.book.Book;
import edu.a1.system.LibrarySystem;
import edu.a1.system.cmd.QueryBookCommand;
import edu.a1.database.*;

public class TestQueryBookCmd {

    private static BookManager bookDB;
    private static StringBuilder inputBuffer;

    @BeforeAll
    public static void initLibSystem() {

        inputBuffer = new StringBuilder("");
        LibrarySystem.initLibrarySystem(new IOInteractionForTests(inputBuffer));
        bookDB = LibrarySystem.bookStorage;

    }

    @AfterEach
    public void clearDataBase() {
        List<Book> stashedBooks = new ArrayList<>(bookDB.findAll());

        // clear the databases
        for(var b : stashedBooks) {
            bookDB.delete(b);
        }
    }

    private static void inputCommand(String cmd) {
        inputBuffer.append(cmd);
        LibrarySystem.handleCommandLine();
    }

    @Test
    public void testAddBook() {
        Book book = new Book("MeowMeow", "1234567890123", 5, "Fantasy", "2022", "Cat", 19.99);
        bookDB.save(book);
        
        assertTrue(bookDB.existBook("1234567890123"));
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book("MeowMeow", "1234567890123", 5, "Fantasy", "2022", "Cat", 19.99);
        bookDB.save(book);
        bookDB.delete(book);
        
        assertFalse(bookDB.existBook("1234567890123"));
    }    

    @Test
    public void testHandleISBNQuery() {
        Book book = new Book("MeowMeow", "1234567890123", 5, "Fantasy", "2022", "Cat", 19.99);
        bookDB.save(book);

        // first drain the command output buffer
        var outBuffer = ((IOInteractionForTests)LibrarySystem.getIO()).myOwnedBuffer;
        outBuffer.delete(0, outBuffer.length());

        // Set up the QueryBookCommand
        inputCommand("query-book --ISBN 1234567890123");

        var out = outBuffer.toString();

        assertTrue(out.contains("Book found:"));
        assertTrue(out.contains(book.toString()));
        
        outBuffer.delete(0, outBuffer.length());

    }

    @Test
    public void testHandleAuthorBookNameQuery() {
        Book book = new Book("MeowMeow", "1234567890123", 5, "Fantasy", "2022", "Cat", 19.99);
        bookDB.save(book);

        // first drain the command output buffer
        var outBuffer = ((IOInteractionForTests)LibrarySystem.getIO()).myOwnedBuffer;
        outBuffer.delete(0, outBuffer.length());

        // Set up the QueryBookCommand
        inputCommand("query-book --author-and-book-name Cat MeowMeow");

        var out = outBuffer.toString();

        assertTrue(out.contains("Book found:"));
        assertTrue(out.contains(book.toString()));
        
        outBuffer.delete(0, outBuffer.length());
    }
    
    @Test
    public void testHandleCategoryQuery() {
        Book book = new Book("MeowMeow", "1234567890123", 5, "Fantasy", "2022", "Cat", 19.99);
        bookDB.save(book);

        // first drain the command output buffer
        var outBuffer = ((IOInteractionForTests)LibrarySystem.getIO()).myOwnedBuffer;
        outBuffer.delete(0, outBuffer.length());

        // Set up the QueryBookCommand
        inputCommand("query-book --category Fantasy");

        var out = outBuffer.toString();

        assertTrue(out.contains(book.toString()));
        
        outBuffer.delete(0, outBuffer.length());
        
    }

    @Test
    public void testHandleAllQuery() {
        List<Book> testBooks = new ArrayList<>();
        testBooks.add(new Book("MeowMeow", "1234567890123", 5, "Fantasy", "2022", "Cat", 19.99));
        testBooks.add(new Book("MeowMeow2", "1234567890124", 5, "Fantasy", "2022", "Cat", 19.99));

        for (Book book : testBooks) {
            bookDB.save(book);
        }

        // first drain the command output buffer
        var outBuffer = ((IOInteractionForTests)LibrarySystem.getIO()).myOwnedBuffer;
        outBuffer.delete(0, outBuffer.length());

        // Set up the QueryBookCommand
        inputCommand("query-book --all ");
        var out = outBuffer.toString();

        // Assert that the expected output is present in the output buffer
        assertTrue(out.contains("A total of " + testBooks.size() + " books found."));
        for (int i = 0; i < testBooks.size(); ++i) {
            assertTrue(out.contains("Book " + i + ":"));
            assertTrue(out.contains(testBooks.get(i).toString()));
        }

        // Clear the output buffer after running the test
        outBuffer.delete(0, outBuffer.length());        
    }

    @Test
    public void testHandleNoBookFound() {
        // first drain the command output buffer
        var outBuffer = ((IOInteractionForTests)LibrarySystem.getIO()).myOwnedBuffer;
        outBuffer.delete(0, outBuffer.length());

        // Set up the QueryBookCommand
        inputCommand("query-book --ISBN 1234567890123");

        var out = outBuffer.toString();

        assertTrue(out.contains("No book has the ISBN."));

        outBuffer.delete(0, outBuffer.length());
    }

    @Test
    public void testHandleNoBookCategory() {
        // first drain the command output buffer
        var outBuffer = ((IOInteractionForTests)LibrarySystem.getIO()).myOwnedBuffer;
        outBuffer.delete(0, outBuffer.length());

        // Set up the QueryBookCommand
        inputCommand("query-book --category Fantasy");

        var out = outBuffer.toString();

        assertTrue(out.contains("No book has the category."));
        
        outBuffer.delete(0, outBuffer.length());
        
    }

    @Test
    public void testHandleNoBooksExist() {
        // first drain the command output buffer
        var outBuffer = ((IOInteractionForTests)LibrarySystem.getIO()).myOwnedBuffer;
        outBuffer.delete(0, outBuffer.length());

        // Set up the QueryBookCommand
        inputCommand("query-book --all ");

        var out = outBuffer.toString();

        assertTrue(out.contains("No books exist."));
        
        outBuffer.delete(0, outBuffer.length());
        
    }

    @Test
    public void testHandleNoBookAuthorAndBookName() {
        // first drain the command output buffer
        var outBuffer = ((IOInteractionForTests)LibrarySystem.getIO()).myOwnedBuffer;
        outBuffer.delete(0, outBuffer.length());

        // Set up the QueryBookCommand
        inputCommand("query-book --author-and-book-name Cat MeowMeow");

        var out = outBuffer.toString();

        assertTrue(out.contains("No book has the author and bookname."));
        
        outBuffer.delete(0, outBuffer.length());
        
    }

    @Test
    public void testIllegalArgumentException() {
        QueryBookCommand queryBookCommand = new QueryBookCommand();

        // Test with an invalid number of arguments for "--ISBN"
        assertThrows(IllegalArgumentException.class, () -> queryBookCommand.handle(Collections.singletonList("--ISBN")));
        
        // Test with an invalid number of arguments for "--author-and-book-name"
        assertThrows(IllegalArgumentException.class, () -> queryBookCommand.handle(Arrays.asList("--author-and-book-name", "author")));

        // Test with an invalid number of arguments for "--category"
        assertThrows(IllegalArgumentException.class, () -> queryBookCommand.handle(Collections.singletonList("--category")));
    }
}
