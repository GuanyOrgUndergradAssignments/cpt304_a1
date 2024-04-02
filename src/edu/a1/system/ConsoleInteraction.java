package edu.a1.system;

import java.util.Scanner;

/**
 * Encapsulates the interaction with the console.
 * @author Guanyuming He
 */
public final class ConsoleInteraction implements IOInteraction {

    /** to read from the console */
    private final Scanner scanner;

    /** only allow one instance */
    private static boolean created = false;

    /**
     * Construct a new instance by connecting it with the console.
     */
    public ConsoleInteraction() {
        assert(!created);
        created = true;

        scanner = new Scanner(System.in);;
    }

    /**
     * Reads input from console until the user presses ENTER.
     * That is, the method reads one line from the console.
     * @return the input read
     */
    @Override
    public String readLineFrom() {
        return scanner.nextLine();
    }

    /**
     * Writes msg to the console. Will start a new line after it.
     * @param msg the message.
     */
    @Override
    public void writeTo(String msg) {
        System.out.println(msg);
    }
}
