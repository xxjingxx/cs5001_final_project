package problem1;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Represents an abstract command line parser.
 */
public abstract class Parser {

  private Map<String, String> options;
  private List<String> validOptions;
  private List<String> requiredOptions;
  private List<String> optionalOptions;

  /**
   * Retrieves the argument of a given option if the option has one.
   *
   * @param option - String, the name of the option
   * @return - String, the argument of the option if it has one.
   */
  abstract String getArg(String option);

  /**
   * Create a map to store the valid options and arguments provided by users.
   *
   * @param args - String[], String array of command line argument.
   * @return - Map, map key is option and value is argument.
   */
  abstract Map<String, String> createOptions(String[] args);

  /**
   * Process the command line arguments.
   *
   * @param args - - String[], String array of command line argument.
   */
  abstract void processArgs(String[] args);

  /**
   * Checks if the provided options are all valid options.
   *
   * @param options      - List, a list of options provided by user.
   * @param validOptions - List, a list of all valid options.
   */
  protected void checkValidOptions(List<String> options, List<String> validOptions) {
    for (String option : options) {
      if (!validOptions.contains(option)) {
        throw new CmdLineExceptions.InvalidOptionException("Error: " + option + " is not a valid option");
      }
    }
  }

  /**
   * Checks if the provided options contains all required options.
   *
   * @param options         - List, a list of options provided by user.
   * @param requiredOptions - List, a list of required options.
   */
  protected void checkRequiredOptions(List<String> options, List<String> requiredOptions) {
    for (String option : requiredOptions) {
      if (!options.contains(option)) {
        throw new CmdLineExceptions.MissingOptionException("Error: " + option + " is required");
      }
    }
  }

  /**
   * Checks if the provided options contain at least one optional options.
   *
   * @param options - List, a list of options provided by user.
   * @param optionalOptions - List, a list of optional options.
   */
  protected void checkOptionalOptions(List<String> options, List<String> optionalOptions) {
    for (String option : optionalOptions) {
      if (options.contains(option)) return;
    }
    throw new CmdLineExceptions.MissingOptionException("Error: missing optional option.");
  }

  /**
   * Checks if the required option is provided if a pre-requisite option is provided.
   *
   * @param prerequisiteOption - String, the pre-requisite option.
   * @param arguments          - List, a list of options provided by users.
   * @param secondaryOption    - String, the required option if the option is given.
   */
  protected void checkSecondaryOption(String prerequisiteOption, List<String> arguments, String secondaryOption) {
    if (arguments.contains(prerequisiteOption)) {
      if (!arguments.contains(secondaryOption)) {
        throw new CmdLineExceptions.MissingOptionException(
                "\nError: " + prerequisiteOption + " provided but no " + secondaryOption + " was given.");
      }
    }
  }

  /**
   * Check if a valid directory is given if a option requires directory as argument.
   *
   * @param option  - String, the option that must follow a directory.
   * @param outPath - the directory path to be checked
   */
  protected void checkDir(String option, String outPath) {
    File file = new File(outPath);
    if (!file.isDirectory())
      throw new CmdLineExceptions.IllegalPathTypeException(
              "\nError: " + option + " must follow a valid directory.");
  }

  /**
   * Identifies options in command line argument and add the options in a list.
   *
   * @param args       - String[], String array of command line arguments.
   * @param identifier - String, the identifier of an option, usually "--".
   * @return List, a list of options in the command line arguments.
   */
  protected List<String> extractOptions(String[] args, String identifier) {
    List<String> ret = new ArrayList<>();
    Pattern re = Pattern.compile(identifier);
    for (String s : args) {
      if (re.matcher(s).matches()) {
        ret.add(s);
      }
    }
    return ret;
  }
}
