package edu.a1.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.a1.database.UserManagement;
import edu.a1.system.LibrarySystem;
import edu.a1.system.User;
import edu.a1.system.auth.SystemAuthenticator;

/**
 * Tests SystemAuthenticator
 * @author Guanyuming He
 */
public class TestAuthentication {

    private static UserManagement usrDB;

    @BeforeAll
    public static void setUpDatabase() {
        LibrarySystem.initLibrarySystem();
        usrDB = LibrarySystem.userStorage;
        usrDB.save(new User("test_abc", "123456"));
    }

    @AfterAll
    public static void cleanDatabase() {
        usrDB.delete(new User("test_abc", "123456"));
    }

    @Test
    public void testDefault() {
        var a = new SystemAuthenticator();

        assertFalse(a.loggedIn());
        assertFalse(a.isAdmin());
        assertFalse(a.isReader());
        assertEquals(null, a.getLoggedInUser());
    }

    @Test
    public void testLogin() {
        var a = new SystemAuthenticator();

        // incorrect credentials
        assertThrows(IllegalArgumentException.class, ()-> a.login("test_abc", "abcdef"));

        // success
        a.login("test_abc", "123456");
        assertTrue(a.loggedIn());
        assertTrue(a.isReader());
        assertFalse(a.isAdmin());
        assertEquals(new User("test_abc", "123456"), a.getLoggedInUser());

        // already logged in
        assertThrows(IllegalStateException.class, ()-> a.login("test_abc", "123456"));
    }

    @Test
    public void testChpwd() {
        // the strategy is tested elsewhere.
        // here I only test state.

        var a = new SystemAuthenticator();

        // not logged in
        assertThrows(IllegalStateException.class, ()-> a.changePassword("abcdefg"));
    }

}
