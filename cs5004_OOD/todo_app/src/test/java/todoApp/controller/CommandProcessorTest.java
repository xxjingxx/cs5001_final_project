package todoApp.controller;

import static org.junit.Assert.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import todoApp.commandLineParser.CmdLineExceptions;
import todoApp.commandLineParser.CmdLineParser;
import todoApp.commandLineParser.OptionConst;
import todoApp.model.Task;
import todoApp.model.Task.Builder;
import todoApp.model.TaskData;
import todoApp.view.DisplayHelper;

public class CommandProcessorTest {

  private ErrorLogger commandErrorLog1;
  private CommandProcessor commandProcessor1;
  private String input1[];
  private String input2[];
  private String input3[];
  private String invalidInput[];
  private String invalidInput2[];
  private String invalidInput3[];
  private String invalidInput4[];
  private CmdLineParser cp1;
  private Task t;
  public Builder b;
  private ErrorLogger commandErrorLog2;

  private CmdLineParser cp2;
  private CmdLineParser cp3;
  private CmdLineParser cp4;

  @Before
  public void setUp() throws Exception {
    commandErrorLog1 = new ErrorLogger();
    commandProcessor1 = new CommandProcessor();
    input1 = new String[] {"--csv-file", "todo.csv", "--add-todo", "--todo-text", "wash dishes","--display"};
    input2 = new String[] {"--csv-file", "todo.csv", "--add-todo", "--todo-text", "Finish HW9","--display", "--due", "2021-08-02", "--priority", "1", "--category", "school"};
    input3 = new String[] {"--csv-file", "todo.csv"};
    invalidInput = new String[] {"--csv-file", "todo.csv", "--complete-todo", "a"};
    invalidInput2 = new String[] {"--csv-file", "todo.csv", "--complete-todo", "99"};
    invalidInput3 = new String[] {"--csv-file", "todo.csv", "--add-todo", "--todo-text", "Finish HW9","--display", "--due", "08/02/2021"};
    invalidInput4 = new String[] {"--csv-file", "todo.csv", "--add-todo", "--todo-text", "Finish HW9","--display", "--due", "2021-08-02", "--priority", "a", "--category", "school"};
    cp1 = new CmdLineParser(input1);
    b = new Builder("wash dishes");
    t = new Task(b);
    cp2 = new CmdLineParser(invalidInput);
    cp3 = new CmdLineParser(invalidInput2);
    cp4 = new CmdLineParser(input3);
  }

  @Test
  public void getCommandErrorLog() {
    commandErrorLog2 = new ErrorLogger();
    assertEquals(commandProcessor1.getCommandErrorLog(),commandErrorLog2);
  }

  @Test
  public void processNewTodo() {
    Task actual = commandProcessor1.processNewTodo(cp1);
    assertEquals(actual,t);
  }

  @Test
  public void processNewTodo2() {
    CmdLineParser cp = new CmdLineParser(input2);
            Task t1 =
            new Task.Builder("Finish HW9")
                    .due(LocalDate.of(2021, 8, 2))
                    .priority(1)
                    .category("school")
                    .build();
    Task actual = commandProcessor1.processNewTodo(cp);
    assertEquals(t1, actual);
  }

  @Test
  public void processNewTodo3() {
    Task actual = commandProcessor1.processNewTodo(cp4);
    assertEquals(null, actual);
  }


  @Test
  public void processNewTodoFailInvalidDate() {
    CmdLineParser cp = new CmdLineParser(invalidInput3);
    Task invalidTask = commandProcessor1.processNewTodo(cp);
    ErrorLogger el = new ErrorLogger();
    el.log("Please provide due date in format: YYYY-MM-DD.");
    assertEquals(el, commandProcessor1.getCommandErrorLog());
  }

