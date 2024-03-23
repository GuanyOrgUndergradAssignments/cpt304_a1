package edu.a1.system.cmd;

import java.util.List;

/**
 * Defines the handler of a command.
 * 
 * @author Guanyuming He
 */
public interface Command {
    /**
     * Handles the command according to the passed in arguments.
     * @param arguments
     */
    public void handle(List<String> arguments);

    /**
     * @return the help message of the command.
     */
    public String helpMessage();
}
