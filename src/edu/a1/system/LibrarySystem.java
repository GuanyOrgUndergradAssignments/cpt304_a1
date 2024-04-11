package edu.a1.system;

import java.util.Date;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.a1.system.auth.SystemAuthenticator;
import edu.a1.database.BookManagement;
import edu.a1.database.BookManager;
import edu.a1.database.BorrowManagement;
import edu.a1.database.BorrowManager;
import edu.a1.database.UserManagement;
import edu.a1.database.UserManager;
import edu.a1.system.cmd.AdminManCommand;
import edu.a1.system.cmd.BorrowHistoryCommand;
import edu.a1.system.cmd.ChangePwdCommand;
import edu.a1.system.cmd.Command;
import edu.a1.system.cmd.ExitCommand;
import edu.a1.system.cmd.LoginCommand;
import edu.a1.system.cmd.LogoutCommand;
import edu.a1.system.cmd.QueryBookCommand;
import edu.a1.system.cmd.ReaderBookOpCommand;

/**
 * Interprets the commands the user enters into the system
 * and delegates them to corresponding components of the system.
 * 
 * @author Guanyuming He
 */
public final class LibrarySystem {
   
    // IOInteraction interface.
    private static IOInteraction ioInteraction;
    public static IOInteraction getIO() { return ioInteraction; }

    // Internal data
    /** will be created immutable */
    private static Map<String, Command> commands;
    public static Map<String, Command> getCommands() { return commands; }
    private static boolean exit = false;

    // System states
    public static SystemAuthenticator authenticator;
    public static Date today;

    // Database interfaces
    public static BookManager bookStorage;
    public static BorrowManager borrowStorage;
    public static UserManager userStorage;

    // Admin default pwd
    public static final String DEFAULT_ADMIN_PASSWORD = "dh98f12bo3ujrb98";

    /**
     * Use static methods only.
     * Do not create an instance.
     */
    private LibrarySystem() {
        throw new UnsupportedOperationException("Use static methods only. Do not create an instance.");
    }

    /**
     * @return the date today
     */
    private static Date findToday() {
        return Date.from(Instant.now());
    }

    /**
     * Initialises the system.
     * @param ioInteraction how will the system interact with the user
     */
    public static void initLibrarySystem(IOInteraction io) {

        // init io
        {
            if(io == null) {
                throw new IllegalArgumentException("The IO interaction cannot be null.");
            }
            ioInteraction = io;
        }
        
        // init the states
        {
            today = findToday();
            authenticator = new SystemAuthenticator();
        }

        // create database interfaces
        {
            bookStorage = BookManagement.getInstance();
            borrowStorage = BorrowManagement.getInstance();
            userStorage = UserManagement.getInstance();
        }

        // If there isn't an admin yet, create one with the default password
        {
            if(!userStorage.existUser("admin")) {
                userStorage.save(new User("admin", DEFAULT_ADMIN_PASSWORD));
            }
        }

        // Create the command handlers
        {
            var temp = new HashMap<String, Command>();
        
            // add the commands
            temp.put(ExitCommand.name, new ExitCommand());
            temp.put(LoginCommand.name, new LoginCommand());
            temp.put(LogoutCommand.name, new LogoutCommand());
            temp.put(ChangePwdCommand.name, new ChangePwdCommand());
            temp.put(AdminManCommand.name, new AdminManCommand());
            temp.put(BorrowHistoryCommand.name, new BorrowHistoryCommand());
            temp.put(QueryBookCommand.name, new QueryBookCommand());
            temp.put(ReaderBookOpCommand.name, new ReaderBookOpCommand());
    
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

        // command:
        //      name |
        //      name SPACE args
        // args:
        //      arg
        //      args SPACE arg

        String name = null;

        int nameSeparator = commandLine.indexOf(" ");

        // If the command has only the name
        if(nameSeparator == -1) {
            name = commandLine;
            return new CommandResult(name, List.of());
        }

        // Otherwise, parse the args
        name = commandLine.substring(0, nameSeparator);
        String argsStr = commandLine.substring(nameSeparator+1);
        List<String> args = new ArrayList<>();
        int nextSpaceInd = -1;
        do {
            // find the index of the next space
            // and extract the next argument from the string.
            nextSpaceInd = argsStr.indexOf(" ");
            if(nextSpaceInd == -1) {
                // No further spaces. The whole string is the argument.
                args.add(argsStr);
                argsStr = "";
            }
            else {
                // Extract the next argument.
                args.add(argsStr.substring(0, nextSpaceInd));
                argsStr = argsStr.substring(nextSpaceInd+1);
            }
        }
        while(nextSpaceInd != -1);
        // Now the argsStr should have no more characters.
        assert(argsStr.isEmpty());

        return new CommandResult(name, args);
    }

    public static void handleCommandLine() {

        String cmdLine = ioInteraction.readLineFrom();
        CommandResult res = interpretCommand(cmdLine);

        if(!commands.containsKey(res.name)) {
            throw new UnsupportedOperationException("The command with name: " + res.name + " is not supported.");
        }

        var handler = commands.get(res.name);

        // handle the help arg if --help is found
        if(res.args.size() >= 1 && res.args.get(0).equals("--help")) {
            ioInteraction.writeTo(handler.helpMessage());
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

        assert(ioInteraction != null);

        // print the welcome message
        {
            StringBuilder welcomeMsgBuilder = new StringBuilder("");
            welcomeMsgBuilder.append("Welcome to the Library Management System\n that is developed by Group 14.\n");
            welcomeMsgBuilder.append("Authors:\n");
            welcomeMsgBuilder.append("\tBrosnan Liew\n");
            welcomeMsgBuilder.append("\tGuanyuming He\n");
            welcomeMsgBuilder.append("\tHanyu Zhang\n");
            welcomeMsgBuilder.append("\tKemu Xu\n");
            welcomeMsgBuilder.append("Commands are listed below.\n For how to use them, execute <command-name> --help.\n");
            for(var cmdName : commands.keySet()) {
                welcomeMsgBuilder.append("\t");
                welcomeMsgBuilder.append(cmdName);
                welcomeMsgBuilder.append("\n");
            }
            ioInteraction.writeTo(welcomeMsgBuilder.toString());
        }

        while(!exit) {
            // Errors from commands are reported as exceptions.
            // print error messages to the console.
            try {
                handleCommandLine();
            }
            catch(Exception e) {
                ioInteraction.writeTo("Error: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        LibrarySystem.initLibrarySystem(new ConsoleInteraction());
        LibrarySystem.mainLoop();

    }
}