  @Test
  public void processNewTodoFailInvalidPriority() {
    CmdLineParser cp = new CmdLineParser(invalidInput4);
    Task invalidTask = commandProcessor1.processNewTodo(cp);
    ErrorLogger el = new ErrorLogger();
    el.log("Priority must be an integer.");
    assertEquals(el, commandProcessor1.getCommandErrorLog());
  }

  @Test
  public void processCompleteTodo() {
    TaskData td = new TaskData();
    Task t1 = new Task.Builder("wash dishes").priority(3).category("home").build();
    Task t2 = new Task.Builder("do homework").priority(2).category("school").due(LocalDate.now()).build();
    td.addTodo(t1);
    td.addTodo(t2);
    commandProcessor1.processCompleteTodo(cp1,td);

  }

  @Test
  public void processCompleteTodoError() {
    ErrorLogger expectedEL = new ErrorLogger();

    TaskData td = new TaskData();
    Task t1 = new Task.Builder("wash dishes").priority(3).category("home").build();
    Task t2 = new Task.Builder("do homework").priority(2).category("school").due(LocalDate.now()).build();
    td.addTodo(t1);
    td.addTodo(t2);

    commandProcessor1.processCompleteTodo(cp2,td);
    expectedEL.log("ID must be an integer.");
    assertEquals(expectedEL, commandProcessor1.getCommandErrorLog());

  }

  @Test
  public void processCompleteTodoError2() {
    ErrorLogger expectedEL = new ErrorLogger();

    TaskData td = new TaskData();
    Task t1 = new Task.Builder("wash dishes").priority(3).category("home").build();
    Task t2 = new Task.Builder("do homework").priority(2).category("school").due(LocalDate.now()).build();
    td.addTodo(t1);
    td.addTodo(t2);

    commandProcessor1.processCompleteTodo(cp3,td);
    expectedEL.log("Invalid ID number.");
    assertEquals(expectedEL, commandProcessor1.getCommandErrorLog());

  }

  @Test
  public void selectIncomplete() {
    List<List<String>> re = new ArrayList<>();
    List<List<String>> ex = new ArrayList<>();
    String s1[] = new String[]{"\"1\"", "\"wash dishes\"", "\"false\"", "\"?\"", "\"3\"", "\"home\""};
    String s2[] = new String[]{"\"2\"", "\"do homework\"", "\"false\"", "\"?\"", "\"2\"", "\"school\""};
    List<String> a1 = Arrays.asList(s1);
    List<String> a2 = Arrays.asList(s2);
    ex.add(a1);
    ex.add(a2);
    TaskData td = new TaskData();
    Task t1 = new Task.Builder("wash dishes").priority(3).category("home").build();
    Task t2 = new Task.Builder("do homework").priority(2).category("school").build();
    td.addTodo(t1);
    td.addTodo(t2);
    re = commandProcessor1.selectIncomplete(td);
    assertEquals(ex,re);
  }

  @Test
  public void selectCategory() {
    List<List<String>> re = new ArrayList<>();
    List<List<String>> ex = new ArrayList<>();
    String s1[] = new String[]{"\"1\"", "\"wash dishes\"", "\"false\"", "\"?\"", "\"3\"", "\"home\""};
    String s2[] = new String[]{"\"2\"", "\"do homework\"", "\"false\"", "\"?\"", "\"2\"", "\"school\""};
    List<String> a1 = Arrays.asList(s1);
    ex.add(a1);
    TaskData td = new TaskData();
    Task t1 = new Task.Builder("wash dishes").priority(3).category("home").build();
    Task t2 = new Task.Builder("do homework").priority(2).category("school").build();
    td.addTodo(t1);
    td.addTodo(t2);
    re = commandProcessor1.selectCategory(td,"home");
    assertEquals(ex,re);
  }

