package edu.a1.tests;

import edu.a1.database.UserManager;
import edu.a1.system.IOInteraction;
import edu.a1.system.LibrarySystem;
import edu.a1.system.User;
import edu.a1.system.auth.SystemAuthenticator;
import edu.a1.system.cmd.LoginCommand;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class TestLoginCmd {

    private static StringBuilder inputBuffer;
    private static UserManager usrDB;


    @Test
    public void testHandleWithValidArguments() {
        inputBuffer = new StringBuilder("");
        LibrarySystem.initLibrarySystem(new IOInteractionForTests(inputBuffer));
        LibrarySystem.authenticator = new SystemAuthenticator();

        usrDB = LibrarySystem.userStorage;
        usrDB.save(new User("username", "password"));

        LoginCommand loginCommand = new LoginCommand();

        List<String> validArguments = Arrays.asList("username", "password");
        assertDoesNotThrow(() -> loginCommand.handle(validArguments));
    }

    @Test
    public void testHandleWithInvalidArguments() {
        LoginCommand loginCommand = new LoginCommand();

        List<String> invalidArguments = Collections.singletonList("username");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> loginCommand.handle(invalidArguments));
        assertEquals("the number of arguments must be 2.", exception.getMessage());
    }

    @Test
    public void testHelpMessage() {
        LoginCommand loginCommand = new LoginCommand();

        String expectedHelpMessage = "Usage: login USERNAME PASSWORD\n" +
                "Effect: log in the user with USERNAME\n" +
                "Options: \n" +
                "\t --help: displays the help message.\n" +
                "Parameters: \n" +
                "\t USERNAME: the username of the user.\n" +
                "\t PASSWORD: the password of the user.\n";
        assertEquals(expectedHelpMessage, loginCommand.helpMessage());
    }

}
