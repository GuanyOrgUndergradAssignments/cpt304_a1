package edu.a1.system.cmd;

import java.util.List;

import edu.a1.system.LibrarySystem;

public class LoginCommand implements Command {

    @Override
    public void handle(List<String> arguments) {

        if(arguments.size() != 2) {
            // the situation with --help is already handled universally in LibrarySystem.java
            throw new IllegalArgumentException("the number of arguments must be 2.");
        }

        if(LibrarySystem.authenticator.loggedIn()) {
            throw new IllegalStateException("already logged in.");
        }

        // try to log in now.
        
    }

    @Override
    public String helpMessage() {

        return Command.generateHelpMessage(
            "login USERNAME PASSWORD",
            "log in the user with USERNAME",
            List.of(),
            List.of("USERNAME: the username of the user.", "PASSWORD: the password of the user.")
        );
    }
    
}