  @Test
  public void selectSortByDate() {
    List<List<String>> re = new ArrayList<>();
    List<List<String>> ex = new ArrayList<>();
    String s1[] = new String[]{"\"1\"", "\"wash dishes\"", "\"false\"", "\"?\"", "\"3\"", "\"home\""};
    String s2[] = new String[]{"\"2\"", "\"do homework\"", "\"false\"", "\"?\"", "\"2\"", "\"school\""};
    List<String> a1 = Arrays.asList(s1);
    List<String> a2 = Arrays.asList(s2);
    ex.add(a1);
    ex.add(a2);
    TaskData td = new TaskData();
    Task t1 = new Task.Builder("wash dishes").priority(3).category("home").build();
    Task t2 = new Task.Builder("do homework").priority(2).category("school").build();
    td.addTodo(t1);
    td.addTodo(t2);
    re = commandProcessor1.selectSortByDate(td);
    assertEquals(ex,re);
  }

  @Test
  public void selectSortByPriority() {
    List<List<String>> re = new ArrayList<>();
    List<List<String>> ex = new ArrayList<>();
    String s1[] = new String[]{"\"1\"", "\"wash dishes\"", "\"false\"", "\"?\"", "\"3\"", "\"home\""};
    String s2[] = new String[]{"\"2\"", "\"do homework\"", "\"false\"", "\"?\"", "\"2\"", "\"school\""};
    List<String> a1 = Arrays.asList(s1);
    List<String> a2 = Arrays.asList(s2);
    ex.add(a2);
    ex.add(a1);
    TaskData td = new TaskData();
    Task t1 = new Task.Builder("wash dishes").priority(3).category("home").build();
    Task t2 = new Task.Builder("do homework").priority(2).category("school").build();
    td.addTodo(t1);
    td.addTodo(t2);
    re = commandProcessor1.selectSortByPriority(td);
    assertEquals(ex,re);
  }

//  @Test
//  public void processDisplay() throws IOException {
//    String expectedFilePath = "todos.csv";
//    FileProcessor fp = new FileProcessor(expectedFilePath);
//    commandProcessor1.processDisplay(cp1,fp);
//    TaskData current = fp.getTaskData();
//    assertTrue(cp1.getOptions().containsKey(OptionConst.DISPLAY));
//    assertFalse(cp1.getOptions().containsKey(OptionConst.SHOW_INCOMPLETE));
//    assertFalse(cp1.getOptions().containsKey(OptionConst.SHOW_CATEGORY));
//    assertFalse(cp1.getOptions().containsKey(OptionConst.SORT_BY_DATE));
//    assertFalse(cp1.getOptions().containsKey(OptionConst.SORT_BY_PRIORITY));
//    String output1 = DisplayHelper.createTable(fp.getHeaders(), CommandProcessor.selectIncomplete(current));
//
//    String expectedFilePath1 = "todos1.csv";
//    FileProcessor fp1 = new FileProcessor(expectedFilePath1);
//    commandProcessor1.processDisplay(cp1,fp1);
//    TaskData current1 = fp.getTaskData();
//    assertTrue(cp1.getOptions().containsKey(OptionConst.DISPLAY));
//    assertFalse(cp1.getOptions().containsKey(OptionConst.SHOW_INCOMPLETE));
//    assertFalse(cp1.getOptions().containsKey(OptionConst.SHOW_CATEGORY));
//    assertFalse(cp1.getOptions().containsKey(OptionConst.SORT_BY_DATE));
//    assertFalse(cp1.getOptions().containsKey(OptionConst.SORT_BY_PRIORITY));
//    String output11 = DisplayHelper.createTable(fp.getHeaders(), CommandProcessor.selectIncomplete(current1));
//
//    assertEquals(output1, output11);
//
////    String output3 = DisplayHelper.createTable(fp.getHeaders(), CommandProcessor.selectSortByDate(current));
////    String output4 = DisplayHelper.createTable(fp.getHeaders(), CommandProcessor.selectSortByPriority(current));
////    assertTrue(output1.contains("text"));
////    assertTrue(output3.contains("id"));
////    assertTrue(output4.contains("due"));
//  }
}