package todoApp.commandLineParser;

import java.util.Map;

/** Class to handle all Command Line Arguments related exceptions. */
public abstract class CmdLineExceptions extends RuntimeException {

  private static String usageHelp;
  private static Map<String, Option> allOptions = OptionConst.allOptions;

  /**
   * This constructor prints the error message on command line and exits successfully.
   *
   * @param msg - String, Error message.
   */
  public CmdLineExceptions(String msg) {
    super("\n" + msg + "\n" + usageHelp);
  }

  /** Class to handle errors related to illegal path type. */
  public static class IllegalPathTypeException extends CmdLineExceptions {
    public IllegalPathTypeException(String msg) {
      super(msg);
    }
  }

  /** Class to handle errors related to requiring a null argument. */
  public static class NullArgumentException extends CmdLineExceptions {
    public NullArgumentException(String msg) {
      super(msg);
    }
  }

  /** Class to handle invalid command line arguments. */
  public static class InvalidOptionException extends CmdLineExceptions {
    public InvalidOptionException(String msg) {
      super(msg);
    }
  }

  /** Class to handle errors related to missing required option. */
  public static class MissingOptionException extends CmdLineExceptions {
    public MissingOptionException(String msg) {
      super(msg);
    }
  }

  // Used to represent USAGE help report in tabular form
  static {
    usageHelp = "\nUsage:\n";
    usageHelp += ParserDisplayHelper.createUsage(CmdLineExceptions.allOptions);
  }
}
