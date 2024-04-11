package edu.a1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.a1.database.UserManager;
import edu.a1.system.LibrarySystem;
import edu.a1.system.User;
import edu.a1.system.auth.SystemAuthenticator;
import edu.a1.system.cmd.LoginCommand;
import org.junit.Test;

import edu.a1.system.cmd.LogoutCommand;

public class TestLogoutCmd {

    private static StringBuilder inputBuffer;
    private static UserManager usrDB;

    @Test
    public void testHandle() {
        inputBuffer = new StringBuilder("");
        LibrarySystem.initLibrarySystem(new IOInteractionForTests(inputBuffer));
        LibrarySystem.authenticator = new SystemAuthenticator();

        usrDB = LibrarySystem.userStorage;
        usrDB.save(new User("username", "password"));
        LoginCommand loginCommand = new LoginCommand();

        List<String> validArguments = Arrays.asList("username", "password");
        loginCommand.handle(validArguments);

        LogoutCommand logoutCommand = new LogoutCommand();

        assertDoesNotThrow(() -> logoutCommand.handle(Collections.emptyList()));
    }

    @Test
    public void testHelpMessage() {
        LogoutCommand logoutCommand = new LogoutCommand();

        String expectedHelpMessage = "Usage: logout\n"
                + "Effect: log out the currently logged in user.\n"
                + "Options: \n"
                + "\t --help: displays the help message.\n";
        assertEquals(expectedHelpMessage, logoutCommand.helpMessage());
    }
}
