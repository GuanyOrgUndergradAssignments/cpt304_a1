package edu.a1.system;

import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.a1.system.auth.SystemAuthenticator;
import edu.a1.database.BookManagement;
import edu.a1.database.BorrowManagement;
import edu.a1.database.UserManagement;
import edu.a1.system.cmd.AdminManCommand;
import edu.a1.system.cmd.BorrowHistoryCommand;
import edu.a1.system.cmd.ChangePwdCommand;
import edu.a1.system.cmd.Command;
import edu.a1.system.cmd.ExitCommand;
import edu.a1.system.cmd.LoginCommand;
import edu.a1.system.cmd.LogoutCommand;
import edu.a1.system.cmd.QueryBookCommand;
import edu.a1.system.cmd.ReaderBookOpCommand;
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
   
    // Internal data
    private static Map<String, Command> commands; // will be immutable
    private static boolean exit = false;

    // System states
    public static SystemAuthenticator authenticator;
    public static Date today;

    // System contexts
    public static UnprivilegedQueryContext unprivilegedQueryContext;
    public static SystemAdminContext systemAdminContext;

    // Database interfaces
    public static BookManagement bookStorage;
    public static BorrowManagement borrowStorage;
    public static UserManagement userStorage;

    /**
     * Use static methods only.
     * Do not create an instance.
     */
    private LibrarySystem() {
        throw new UnsupportedOperationException("Use static methods only. Do not create an instance.");
    }

    /**
     * commands will be created immutable, but itself is not final.
     * Therefore, one can only get it.
     * @return
     */
    public static Map<String, Command> getCommands() {
        return commands;
    }

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

        // init the states
        {
            today = findToday();
            authenticator = new SystemAuthenticator();
        }

        // Create the contexts
        {
            unprivilegedQueryContext = new UnprivilegedQueryContext();
            systemAdminContext = new SystemAdminContext();
        }

        // create database interfaces
        {
            bookStorage = new BookManagement();
            borrowStorage = new BorrowManagement();
            userStorage = new UserManagement();
        }

        // Create the command handlers
        {
            var temp = new HashMap<String, Command>();
        
            // add the commands
            commands.put(ExitCommand.name, new ExitCommand());
            commands.put(LoginCommand.name, new LoginCommand());
            commands.put(LogoutCommand.name, new LogoutCommand());
            commands.put(ChangePwdCommand.name, new ChangePwdCommand());
            commands.put(AdminManCommand.name, new AdminManCommand());
            commands.put(BorrowHistoryCommand.name, new BorrowHistoryCommand());
            commands.put(QueryBookCommand.name, new QueryBookCommand());
            commands.put(ReaderBookOpCommand.name, new ReaderBookOpCommand());
    
            commands = Collections.unmodifiableMap(temp);
        }
        
    }

    public static class CommandResult {

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
     * @return names and args contained in CommandResult
     */
    public static CommandResult interpretCommand(String commandLine) {
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
            // Errors from commands are reported as exceptions.
            // print error messages to the console.
            try {
                handleCommandLine();
            }
            catch(Exception e) {
                ConsoleInteraction.writeToConsole("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        LibrarySystem.initLibrarySystem();
        LibrarySystem.mainLoop();

    }
}