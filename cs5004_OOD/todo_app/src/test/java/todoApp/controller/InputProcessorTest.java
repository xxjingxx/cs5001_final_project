package todoApp.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InputProcessorTest {
  String input1;
  String[] ret1;
  InputProcessor ip;
  InputProcessor ip2;
  private ErrorLogger inputErrorLog1;

  @Before
  public void setUp() throws Exception {
    input1 = "--csv-file todos.csv --add-todo --todo-text do exercise --display";
    ip = new InputProcessor();
    ret1 = new String[]{"--csv-file","todos.csv","--add-todo","--todo-text","do exercise","--display"};
    inputErrorLog1 = new ErrorLogger();

  }

  @Test
  public void getInputErrorLog() {
    assertEquals(inputErrorLog1,ip.getInputErrorLog());
  }

  @Test
  public void testEqualsReflexivity() {
    assertTrue(ip.equals(ip));
  }


  @Test
  public void testEqualsSymmetry() {
    ip2 = new InputProcessor();
    assertTrue(ip.equals(ip2));
  }


  @Test
  public void testEqualsNull() {
    assertFalse(ip.equals(null));
  }


  @Test
  public void testHashCode() {
    assertFalse(0 == ip.hashCode());
  }

  @Test
  public void testHashCodeConsistency() {
    int testHashCode = ip.hashCode();
    assertEquals(testHashCode, ip.hashCode());
  }

  @Test
  public void testToString() {
    String excepted = "InputProcessor{" +
        "inputErrorLog=" + inputErrorLog1 +
        '}';
    assertEquals(excepted, ip.toString());
  }

  @Test
  public void processInput() {
    String arguments1[] = ip.processInput(input1);
    assertArrayEquals(ret1,arguments1);
  }

  @Test
  public void processInputFail() {
    String arguments1[] = ip.processInput(null);
    ErrorLogger el = new ErrorLogger();
    el.log("Something went wrong while processing input.");
    assertEquals(el, ip.getInputErrorLog());
  }
}