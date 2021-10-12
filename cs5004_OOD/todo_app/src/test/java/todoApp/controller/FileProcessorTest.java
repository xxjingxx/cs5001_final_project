package todoApp.controller;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import todoApp.model.Task;
import todoApp.model.TaskData;

public class FileProcessorTest {

  private String expectedFilePath;
  private String expectedFilePath2;
  private String invalidFile;
  private TaskData exceptedTaskData;
  private ErrorLogger expectedLog;
  private List<String> exceptedHeaders;
  private List<String> exceptedHeaders2;
  private List<String> output1;

  private FileProcessor testFileProcessor;
  private FileProcessor testFileProcessor2;
  private FileProcessor testFileProcessor3;
  private FileProcessor testFileProcessor4;

  @Before
  public void setUp() throws Exception {
    expectedFilePath = "todotest.csv";
    expectedFilePath2 = "todo1.csv";
    invalidFile = "td";
    // expectedLog = new ErrorLogger();
    // testFileProcessor = new FileProcessor(expectedFilePath);
    // testFileProcessor2 = new FileProcessor(expectedFilePath);
    // testFileProcessor3 = new FileProcessor(invalidFile);
    exceptedTaskData = new TaskData();
    exceptedHeaders = new ArrayList<>();
    exceptedHeaders.add("\"id\"");
    exceptedHeaders.add("\"text\"");
    exceptedHeaders.add("\"completed\"");
    exceptedHeaders.add("\"due\"");
    exceptedHeaders.add("\"priority\"");
    exceptedHeaders.add("\"category\"");

    // testFileProcessor3 = new
    // FileProcessor(expectedFilePath,exceptedTaskData,exceptedHeaders,expectedLog);
    // testFileProcessor4 = new
    // FileProcessor(expectedFilePath2,exceptedTaskData,exceptedHeaders,expectedLog);
    // testFileProcessor3 = new FileProcessor(expectedFilePath2);
  }

  @Test
  public void testConstructor() throws IOException {
    testFileProcessor = new FileProcessor(expectedFilePath);
    // List<String> actualList = testFileProcessor.readFile(expectedFilePath);
    // List<String> actualHeader = testFileProcessor.generateHeaders(actualList);
    List<String> expectedList = new ArrayList<>();
    expectedList.add("\"id\"");
    expectedList.add("\"text\"");
    expectedList.add("\"completed\"");
    expectedList.add("\"due\"");
    expectedList.add("\"priority\"");
    expectedList.add("\"category\"");

    List<List<String>> stringData = new ArrayList<>();
    List<String> d1 = new ArrayList<>();
    d1.add("\"1\"");
    d1.add("\"Finish HW9\"");
    d1.add("\"true\"");
    d1.add("\"2021-08-02\"");
    d1.add("\"1\"");
    d1.add("\"school\"");
    stringData.add(d1);

    Task t1 =
        new Task.Builder("Finish HW9")
            .due(LocalDate.of(2021, 8, 2))
            .completed(true)
            .priority(1)
            .category("school")
            .build();

    TaskData td = new TaskData();
    td.addTodo(t1);

    assertEquals(expectedFilePath, testFileProcessor.getFilePath());
    assertEquals(expectedList, testFileProcessor.getHeaders());
    assertEquals(td, testFileProcessor.getTaskData());
    assertEquals(expectedFilePath, testFileProcessor.getFilePath());
  }

  @Test
  public void testConstructor2() throws IOException {
    testFileProcessor = new FileProcessor("todotest2.csv");
    List<String> expectedList = new ArrayList<>();
    expectedList.add("\"id\"");
    expectedList.add("\"text\"");
    expectedList.add("\"completed\"");
    expectedList.add("\"due\"");
    expectedList.add("\"priority\"");
    expectedList.add("\"category\"");

    List<List<String>> stringData = new ArrayList<>();
    List<String> d1 = new ArrayList<>();
    d1.add("\"1\"");
    d1.add("\"Finish HW9\"");
    d1.add("\"false\"");
    d1.add("\"?\"");
    d1.add("\"?\"");
    d1.add("\"school\"");
    stringData.add(d1);

    Task t1 =
            new Task.Builder("Finish HW9")
                    .completed(false)
                    .category("school")
                    .build();

    TaskData td = new TaskData();
    td.addTodo(t1);

    assertEquals("todotest2.csv", testFileProcessor.getFilePath());
    // assertEquals(expectedList, testFileProcessor.getHeaders());
    assertEquals(td, testFileProcessor.getTaskData());
    // assertEquals(expectedFilePath, testFileProcessor.getFilePath());
    // assertEquals(data, testFileProcessor.getTaskData().getStringData());
  }

