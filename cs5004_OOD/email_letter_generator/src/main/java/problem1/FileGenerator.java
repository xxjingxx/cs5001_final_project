package problem1;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.LinkedTransferQueue;

/**
 * class File Generator to generate template file from csv file
 */
public class FileGenerator {

  private static final Integer TWO = 2;
  private ErrorLogger log;

  public FileGenerator() {
    this.log = new ErrorLogger();
  }

  /**
   * read a file and return its content as a String.
   *
   * @param file String, contents in the file
   * @return String of read file by using line by line
   * @throws IOException if I/O exceptions are encountered
   */
  public String readFile(String file) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String line;
    StringBuilder stringBuilder = new StringBuilder();
    String ls = System.getProperty("line.separator");

    try {
      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line);
        stringBuilder.append(ls);
      }
      return stringBuilder.toString();
    } finally {
      reader.close();
    }
  }

  /**
   * replace a template with users' information
   *
   * @param input        String, the template to be replaced
   * @param users        Map, user data
   * @param outPath      String, output path
   * @param templateType String, template type
   * @return String[][], a 2D-String array that stores file names and file contents.
   */
  public String[][] generateOutString(
          String input, Map<String, ArrayList<String>> users, String outPath, String templateType) {

    int numOfUsers = users.get("last_name").size();
    List<String> lastNames = users.get("last_name");

    String[][] ret = new String[numOfUsers][TWO];
    Set<String> keys = users.keySet();

    for (int i = 0; i < numOfUsers; i++) {
      String fileName = outPath + File.separator + lastNames.get(i) + "-" + templateType + ".txt";
      ret[i][1] = input;
      ret[i][0] = fileName;
    }

    for (String key : keys) {
      List<String> userinfo = users.get(key);
      for (int i = 0; i < numOfUsers; i++) {
        String op = ret[i][1];
        op = op.replace(key, userinfo.get(i));
        ret[i][1] = op;
      }
    }

    for (int i = 0; i < numOfUsers; i++) {
      String op = ret[i][1];
      op = op.replaceAll("\\[\\[", "");
      op = op.replaceAll("]]", "");
      ret[i][1] = op;
    }
    return ret;
  }


  /**
   * Generate files based on input file names and contents.
   *
   * @param input - string[][], a 2D-String array that stores file names and file contents.
   * @throws IOException if I/O related exception is encountered.
   */
  public void generateFile(String[][] input) throws IOException {
    for (int i = 0; i < input.length; i++) {
      FileWriter out = null;
      try {
        out = new FileWriter(input[i][0]);
        out.write(input[i][1]);
      } catch (FileNotFoundException fnfe) {
        this.log.log("File not found");
      } finally {
        if (out != null) {
          try {
            out.close();
          } catch (IOException e) {
            this.log.log("File not open");
          }
        }
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileGenerator that = (FileGenerator) o;
    return this.log.equals(that.log);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.log);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return "FileGenerator{" +
            "log=" + log +
            '}';
  }
}