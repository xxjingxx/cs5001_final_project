package problem1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A FileProcessor class
 */
public class FileProcessor {
  private Map<String, ArrayList<String>> users;
  private ErrorLogger log;

  /**
   * Constructor for FileProcessor class
   *
   * @param file_name - String
   */
  public FileProcessor(String file_name) {
    this.log = new ErrorLogger();
    this.users = null;
  }

  /**
   * returns a List of String
   *
   * @param path - String
   * @return - List lines
   * @throws IOException if I/O exceptions are encountered
   */
  public List<String> readFile(String path) throws IOException {
    List<String> lines = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String line = "";
    try {
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } catch (FileNotFoundException e) {
      this.log.log("File not found");
    } catch (IOException e) {
      this.log.log("IO exception");
    } finally {
      reader.close();
    }
    return lines;
  }

  /**
   * process the input
   *
   * @param lines - List lines
   * @return - map
   */
  public Map<String, ArrayList<String>> processInput(List<String> lines) {
    String header = lines.get(0);
    String[] array = header.split("\"");
    Map<String, ArrayList<String>> map = createMap(array);
    for (int j = 1; j < lines.size(); j++) {
      String line = lines.get(j);
      String[] userData = line.split("\"");
      for (int i = 0; i < userData.length; i++) {
        if (i % 2 != 0) {
          map.get(array[i]).add(userData[i]);
        }
      }
    }

    return map;
  }

  /**
   * create a map
   *
   * @param array - String[] array
   * @return - map
   */
  public Map<String, ArrayList<String>> createMap(String[] array) {
    HashMap<String, ArrayList<String>> map = new HashMap<>();
    for (int i = 0; i < array.length; i++) {
      if (i % 2 != 0) {
        map.put(array[i], new ArrayList<>());
      }
    }
    return map;
  }

  /**
   * returns ErrorLogger
   *
   * @return log
   */
  public ErrorLogger getLog() {
    return this.log;
  }

  /**
   * returns map
   *
   * @return users
   */
  public Map<String, ArrayList<String>> getUsers() {
    return this.users;
  }
}
