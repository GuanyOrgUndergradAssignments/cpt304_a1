package edu.a1.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.a1.system.User;
import edu.a1.system.auth.AdminPwdChangeStrategy;
import edu.a1.system.auth.ReaderPwdChangeStrategy;

/**
 * Tests if the pwd change strategies work as intended
 * @author Guanyuming He
 */
public class TestPwdChangeStrategies {

    @Test
    public void testReaderPwdChange() {
        var st = new ReaderPwdChangeStrategy();

        User usr = new User();
        usr.setUsername("abc");
        usr.setPwd("123456");

        // the same password
        assertThrows(IllegalArgumentException.class, () -> {st.approveNewPassword(usr, "123456");});

        // bad passwords: length < 6
        assertFalse(st.approveNewPassword(usr, ""));
        assertFalse(st.approveNewPassword(usr, "123"));
        assertFalse(st.approveNewPassword(usr, "abcde"));

        // good passwords: length >= 6
        assertTrue(st.approveNewPassword(usr, "abcdefg"));
        assertTrue(st.approveNewPassword(usr, "1234567"));
        assertTrue(st.approveNewPassword(usr, "A187hdjb1"));
        assertTrue(st.approveNewPassword(usr, "3383a[h.n?"));
    }

    @Test
    public void testAdminPwdChange() {
        var st = new AdminPwdChangeStrategy();

        User usr = new User();
        usr.setUsername("admin");
        usr.setPwd("0hd1823y4nb0IHG8d123d.");

        // the same password
        assertThrows(IllegalArgumentException.class, () -> {st.approveNewPassword(usr, usr.getPwd());});

        // bad passwords: length < 12
        assertFalse(st.approveNewPassword(usr, ""));
        assertFalse(st.approveNewPassword(usr, "123"));
        assertFalse(st.approveNewPassword(usr, "abcdefg"));

        // bad passwords: only not containing both letters and numbers
        assertFalse(st.approveNewPassword(usr, "aB!Cdefghijkl?mnopq"));
        assertFalse(st.approveNewPassword(usr, "12345678[]901112"));

        // good passwords:
        assertTrue(st.approveNewPassword(usr, "aBCdef2345ghijklmnopq"));
        assertTrue(st.approveNewPassword(usr, ".?19snGl912nkl8mb1"));
    }
}
