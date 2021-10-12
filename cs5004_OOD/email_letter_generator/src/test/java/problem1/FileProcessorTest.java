package problem1;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileProcessorTest {

  private Map<String, ArrayList<String>> expectedUsers;
  private ErrorLogger expectedLog;
  private FileProcessor testFileProcessor;
  private ErrorLogger log;


  @Before
  public void setUp() throws Exception {
    expectedUsers = new HashMap<>();
    expectedLog = new ErrorLogger();
    testFileProcessor = new FileProcessor("test.csv");
  }

  @Test
  public void createMap() {
    ArrayList<String> test = new ArrayList<>();
    test.add("tom");
    expectedUsers.put("first_name", test);
    assertEquals("tom", expectedUsers.get("first_name").get(0));
  }


  @Test
  public void getLog() {
    assertEquals(testFileProcessor.getLog(), expectedLog);
  }
}
