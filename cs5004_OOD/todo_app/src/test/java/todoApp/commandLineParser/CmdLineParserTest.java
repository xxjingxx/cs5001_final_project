package todoApp.commandLineParser;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.junit.Before;
import org.junit.Test;

public class CmdLineParserTest {
  private CmdLineParser cp1;
  private CmdLineParser cp2;
  private CmdLineParser cp3;

  private List<String> validOptions1;
  private List<String> validOptions2;
  private List<String> requiredOptions1;
  private List<String> requiredOptions2;
  private Map<String, String> options1;
  private Map<String, String> options2;

  private String[] args1;
  private String[] args2;
  private String[] args3;
  private String IN_FILE;
  private String input1[];
  private String input2[];
  private String input3[];
  private String invalidInput1[];
  private String invalidInput2[];
  private String invalidInput3[];
  private String invalidInput4[];
  private String invalidInput5[];

  private List<String> invalidArgs1;
  private List<String>invalidArgs2;
  private List<String> invalidArgs3;
  private List<String> invalidArgs4;
  private List<String> invalidArgs5;


  @Before
  public void setUp() throws Exception {
    IN_FILE = OptionConst.CSV;
    input1 = new String[] {"--csv-file", "todo.csv", "--add-todo", "--todo-text", "wash dishes"};
    input2 = new String[] {
          "--csv-file",
          "todo.csv",
          "--add-todo",
          "--todo-text",
          "wash dishes",
          "--completed",
          "--due",
          "08/22/2021",
          "--priority",
          "3",
          "--category",
          "home",
          "--display",
          "--sort-by-date"
        };
    input3 = new String[] {"--csv-file", "todo.csv", "--add-todo", "--todo-text", "wash dishes", "--completed", "--show-incomplete", "--sort-by-date", "sort-by-priority"};
    invalidInput1 = new String[] {"todo.csv", "--add-todo", "--todo-text", "wash dishes"};
    invalidInput2 = new String[] {
          "--csv-file", "todo.csv", "--add-todo", "--todo-text", "wash dishes", "--invalid"
        };
    invalidInput3 = new String[] {"--csv-file", "todo.csv", "--sort-by-date", "--sort-by-priority"};

    invalidInput4 = new String[] {"--cs-file", "todo.csv", "--sort-by-date", "--sort-by-priority"};

    invalidInput5 = new String[] {"--cs-file", "todo.csv", "--sort-by-date", null};


    cp1 = new CmdLineParser(input1);
    cp2 = new CmdLineParser(input2);
    validOptions1 = OptionConst.validOptions;
    validOptions2 = OptionConst.validOptions;
    requiredOptions1 = OptionConst.requiredOptions;
    requiredOptions2 = OptionConst.requiredOptions;
    //cp3 = new CmdLineParser(invalidInput3);
  }

  @Test
  public void getOptions() {
    Map<String,String> m1= new HashMap<>();
    m1.put("--csv-file","todo.csv");
    m1.put("--add-todo",null);
    m1.put("--todo-text","wash dishes");
    Map<String,String> m2 = cp1.getOptions();
    assertEquals(m1,m2);
  }

  @Test
  public void processArgs() {
    //List<String> options1 = super.extractOptions(args1, OptionConst.OPTION);

  }

  @Test
  public void getArg() {
    String op1 = "--csv-file";
    String expe = "todo.csv";
    assertEquals(expe,cp1.getArg(op1));
  }

  @Test
  public void createOptions() {
    List<String> arguments = Arrays.asList(input1);
    String csvPath1 = arguments.get(arguments.indexOf(OptionConst.CSV_FILE) + 1);
    String todoText1 = arguments.get(arguments.indexOf(OptionConst.TODO_TEXT) + 1);
    assertEquals(csvPath1,"todo.csv");
    assertEquals(todoText1,"wash dishes");
  }


  @Test
  public void testEqualsNull() {
    assertFalse(cp1.equals(null));
  }

  @Test
  public void testEqualsReflexivity() {
    assertTrue(cp1.equals(cp1));
  }

  @Test
  public void testEqualsSymmetry() {
    assertFalse(cp1.equals(cp2));
  }


  @Test
  public void testHashCode() {
    assertFalse(0 == cp1.hashCode());
  }

  @Test
  public void testHashCodeConsistency() {
    int testHashCode = cp1.hashCode();
    assertEquals(testHashCode, cp1.hashCode());
  }

  @Test
  public void testToString() {
    String expected =  "CmdLineParser{" +
            "validOptions=" + OptionConst.validOptions +
            ", requiredOptions=" + OptionConst.requiredOptions +
            ", options=" + cp1.getOptions() +
            ", IN_FILE='" + OptionConst.CSV + '\'' +
            '}';
    assertEquals(expected, cp1.toString());
  }

  @Test
  public void testConstructor() {
    CmdLineParser cp = new CmdLineParser(input3);
  }

  @Test(expected = CmdLineExceptions.MissingOptionException.class)
  public void testConstructorMissingRequiredOption() {
    cp3 = new CmdLineParser(invalidInput1);
  }

  @Test(expected = CmdLineExceptions.InvalidOptionException.class)
  public void testConstructorInvalidOption() {
    cp3 = new CmdLineParser(invalidInput2);
  }


  @Test(expected = CmdLineExceptions.InvalidOptionException.class)
  public void testConstructorIllegalPathTypeException() {
    cp3 = new CmdLineParser(invalidInput4);
    cp3.processArgs(invalidInput4);
  }

//  @Test(expected = CmdLineExceptions.NullArgumentException.class)
//  public void testConstructorNullArgumentException() {
//    cp3 = new CmdLineParser(invalidInput5);
//    //cp3.getArg(invalidInput5);
//  }

}