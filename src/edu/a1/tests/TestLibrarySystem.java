package edu.a1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.a1.system.LibrarySystem;
import edu.a1.system.cmd.AdminManCommand;
import edu.a1.system.cmd.BorrowHistoryCommand;
import edu.a1.system.cmd.ChangePwdCommand;
import edu.a1.system.cmd.ExitCommand;
import edu.a1.system.cmd.LoginCommand;
import edu.a1.system.cmd.LogoutCommand;
import edu.a1.system.cmd.QueryBookCommand;
import edu.a1.system.cmd.ReaderBookOpCommand;

/**
 * Tests if the system works as intended.
 * @author Guanyuming He
 */
public class TestLibrarySystem {
    
    private static StringBuilder inputBuffer;

    @BeforeAll
    public static void initLibrarySystem() {
        inputBuffer = new StringBuilder("");
        LibrarySystem.initLibrarySystem(new IOInteractionForTests(inputBuffer));
    }

    /**
     * Tests if the system contains all the commands
     */
    @Test
    public void testContainsAllCommands() {
        var commands = LibrarySystem.getCommands();

        assertEquals(8, commands.size());
        assertTrue(commands.containsKey(AdminManCommand.name));
        assertTrue(commands.containsKey(BorrowHistoryCommand.name));
        assertTrue(commands.containsKey(ChangePwdCommand.name));
        assertTrue(commands.containsKey(ExitCommand.name));
        assertTrue(commands.containsKey(LoginCommand.name));
        assertTrue(commands.containsKey(LogoutCommand.name));
        assertTrue(commands.containsKey(QueryBookCommand.name));
        assertTrue(commands.containsKey(ReaderBookOpCommand.name));
    }

    /**
     * Tests if it can successfully split a command line into necessary parts
     */
    @Test
    public void testInterpretCommand() {

        // name only
        var res = LibrarySystem.interpretCommand("cmdname");
        assertEquals("cmdname", res.name);
        assertTrue(res.args.isEmpty());

        // name and options
        res = LibrarySystem.interpretCommand("abc --123 -r");
        assertEquals("abc", res.name);
        assertEquals(List.of("--123", "-r"), res.args);

        // name and pars
        res = LibrarySystem.interpretCommand("login abc def");
        assertEquals("login", res.name);
        assertEquals(List.of("abc", "def"), res.args);

        // name and all
        res = LibrarySystem.interpretCommand("rm -r /media/nvme0/");
        assertEquals("rm", res.name);
        assertEquals(List.of("-r", "/media/nvme0/"), res.args);

    }
}
