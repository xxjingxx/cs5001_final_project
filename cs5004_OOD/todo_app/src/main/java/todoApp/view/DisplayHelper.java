package todoApp.view;

import java.util.List;

/** Represents a display helper. */
public class DisplayHelper {

  private static final int SPACE = 3;
  private static final int ROWS = 6;

  /**
   * Helper method to present some information in tabular form.
   *
   * @param values Values in each row
   * @param headers List, headers of the table
   * @return String representation of a table
   * @throws IllegalArgumentException If the dimensions of all the parameters do not match
   */
  public static String createTable(List<String> headers, List<List<String>> values) {

    String table = "";
    int maxValue = maxLength(values);
    int lineCount = (maxValue + SPACE) * ROWS - 1;
    table = "\n";

    if (headers != null) {
      table += createLine("-", lineCount);
      for (String header : headers) {
        table += String.format(" %" + maxValue + "s |", header);
      }
      table += "\n";
    }

    for (List<String> rowData : values) {
      table += createLine("-", lineCount);
      for (String s : rowData) {
        table += String.format(" %" + maxValue + "s |", s);
      }
      table += "\n";
    }
    table += createLine("-", lineCount);
    return table;
  }

  /**
   * Helper method to draw a line on stdout.
   *
   * @param character Character to be used for creating the line
   * @param count Length of the line
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
   * Finds the max length of the strings in a list of list of string.
   *
   * @param values - List
   * @return - int
   */
  private static int maxLength(List<List<String>> values) {
    int max = 0;
    for (List<String> rowData : values) {
      for (String s : rowData) {
        if (s.length() > max) {
          max = s.length();
        }
      }
    }
    return max;
  }
}
