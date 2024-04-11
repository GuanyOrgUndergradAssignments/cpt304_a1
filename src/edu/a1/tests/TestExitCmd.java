package edu.a1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.Test;

import edu.a1.system.cmd.ExitCommand;

public class TestExitCmd {
    @Test
    public void testHandle() {
        ExitCommand exitCommand = new ExitCommand();

        assertDoesNotThrow(() -> exitCommand.handle(Collections.emptyList()));
    }

    @Test
    public void testHelpMessage() {
        ExitCommand exitCommand = new ExitCommand();

        String expectedHelpMessage = "Usage: exit\n" +
                "Effect: exits the system\n" +
                "Options: \n" +
                "\t --help: displays the help message.\n";
        assertEquals(expectedHelpMessage, exitCommand.helpMessage());
    }
}

