package edu.a1.system.cmd;

import java.util.List;

import edu.a1.system.ConsoleInteraction;
import edu.a1.system.LibrarySystem;

/**
 * Handles the command "chpwd"
 * @author Guanyuming He
 */
public class ChangePwdCommand implements Command {

    public static final String name = "chpwd";

    @Override
    public void handle(List<String> arguments) {

        if(arguments.size() != 1) {
            throw new IllegalArgumentException("Must have exactly one parameter.");
        }

        var newpwd = arguments.get(0);
        LibrarySystem.authenticator.changePassword(newpwd);

        // no exception means success
        LibrarySystem.getIO().writeTo("Password changed. Please log in again.");
    }

    @Override
    public String helpMessage() {
        return Command.generateHelpMessage(
            name + " NEWPWD", "sets the currently logged in user's pwd to NEWPWD, and log out.", 
            List.of(), List.of("NEWPWD: new password.")
        );
    }
    
}
