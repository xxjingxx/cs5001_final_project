package problem1;

import java.io.File;
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
  private List<String> optionalOption = OptionConst.optionalOptions;
  private Map<String, String> options;

  private String IN_FILE = OptionConst.CSV;
  private String OUT_FILE = OptionConst.TXT;

  /**
   * Constructs a CommandLineParser.
   *
   * @param args - String[], String array of command line argument.
   */
  public CmdLineParser(String[] args) {
    this.processArgs(args);
    this.options = this.createOptions(args);
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
    this.checkOptionalOptions(options, this.optionalOption);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getArg(String option) {
    if (this.options.get(option) == null)
      throw new CmdLineExceptions.NullArgumentException("\nError: " + option + " has no argument.");
    return options.get(option);
  }

  /**
   * Retrieves the path(s) of the template file(s) from command line argument.
   *
   * @return - List, a list of template path(s).
   */
  public List<String> getTemplates() {
    List<String> templates = new ArrayList<>();
    if (this.options.containsKey(OptionConst.LETTER_TEMPLATE)) {
      String letterTemplate = this.options.get(OptionConst.LETTER_TEMPLATE);
      templates.add(letterTemplate);
    }
    if (this.options.containsKey(OptionConst.EMAIL_TEMPLATE)) {
      String emailTemplate = this.options.get(OptionConst.EMAIL_TEMPLATE);
      templates.add(emailTemplate);
    }
    return templates;
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

    if (arguments.contains(OptionConst.EMAIL)) {
      this.validateTemplate(OptionConst.EMAIL, arguments, OptionConst.EMAIL_TEMPLATE);
      String emailTemplateFile = arguments.get(arguments.indexOf(OptionConst.EMAIL_TEMPLATE) + 1);
      options.put(OptionConst.EMAIL, null);
      options.put(OptionConst.EMAIL_TEMPLATE, emailTemplateFile);
    }

    if (arguments.contains(OptionConst.LETTER)) {
      this.validateTemplate(OptionConst.LETTER, arguments, OptionConst.LETTER_TEMPLATE);
      String letterTemplateFile = arguments.get(arguments.indexOf(OptionConst.LETTER_TEMPLATE) + 1);
      options.put(OptionConst.LETTER, null);
      options.put(OptionConst.LETTER_TEMPLATE, letterTemplateFile);
    }

    String csvPath = arguments.get(arguments.indexOf(OptionConst.CSV_FILE) + 1);
    this.checkFileType(OptionConst.CSV_FILE, csvPath, this.IN_FILE);
    options.put(OptionConst.CSV_FILE, csvPath);

    String outPath = arguments.get(arguments.indexOf(OptionConst.OUTPUT_DIR) + 1);
    this.checkDir(OptionConst.OUTPUT_DIR, outPath);
    options.put(OptionConst.OUTPUT_DIR, outPath);

    return options;
  }

  /**
   * Validates if the arguments contains required template option and correct file type given a
   * provided option
   *
   * @param option         - String, the option
   * @param arguments      - List, a list of arguments provided by user
   * @param templateOption - String
   */
  private void validateTemplate(String option, List<String> arguments, String templateOption) {
    this.checkSecondaryOption(option, arguments, templateOption);
    String templateName = arguments.get(arguments.indexOf(templateOption) + 1);
    this.checkFileType(templateOption, templateName, this.OUT_FILE);
  }

  /**
   * Checks if a file name matches the required file type if the file name is an argument of a given
   * option.
   *
   * @param option          - String, the option which requires a file as argument.
   * @param fileName        - String, the file name to be checked.
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
