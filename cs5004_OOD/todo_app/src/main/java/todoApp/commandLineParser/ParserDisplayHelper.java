package todoApp.commandLineParser;

import java.util.Arrays;
import java.util.Map;

/**
 * Helper class to create user help manual.
 */
public class ParserDisplayHelper {

  private static final int SPACE = 3;

  public static String createUsage(Map<String, Option> options) {
    int numOfRows = options.size();
    String[] descriptions = new String[numOfRows];
    Object[] keys = options.keySet().toArray();
    Arrays.sort(keys);
    for (int i = 0; i < numOfRows; i++) {

      descriptions[i] = options.get(keys[i]).getDescription();
    }
    String table = ParserDisplayHelper.createTable(numOfRows, descriptions);
    return table;
  }

  /**
   * Helper method to present some information in tabular form.
   *
   * @param numOfRows - int, number of rows of the table.
   * @param values    - String, contents of the table.
   * @return - String
   */
  public static String createTable(int numOfRows, String[] values) {
    if (values == null) {
      throw new IllegalArgumentException("Cannot pass NULL as an argument!");
    }

    if (numOfRows < 0) {
      throw new IllegalArgumentException("Number of rows must be positive!");
    }

    if (numOfRows != values.length) {
      throw new IllegalArgumentException(
              "Number of Strings for values should equal to number of rows!");
    }
    int maxValues = maxLength(values);
    int lineCount = (maxValues + SPACE) - 1;
    String table = "";
    for (int row = 0; row < numOfRows; row++) {
      table += createLine("-", lineCount);
      table += "|";
      table += String.format("%-" + maxValues + "." + maxValues + "s", values[row]);
      table += "|\n";
    }
    table += createLine("-", lineCount);
    return table;
  }

  /**
   * Helper method to draw a line on stdout.
   *
   * @param character Character to be used for creating the line
   * @param count     Length of the line
   * @return String representing the line
   */
  public static String createLine(String character, int count) {
    StringBuilder line = new StringBuilder("");
    for (int i = 0; i < count; ++i) {
      line.append(character);
    }
    line.append("\n");

    return line.toString();
  }

  /**
   * Helper method to calculate the length of maximum string in an array of strings.
   *
   * @param strings Array of string
   * @return Length of longest string in the given array
   */
  private static int maxLength(String[] strings) {
    int max = 0;
    for (String str : strings) {
      if (str.length() > max) {
        max = str.length();
      }
    }
    return max;
  }
}

