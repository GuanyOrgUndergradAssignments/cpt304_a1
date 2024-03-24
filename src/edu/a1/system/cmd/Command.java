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

    /**
     * Given the description strings of a command,
     * put them together.
     * 
     * @param usage describes the usage
     * @param effect describes the effect
     * @param options a list of option descriptions, each for one option.
     * The --help option desc is included for all commands inside this method.
     * @param parameters a list of parameter descriptions, each for one parameter.
     * @return the final help message of the command.
     */
    public static String generateHelpMessage(
        String usage,
        String effect,
        List<String> options,
        List<String> parameters
    ) {
        
        assert(usage != null && effect != null && options != null && parameters != null);

        String ret = "Usage: ";

        // usage
        ret += "Usage: " + usage + "\n";

        // effect
        ret += "Effect: " + effect + "\n";

        // options
        ret += "Options: \n";
        ret += "\t --help: displays the help message.\n";
        for(var option : options) {
            ret += "\t " + option + "\n";
        }

        // parameters
        if(!parameters.isEmpty()) {
            ret += "Parameters: \n";
            for(var par : parameters) {
                ret += "\t " + par + "\n";
            }
        }
        
        return ret;
    }
}
