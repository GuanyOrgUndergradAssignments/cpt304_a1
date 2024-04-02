package edu.a1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.a1.book.Book;
import edu.a1.database.BookManager;
import edu.a1.database.UserManager;
import edu.a1.system.LibrarySystem;
import edu.a1.system.User;

/**
 * Test the command admin-man.
 * It is one of the most complex commands.
 * 
 * @author Guanyuming He
 */
public class TestAdminManCmd {

    private static UserManager usrDB;
    private static BookManager bookDB;
    private static StringBuilder inputBuffer;
    private List<User> stashedUsrs;
    private List<Book> stashedBooks;

    @BeforeAll
    public static void initLibSystem() {

        inputBuffer = new StringBuilder("");
        LibrarySystem.initLibrarySystem(new IOInteractionForTests(inputBuffer));
        usrDB = LibrarySystem.userStorage;
        bookDB = LibrarySystem.bookStorage;

    }

    @BeforeEach
    public void clearDataBase() {
        stashedUsrs = new ArrayList<>(usrDB.findAll()); 
        stashedBooks = new ArrayList<>(bookDB.findAll());

        // clear the databases
        for(var u : stashedUsrs) {
            if(!u.getUsername().equals("admin")) {
                usrDB.delete(u);
            }
        }
        for(var b : stashedBooks) {
            bookDB.delete(b);
        }
    }

    /**
     * Log out after each test.
     */
    @AfterEach
    public void logOutAndRestoreDB() {
        LibrarySystem.authenticator.logout();

        // clear the databases again
        var temp1 = new ArrayList<>(usrDB.findAll()); 
        var temp2 = new ArrayList<>(bookDB.findAll());
        for(var u : temp1) {
            if(!u.getUsername().equals("admin")) {
                usrDB.delete(u);
            }
        }
        for(var b : temp2) {
            bookDB.delete(b);
        }

        // restore the databases
        for(var u : stashedUsrs) {
            if(!u.getUsername().equals("admin")) {
                usrDB.save(u);
            }
        }
        for(var b : stashedBooks) {
            bookDB.save(b);
        }
    }

    private static void inputCommand(String cmd) {
        inputBuffer.append(cmd);
        LibrarySystem.handleCommandLine();
    }

    private static void loginAsAdmin() {
        User admin = usrDB.findByUsername("admin");
        LibrarySystem.authenticator.login(admin.getUsername(), admin.getPwd());
    }

    @Test
    public void testAddUser() {

        // not logged in
        assertThrows(IllegalStateException.class, () -> inputCommand("admin-man --add user"));

        loginAsAdmin();

        // incorrect number of arguments
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --add user"));
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --add user 12345 abcde 12345"));

        // correct, but password is not approved
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --add user test_12345 abcde"));
        
        // correct 
        inputCommand("admin-man --add user test_12345 abcdefg");
        assertTrue(usrDB.existUser("test_12345"));
        User usr = usrDB.findByUsername("test_12345");
        assertEquals("abcdefg", usr.getPwd());
    }

    @Test
    public void testRmUser() {

        // not logged in
        assertThrows(IllegalStateException.class, () -> inputCommand("admin-man --rm user"));

        loginAsAdmin();

        // incorrect number of arguments
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --rm user"));
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --rm user 12345 abcde"));

        // user does not exist
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --rm user test_12345"));

        // correct
        User usr = new User("test_12345", "abcdefg");
        usrDB.save(usr);
        inputCommand("admin-man --rm user test_12345");
        assertFalse(usrDB.existUser(usr.getUsername()));
    }

    @Test
    public void testAddBook() {
        
        // not logged in
        assertThrows(IllegalStateException.class, () -> inputCommand("admin-man --add book"));

        loginAsAdmin();

        // incorrect number of arguments
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --add book"));
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --add book 12345 abcde 12345"));
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --add book test_12345 2 test 1984 1984 history 12.0 abc"));

        // incorrect format of arguments
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --add book test_12345 ab test 1984 1984 history 12.0"));
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --add book test_12345 2 test 1984 1984 history 12/ac"));
        
        // correct 
        inputCommand("admin-man --add book test_12345 2 test 1984 1984 history 12.0");
        assertTrue(bookDB.existBook("test_12345"));
        Book expected = new Book("1984", "test_12345", 2, "history", "1984", "test", 12.0);
        assertEquals(expected, bookDB.findByISBN("test_12345"));

    }

    @Test
    public void testRmBook() {

        // not logged in
        assertThrows(IllegalStateException.class, () -> inputCommand("admin-man --rm book"));

        loginAsAdmin();

        // incorrect number of arguments
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --rm book"));
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --rm book 12345 abcde 12345"));

        // no book of such isbn exists
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --rm book 12345"));

        // correct
        Book expected = new Book("1984", "test_12345", 2, "history", "1984", "test", 12.0);
        bookDB.save(expected);
        inputCommand("admin-man --rm book test_12345");
        assertFalse(bookDB.existBook(expected.getISBN()));
    }

    @Test
    public void testListUsers() {

        // not logged in
        assertThrows(IllegalStateException.class, () -> inputCommand("admin-man --list user"));

        loginAsAdmin();

        // incorrect number of arguments
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --list"));
        assertThrows(IllegalArgumentException.class, () -> inputCommand("admin-man --list user 12345"));

        // now there are no users except the admin
        // first drain the command output buffer
        var outBuffer = ((IOInteractionForTests)LibrarySystem.getIO()).myOwnedBuffer;
        outBuffer.delete(0, outBuffer.length());
        // now execute the command
        inputCommand("admin-man --list user");
        var out = outBuffer.toString();
        assertEquals("admin\nAdministrative operation completed successfully.\n", out);
        outBuffer.delete(0, outBuffer.length());
        // now add some user
        User newUsr = new User("abc", "123456");
        usrDB.save(newUsr);
        inputCommand("admin-man --list user");
        out = outBuffer.toString();
        assertEquals("admin\nabc\nAdministrative operation completed successfully.\n", out);
    }
}
