package edu.a1.system.cmd;

import java.util.List;

import edu.a1.system.LibrarySystem;

/**
 * Handles the command "exit"
 * @author Guanyuming He
 */
public class ExitCommand implements Command {

    @Override
    public void handle(List<String> arguments) {
        LibrarySystem.exit();
    }

    @Override
    public String helpMessage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'helpMessage'");
    }
    
}
