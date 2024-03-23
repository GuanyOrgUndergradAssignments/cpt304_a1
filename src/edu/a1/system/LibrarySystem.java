package edu.a1.system;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.a1.system.cmd.Command;
import edu.a1.system.cmd.ExitCommand;
import edu.a1.system.context.SystemAdminContext;
import edu.a1.system.context.UnprivilegedQueryContext;
import edu.a1.system.context.reader.NoReaderContext;
import edu.a1.system.context.reader.ReaderContext;

/**
 * Interprets the commands the user enters into the system
 * and delegates them to corresponding components of the system.
 * 
 * @author Guanyuming He
 */
public final class LibrarySystem {
   
    private static Map<String, Command> commands;
    private static boolean exit = false;

    public static Date today;

    public static UnprivilegedQueryContext unprivilegedQueryContext;
    public static SystemAdminContext systemAdminContext;
    public static ReaderContext readerContext;

    /**
     * @return the date today
     */
    private static Date findToday() {
        throw new RuntimeException("Not implemented.");
    }

    /**
     * Creates the commands
     */
    public static void initLibrarySystem() {

        today = findToday();

        // Create the components
        {
            unprivilegedQueryContext = new UnprivilegedQueryContext();
            systemAdminContext = new SystemAdminContext();
            // initially no one is logged in.
            readerContext = new NoReaderContext();
        }

        // Create the command handlers
        {
            var temp = new HashMap<String, Command>();
        
            // add the commands
            commands.put("exit", new ExitCommand());
            throw new RuntimeException("Not fully implemented.");
    
            // commands = Collections.unmodifiableMap(temp);
        }
    }

    private static class CommandResult {

        public CommandResult(String n, List<String> a) {
            this.name = n; 
            this.args = Collections.unmodifiableList(a);
        }

        public final String name;
        public final List<String> args;
    }

    /**
     * Turns a command line string into name and args.
     * @param commandLine
     * @return
     */
    private static CommandResult interpretCommand(String commandLine) {
        throw new RuntimeException("Not implemented.");
    }

    private static void handleCommandLine() {

        String cmdLine = ConsoleInteraction.readFromConsole();
        CommandResult res = interpretCommand(cmdLine);

        if(!commands.containsKey(res.name)) {
            throw new UnsupportedOperationException("The command with name: " + res.name + " is not supported.");
        }

        var handler = commands.get(res.name);

        // handle the help arg if --help is found
        if(res.args.size() >= 1 && res.args.get(1).equals("--help")) {
            ConsoleInteraction.writeToConsole(handler.helpMessage());
        }
        // otherwise use the handler.
        else {
            handler.handle(res.args);
        }      

    }

    /**
     * Should only be called by the exit command to exit the program.
     */
    public static void exit() { exit = true; }

    /**
     * Handles command until the exit command is called.
     */
    public static void mainLoop() {
        while(!exit) {
            handleCommandLine();
        }
    }

    public static void main(String[] args) {

        LibrarySystem.initLibrarySystem();
        LibrarySystem.mainLoop();

        throw new RuntimeException("Not implemented.");
    }
}