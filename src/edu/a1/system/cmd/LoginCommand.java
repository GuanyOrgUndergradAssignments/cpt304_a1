package edu.a1.system.cmd;

import java.util.List;

import edu.a1.system.ConsoleInteraction;
import edu.a1.system.LibrarySystem;

/**
 * Handles the command "login"
 * @author Guanyuming He
 */
public class LoginCommand implements Command {

    public static final String name = "login";

    @Override
    public void handle(List<String> arguments) {

        if(arguments.size() != 2) {
            // the situation with --help is already handled universally in LibrarySystem.java
            throw new IllegalArgumentException("the number of arguments must be 2.");
        }

        // try to log in now.
        var username = arguments.get(0);
        var password = arguments.get(1);
        LibrarySystem.authenticator.login(username, password);

        // no exception thrown means success.
        ConsoleInteraction.writeToConsole("Logged in as " + username);
    }

    @Override
    public String helpMessage() {

        return Command.generateHelpMessage(
            name + " USERNAME PASSWORD",
            "log in the user with USERNAME",
            List.of(),
            List.of("USERNAME: the username of the user.", "PASSWORD: the password of the user.")
        );
    }
    
}
