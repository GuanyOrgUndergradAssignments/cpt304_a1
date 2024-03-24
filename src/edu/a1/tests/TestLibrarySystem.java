package edu.a1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import edu.a1.system.LibrarySystem;

/**
 * Tests if the system works as intended.
 * @author Guanyuming He
 */
public class TestLibrarySystem {
    
    @BeforeAll
    public static void initLibrarySystem() {
        initLibrarySystem();
    }

    /**
     * Tests if the system contains all the commands
     */
    @Test
    public void testContainsAllCommands() {
        fail("Not implemented.");
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
        assertEquals(List.of("--123", "--r"), res.args);

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
