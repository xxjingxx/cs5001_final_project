package todoApp.commandLineParser;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Processes and validates command line arguments. This class doesn't *do* anything with the values
 * provided by the user beyond initial validation. It is another class' responsibility to determine
 * what to do with the user input.
 */
public class CmdLineParser extends Parser {

  private List<String> validOptions = OptionConst.validOptions;
  private List<String> requiredOptions = OptionConst.requiredOptions;
  private Map<String, String> options;

  private String IN_FILE = OptionConst.CSV;

  /**
   * Constructs a CommandLineParser.
   *
   * @param args - String[], String array of command line argument.
   */
  public CmdLineParser(String[] args) {
    this.processArgs(args);
    this.options = this.createOptions(args);
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CmdLineParser that = (CmdLineParser) o;
    return validOptions.equals(that.validOptions)
        && requiredOptions.equals(that.requiredOptions)
        && options.equals(that.options)
        && IN_FILE.equals(that.IN_FILE);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(validOptions, requiredOptions, options, IN_FILE);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "CmdLineParser{"
        + "validOptions="
        + validOptions
        + ", requiredOptions="
        + requiredOptions
        + ", options="
        + options
        + ", IN_FILE='"
        + IN_FILE
        + '\''
        + '}';
  }

  /** @return Map, all the options and arguments provided by user. */
  public Map<String, String> getOptions() {
    return this.options;
  }

  /**
   * Process the command line arguments.
   *
   * @param args - - String[], String array of command line argument.
   */
  @Override
  public void processArgs(String[] args) {
    List<String> options = super.extractOptions(args, OptionConst.OPTION);
    this.checkValidOptions(options, this.validOptions);
    this.checkRequiredOptions(options, this.requiredOptions);
  }

  /** {@inheritDoc} */
  @Override
  public String getArg(String option) {
    return options.get(option);
  }

  /**
   * Validates command line arguments and store the validated arguments in a Map, otherwise throw
   * exceptions.
   *
   * @param args - String[] command line arguments
   * @return - Map
   */
  protected Map<String, String> createOptions(String[] args) {
    Map<String, String> options = new HashMap<>();
    List<String> arguments = Arrays.asList(args);

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case OptionConst.CSV_FILE:
          String csvPath = arguments.get(arguments.indexOf(OptionConst.CSV_FILE) + 1);
          this.checkFileType(OptionConst.CSV_FILE, csvPath, this.IN_FILE);
          options.put(OptionConst.CSV_FILE, csvPath);
          break;
        case OptionConst.ADD_TODO:
          this.checkSecondaryOption(OptionConst.ADD_TODO, arguments, OptionConst.TODO_TEXT);
          String todoText = arguments.get(arguments.indexOf(OptionConst.TODO_TEXT) + 1);
          options.put(OptionConst.ADD_TODO, null);
          options.put(OptionConst.TODO_TEXT, todoText);
          break;
        case OptionConst.COMPLETED:
          options.put(OptionConst.COMPLETED, null);
          break;
        case OptionConst.DUE:
          String dueString = arguments.get(arguments.indexOf(OptionConst.DUE) + 1);
          options.put(OptionConst.DUE, dueString);
          break;
        case OptionConst.PRIORITY:
          String priority = arguments.get(arguments.indexOf(OptionConst.PRIORITY) + 1);
          options.put(OptionConst.PRIORITY, priority);
          break;
        case OptionConst.CATEGORY:
          String category = arguments.get(arguments.indexOf(OptionConst.CATEGORY) + 1);
          options.put(OptionConst.CATEGORY, category);
          break;
        case OptionConst.COMPLETE_TODO:
          String id = arguments.get(arguments.indexOf(OptionConst.COMPLETE_TODO) + 1);
          options.put(OptionConst.COMPLETE_TODO, id);
          break;
        case OptionConst.DISPLAY:
          options.put(OptionConst.DISPLAY, null);
          break;
        case OptionConst.SHOW_INCOMPLETE:
          options.put(OptionConst.SHOW_INCOMPLETE, null);
          break;
        case OptionConst.SHOW_CATEGORY:
          String cat = arguments.get(arguments.indexOf(OptionConst.SHOW_CATEGORY) + 1);
          options.put(OptionConst.SHOW_CATEGORY, cat);
          break;
        case OptionConst.SORT_BY_DATE:
          options.put(OptionConst.SORT_BY_DATE, null);
          break;
        case OptionConst.SORT_BY_PRIORITY:
          options.put(OptionConst.SORT_BY_PRIORITY, null);
          break;
      }
    }
    return options;
  }

  /**
   * Checks if a file name matches the required file type if the file name is an argument of a given
   * option.
   *
   * @param option - String, the option which requires a file as argument.
   * @param fileName - String, the file name to be checked.
   * @param fileTypePattern - String, the regex of the required file type.
   */
  private void checkFileType(String option, String fileName, String fileTypePattern) {
    Pattern re = Pattern.compile(fileTypePattern);
    if (!re.matcher(fileName).matches()) {
      throw new CmdLineExceptions.IllegalPathTypeException(
          "\nError: " + option + " must be followed by a " + fileTypePattern + " file.");
    }
  }
}
