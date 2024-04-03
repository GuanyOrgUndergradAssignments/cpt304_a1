package edu.a1.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import edu.a1.book.Book;
import edu.a1.system.IOInteraction;
import edu.a1.system.LibrarySystem;
import edu.a1.system.User;
import edu.a1.system.cmd.QueryBookCommand;
import edu.a1.database.*;

public class TestQueryBookCmd {

    private static BookManager bookDB;
    private static StringBuilder inputBuffer;

    @BeforeEach
    public static void initLibSystem() {

        inputBuffer = new StringBuilder("");
        LibrarySystem.initLibrarySystem(new IOInteractionForTests(inputBuffer));
        usrDB = LibrarySystem.userStorage;
        bookDB = LibrarySystem.bookStorage;

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
        
        QueryBookCommand queryBookCommand = new QueryBookCommand();
        queryBookCommand.handle(Arrays.asList("--ISBN", "1234567890123"));

        assertTrue(inputBuffer.toString().contains("Book found:"));
        assertTrue(inputBuffer.toString().contains(book.toString()));
        
    }

    @Test
    public void testHandleAuthorBookNameQuery() {
        Book book = new Book("MeowMeow", "1234567890123", 5, "Fantasy", "2022", "Cat", 19.99);
        bookDB.save(book);
        
        QueryBookCommand queryBookCommand = new QueryBookCommand();
        queryBookCommand.handle(Arrays.asList("--author-and-book-name", "Cat", "MeowMeow"));

        assertTrue(inputBuffer.toString().contains("Book found:"));
        assertTrue(inputBuffer.toString().contains(book.toString()));
        
    }
    
    @Test
    public void testHandleCategoryQuery() {
        Book book = new Book("MeowMeow", "1234567890123", 5, "Fantasy", "2022", "Cat", 19.99);
        bookDB.save(book);
        
        QueryBookCommand queryBookCommand = new QueryBookCommand();
        queryBookCommand.handle(Arrays.asList("--category", "Fantasy"));

        assertTrue(inputBuffer.toString().contains("Book found:"));
        assertTrue(inputBuffer.toString().contains(book.toString()));
        
    }

    @Test
    public void testHandleAllQuery() {
        Book book = new Book("MeowMeow", "1234567890123", 5, "Fantasy", "2022", "Cat", 19.99);
        bookDB.save(book);
        
        QueryBookCommand queryBookCommand = new QueryBookCommand();
        queryBookCommand.handle(Arrays.asList("--all"));

        assertTrue(inputBuffer.toString().contains("Book found:"));
        assertTrue(inputBuffer.toString().contains(book.toString()));
        
    }

    @Test
    public void testHandleNoBookFound() {
        QueryBookCommand queryBookCommand = new QueryBookCommand();
        queryBookCommand.handle(Arrays.asList("--ISBN", "1234567890123"));

        assertTrue(inputBuffer.toString().contains("No book found."));
        
    }

    @Test
    public void testHandleNoBookCategory() {
        QueryBookCommand queryBookCommand = new QueryBookCommand();
        queryBookCommand.handle(Arrays.asList("--category", "Fantasy"));

        assertTrue(inputBuffer.toString().contains("No book has the category."));
        
    }

    @Test
    public void testHandleNoBooksExist() {
        QueryBookCommand queryBookCommand = new QueryBookCommand();
        queryBookCommand.handle(Arrays.asList("--all"));

        assertTrue(inputBuffer.toString().contains("No books exist."));
        
    }

    @Test
    public void testHandleNoBookAuthorAndBookName() {
        QueryBookCommand queryBookCommand = new QueryBookCommand();
        queryBookCommand.handle(Arrays.asList("--author-and-book-name", "Cat", "MeowMeow"));

        assertTrue(inputBuffer.toString().contains("No book has the author and bookname."));
        
    }

    @Test
    public void testIllegalArgumentException() {
        QueryBookCommand queryBookCommand = new QueryBookCommand();

        // Test with an invalid number of arguments for "--ISBN"
        assertThrows(IllegalArgumentException.class, () -> queryBookCommand.handle(Collections.singletonList("--ISBN")));
        
        // Test with an invalid number of arguments for "--author-and-book-name"
        assertThrows(IllegalArgumentException.class, () -> queryBookCommand.handle(Arrays.asList("--author-and-book-name", "author")));

        // Add more test cases for other options if needed
    }
}
