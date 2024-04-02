package edu.a1.system.cmd;

import java.util.List;

import edu.a1.system.LibrarySystem;

/**
 * This command is used to check a reader's borrow history.
 * @author Guanyuming He
 */
public class BorrowHistoryCommand implements Command {
    
    public static final String name = "borrow-history";

    @Override
    public void handle(List<String> arguments) {
        
        if(arguments.size() != 1) {
            throw new IllegalArgumentException("The number of arguments is incorrect for " + name);
        }

        // Check the argument
        String username = arguments.get(0);
        if(username.equals("admin")) {
            throw new IllegalArgumentException("The admin cannot borrow, so he has no history.");
        }

        // Check the logged in user's access
        var auth = LibrarySystem.authenticator;
        boolean accessGranted = false;
        if(auth.isAdmin()) {
            // OK: the admin can check any user's borrow history.
            accessGranted = true;
        }
        else if(auth.isReader()) {
            // A reader can only check his own borrow history.
            accessGranted = auth.getLoggedInUser().getUsername().equals(username);
        }
        if(!accessGranted) {
            throw new IllegalStateException("The logged in user doesn't have access to check " + username + "'s history.");
        }

        // Now everything is fine. Print the borrow history.
        var bdb = LibrarySystem.borrowStorage;
        var borrows = bdb.findByUsername(username);
        for(var b : borrows) {
            b.calculateFine();
            LibrarySystem.getIO().writeTo(b.toString());
        }

    }

    @Override
    public String helpMessage() {
        return Command.generateHelpMessage(
            name + " <username>", 
            "Check the borrow history of the reader of username", 
            List.of(),
            List.of("<username>: the username of the reader.")
        );
    }
}
