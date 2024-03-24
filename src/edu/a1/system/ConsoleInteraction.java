package edu.a1.system;

import java.util.Scanner;

/**
 * Encapsulates the interaction with the console.
 * @author Guanyuming He
 */
public final class ConsoleInteraction {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Reads input from console until the user presses ENTER.
     * That is, the method reads one line from the console.
     * @return the input read
     */
    public static String readFromConsole() {
        return scanner.nextLine();
    }

    /**
     * Writes msg to the console. Will start a new line after it.
     */
    public static void writeToConsole(String msg) {
        System.out.println(msg);
    }
}
