package edu.a1.system;

/**
 * Encapsulates the interaction with the console.
 * @author Guanyuming He
 */
public final class ConsoleInteraction {

    /**
     * Reads input from console until the user presses ENTER.
     * @return the input read
     */
    public static String readFromConsole() {
        throw new RuntimeException("Not implemented.");
    }

    /**
     * Writes msg to the console. Will start a new line after it.
     */
    public static void writeToConsole(String msg) {
        System.out.println(msg);
    }
}
