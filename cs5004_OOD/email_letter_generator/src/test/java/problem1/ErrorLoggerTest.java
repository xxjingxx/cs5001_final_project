package problem1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;

public class ErrorLoggerTest {
  private List<String> expectedLog;
  private ErrorLogger testLog;
  @Before
  public void setUp() throws Exception {
    expectedLog = new ArrayList<>();
    testLog = new ErrorLogger();
  }

  @Test
  public void log() {
    testLog.log("File not found");
    String test = "File not found";
    assertEquals(testLog.getLog().get(0), test);
    //System.out.println(expectedLog.get(0));
  }

  @Test
  public void isEmpty() {
    assertTrue(testLog.getLog().isEmpty());
  }

  @Test
  public void isEmpty2() {
    assertTrue(testLog.getLog().size() == 0);
  }

  @Test
  public void equalsReflexivity() {
    assertTrue(testLog.equals(testLog));
  }

  @Test
  public void equalsReflexivity2() {
    ErrorLogger testLog2 = new ErrorLogger();
    testLog2.log("Error");
    assertFalse(testLog.equals(testLog2));
  }

  @Test
  public void equalsObject() {
    assertFalse(testLog.equals(expectedLog));
  }

  @Test
  public void equalsField() {
    ErrorLogger testLog2 = new ErrorLogger();
    testLog2.log("error");
    testLog.log("error");
    assertTrue(testLog.equals(testLog2));
  }

  @Test
  public void testHashCode() {
    int hashCode = Objects.hash(expectedLog);
    assertTrue(testLog.hashCode() == hashCode);
  }

  @Test
  public void testToString() {
    String test = "Empty log";
    assertTrue(test.equals(testLog.toString()));
  }

  @Test
  public void testToString2() {
    testLog.log("Error");
    testLog.log("No file");
    String out = "";
    for (String entry : this.testLog.getLog()) {
      out += entry + System.lineSeparator();
    }
    assertEquals(out, testLog.toString());
  }
}