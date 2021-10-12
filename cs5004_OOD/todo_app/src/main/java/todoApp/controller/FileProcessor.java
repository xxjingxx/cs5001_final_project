package todoApp.controller;

import todoApp.controller.ErrorLogger;
import todoApp.model.Task;
import todoApp.model.TaskData;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/** A FileProcessor class */
public class FileProcessor {

  private String filePath;
  private TaskData taskData;
  private List<String> headers;
  private ErrorLogger log;

  private static final int ONE = 1;
  private static final int TWO = 2;
  private static final int THREE = 3;
  private static final int FOUR = 4;
  private static final int FIVE = 5;

  private static final String QUOTED_QUESTION_MARK = "\"?\"";

  /**
   * Constructor for FileProcessor class
   *
   * @param filePath - String, the file name
   * @throws IOException if error occurs
   */
  public FileProcessor(String filePath) throws IOException {
    this.log = new ErrorLogger();
    List<String> output = this.readFile(filePath);
    this.headers = this.generateHeaders(output);
    this.taskData = this.generateTaskData(output);
    this.filePath = filePath;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FileProcessor that = (FileProcessor) o;
    return filePath.equals(that.filePath)
        && taskData.equals(that.taskData)
        && headers.equals(that.headers)
        && log.equals(that.log);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(filePath, taskData, headers, log);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "FileProcessor{"
        + "filePath='"
        + filePath
        + '\''
        + ", taskData="
        + taskData
        + ", headers="
        + headers
        + ", log="
        + log
        + '}';
  }

  /**
   * returns a List of String
   *
   * @param path - String
   * @return - List //* @throws IOException if I/O exceptions are encountered
   */
  private List<String> readFile(String path) {
    List<String> lines = new ArrayList<>();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(path));
      String line = "";
      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } catch (FileNotFoundException e) {
      this.log.log("CSV File not found");
      return null;
    } catch (IOException e) {
      this.log.log("IO exception");
      return null;
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          this.log.log("Failed to close csv file.");
          return null;
        }
      }
    }
    return lines;
  }

  /**
   * Generate headers based on the csv header.
   *
   * @param lines - List, csv raw data
   * @return - List, the headers for the csv file.
   */
  private List<String> generateHeaders(List<String> lines) {
    if (lines == null) {
      this.log.log("Read file failed.");
      return null;
    }
    String headerString = lines.get(0);
    headerString = headerString.replaceAll(",\"", ",%_%\"");
    String[] rowData = headerString.split(",%_%");
    return Arrays.asList(rowData);
  }

  /**
   * Generates task data.
   *
   * @param lines - List, csv raw data
   * @return - TaskData, a collection of Tasks
   */
  private TaskData generateTaskData(List<String> lines) {
    if (lines == null) {
      this.log.log("Read file failed.");
      return null;
    }
    TaskData ret = new TaskData();
    for (int i = 1; i < lines.size(); i++) {
      String line = lines.get(i);
      line = line.replaceAll(",\"", ",%_%\"");
      String[] rowData = line.split(",%_%");
      for (int j = 0; j < rowData.length; j++) {
        rowData[j] = rowData[j].replaceAll("\"", "");
      }
      Task newTask = buildTask(rowData);
      ret.addTodo(newTask);
    }
    return ret;
  }

  /**
   * Builds a task.
   *
   * @param rowData - String[], a single line of csv file represented in String[].
   * @return - Task, a new task per user's request
   */
  private Task buildTask(String[] rowData) {
    String text = rowData[ONE];
    String completed = rowData[TWO];
    String due = rowData[THREE];
    String priority = rowData[FOUR];
    String category = rowData[FIVE];

    boolean comp;
    if (completed.equals("false")) {
      comp = false;
    } else {
      comp = true;
    }

    LocalDate dueDate = null;
    if (!due.equals("?")) {
      // try {
      DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
      dueDate = LocalDate.parse(due, dtf);
      // } catch (DateTimeException dte) {
      // this.log.log("Please provide due date in format: YYYY-MM-DD.");
      // }
    }

    Integer pri;
    if (priority.equals("?")) {
      pri = THREE;
    } else {
      pri = Integer.parseInt(priority);
    }

    Task task =
        new Task.Builder(text)
            .completed(comp)
            .due(dueDate)
            .priority(pri)
            .category(category)
            .build();
    return task;
  }

  /**
   * Write updated tasks in the original csv file. Original data will be overwritten.
   *
   * @param data - List, task data
   * @param path - file name
   * @throws IOException if error occurs during writing.
   */
  public void writeFile(List<List<String>> data, String path) throws IOException {

    File newCsv = new File(path);
    if (!newCsv.isFile()) {
      this.log.log("File doesn't exist.");
      return;
    }

    FileWriter updatedCSV = null;
    List<String> header = this.generateHeaders(this.readFile(path));

    try {
      updatedCSV = new FileWriter(path);
      updatedCSV.append(String.join(",", header));
      updatedCSV.append("\n");

      for (List<String> rowData : data) {
        updatedCSV.append((String.join(",", rowData)));
        updatedCSV.append("\n");
      }

    } catch (FileNotFoundException e) {
      this.log.log("CSV File not found");
    } finally {
      if (updatedCSV != null) {
        try {
          updatedCSV.close();
        } catch (IOException e) {
          this.log.log("Failed to close csv file.");
        }
      }
    }
  }

  /** @return ErrorLogger */
  public ErrorLogger getLog() {
    return this.log;
  }

  /** @return TaskData, giving task data. */
  public TaskData getTaskData() {
    return taskData;
  }

  /** @return String, giving file name. */
  public String getFilePath() {
    return filePath;
  }

  /** @return List, giving headers. */
  public List<String> getHeaders() {
    return headers;
  }
}
