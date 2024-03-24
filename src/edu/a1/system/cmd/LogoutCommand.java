package edu.a1.system.cmd;

import java.util.List;

import edu.a1.system.ConsoleInteraction;
import edu.a1.system.LibrarySystem;

/**
 * Handles the command "logout"
 * @author Guanyuming He
 */
public class LogoutCommand implements Command {

    public static final String name = "logout";

    @Override
    public void handle(List<String> arguments) {

        LibrarySystem.authenticator.logout();

        // no exception thrown means success.
        ConsoleInteraction.writeToConsole("Logged out.");
    }

    @Override
    public String helpMessage() {
        
        return Command.generateHelpMessage(
            name,
            "log out the currently logged in user.",
            List.of(),
            List.of()
        );

    }
    
}