  @Test
  public void testConstructorFail() throws IOException {
    testFileProcessor2 = new FileProcessor("invalid");
    ErrorLogger expectedEL = new ErrorLogger();
    expectedEL.log("CSV File not found");
    expectedEL.log("Read file failed.");
    expectedEL.log("Read file failed.");
    assertEquals(expectedEL, testFileProcessor2.getLog());
  }


  @Test
  public void testWriteFileFail() throws IOException {
    List<String> expectedList = new ArrayList<>();
    expectedList.add("\"id\"");
    expectedList.add("\"text\"");
    expectedList.add("\"completed\"");
    expectedList.add("\"due\"");
    expectedList.add("\"priority\"");
    expectedList.add("\"category\"");

    List<List<String>> stringData = new ArrayList<>();
    List<String> d1 = new ArrayList<>();
    d1.add("\"1\"");
    d1.add("\"Finish HW9\"");
    d1.add("\"true\"");
    d1.add("\"2021-08-02\"");
    d1.add("\"1\"");
    d1.add("\"school\"");
    stringData.add(d1);

    Task t1 =
            new Task.Builder("Finish HW9")
                    .due(LocalDate.of(2021, 8, 2))
                    .completed(true)
                    .priority(1)
                    .category("school")
                    .build();

    TaskData td = new TaskData();
    td.addTodo(t1);
    testFileProcessor = new FileProcessor(expectedFilePath);
    testFileProcessor.writeFile(td.getStringData(), "invalid");

    ErrorLogger expectedEL = new ErrorLogger();
    expectedEL.log("File doesn't exist.");
    assertEquals(expectedEL, testFileProcessor.getLog());
  }

    @Test
    public void testEqualsReflexivity() throws IOException {
      testFileProcessor = new FileProcessor(expectedFilePath);
      assertTrue(testFileProcessor.equals(testFileProcessor));
    }

  @Test
  public void testEqualsDifferentFile() throws IOException {
    testFileProcessor = new FileProcessor(expectedFilePath);
    testFileProcessor2 = new FileProcessor(expectedFilePath2);
    assertFalse(testFileProcessor.equals(testFileProcessor2));
  }


    @Test
    public void testEqualsSymmetry() throws IOException {
      testFileProcessor = new FileProcessor("todos.csv");
      testFileProcessor2 = new FileProcessor("todos1.csv");
      assertFalse(testFileProcessor.equals(testFileProcessor2));
    }

    @Test
    public void equalsNotSameObject() throws IOException {
      testFileProcessor = new FileProcessor(expectedFilePath);
      assertFalse(testFileProcessor.equals(expectedLog));
    }


    @Test
    public void testEqualsNull() throws IOException {
      testFileProcessor = new FileProcessor(expectedFilePath);
      assertFalse(testFileProcessor.equals(null));
    }


  @Test
  public void testHashCode() throws IOException {
    testFileProcessor = new FileProcessor(expectedFilePath);
    int hash =
        Objects.hash(
            testFileProcessor.getFilePath(),
            testFileProcessor.getTaskData(),
            testFileProcessor.getHeaders(),
            testFileProcessor.getLog());
    assertEquals(hash, testFileProcessor.hashCode());
  }

    @Test
    public void testHashCodeConsistency() throws IOException {
      testFileProcessor = new FileProcessor(expectedFilePath);
      int testHashCode = testFileProcessor.hashCode();
      assertEquals(testHashCode, testFileProcessor.hashCode());
    }

  @Test
  public void testToString() throws IOException {
    testFileProcessor = new FileProcessor(expectedFilePath);
    String excepted =
        "FileProcessor{"
            + "filePath='"
            + testFileProcessor.getFilePath()
            + '\''
            + ", taskData="
            + testFileProcessor.getTaskData()
            + ", headers="
            + testFileProcessor.getHeaders()
            + ", log="
            + testFileProcessor.getLog()
            + '}';
    assertEquals(excepted, testFileProcessor.toString());
  }
}
