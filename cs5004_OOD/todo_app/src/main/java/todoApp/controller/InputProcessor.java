package todoApp.controller;

import todoApp.commandLineParser.CmdLineParser;
import todoApp.commandLineParser.OptionConst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class contains methods that convert user promp input to the format that can accepted by a
 * command line parser.
 */
public class InputProcessor {

  private ErrorLogger inputErrorLog;

  /** Constructs a input processor. */
  public InputProcessor() {
    this.inputErrorLog = new ErrorLogger();
  }

  /** @return - ErrorLogger */
  public ErrorLogger getInputErrorLog() {
    return inputErrorLog;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    InputProcessor that = (InputProcessor) o;
    return Objects.equals(inputErrorLog, that.inputErrorLog);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(inputErrorLog);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "InputProcessor{" + "inputErrorLog=" + inputErrorLog + '}';
  }

  /**
   * Process user input.
   *
   * @param input - String
   * @return - String[]
   */
  public String[] processInput(String input) {
    List<String> l = new ArrayList<>();
    try {
      input = input.replaceAll("--", "%_%--");
      String[] array = input.split("%_%");
      for (int i = 1; i < array.length; i++) {
        String optionArg = array[i];
        optionArg = optionArg.replaceFirst(" ", "%_%");
        optionArg = optionArg.trim();
        String[] optionArgArray = optionArg.split("%_%");
        l.addAll(Arrays.asList(optionArgArray));
      }
    } catch (RuntimeException e) {
      this.inputErrorLog.log("Something went wrong while processing input.");
    }

    String[] ret = l.toArray(l.toArray(new String[0]));

    return ret;
  }
}
