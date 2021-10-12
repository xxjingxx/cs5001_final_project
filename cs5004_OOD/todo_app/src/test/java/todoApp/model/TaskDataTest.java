package todoApp.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;
import todoApp.model.Task.Builder;

public class TaskDataTest {
  private List<Task> expectedTodos;
  private List<List<String>> expectedStringData;
  private Integer expectedID;
  private TaskData testTaskData;
  private Task testTask;

  private List<Task> expectedTodos2;
  private List<List<String>> expectedStringData2;
  private Integer expectedID2;
  private TaskData testTaskData2;
  private Task testTask2;

  @Before
  public void setUp() throws Exception {
    expectedTodos = new ArrayList<>();
    expectedStringData = new ArrayList<>();
    testTaskData = new TaskData();
    expectedID = 1;
    testTask = new Builder("task").priority(2).category("work")
        .due(LocalDate.of(2020, 4, 5)).completed(false).build();
    testTaskData.addTodo(testTask);

    expectedTodos2 = new ArrayList<>();
    expectedStringData2 = new ArrayList<>();
    testTaskData2 = new TaskData();
    expectedID2 = 1;
    testTask2 = new Builder("shopping").priority(1).category("life")
        .due(LocalDate.of(2020, 2, 4)).completed(false).build();
    testTaskData2.addTodo(testTask2);
  }

  @Test
  public void getTodos() {
    ArrayList<Task> expected = new ArrayList<>();
    expected.add(testTask);
    assertEquals(expected, testTaskData.getTodos());
  }

  @Test
  public void getStringData() {
    List<String> test = testTask.toStringList();
    expectedStringData.add(test);
    assertEquals(expectedStringData, testTaskData.getStringData());
  }

  @Test
  public void addTodo() {
    Task testTask2 = new Builder("hw8").priority(1).category("school")
        .due(LocalDate.of(2021, 2, 3)).completed(true).build();
    testTaskData.addTodo(testTask2);
    ArrayList<Task> expected = new ArrayList<>();
    expected.add(testTask);
    expected.add(testTask2);
    assertEquals(expected, testTaskData.getTodos());
  }

  @Test (expected = IllegalArgumentException.class)
  public void addTodo2() {
    testTaskData.addTodo(testTask);
  }

  @Test
  public void completeATask() {
    testTaskData.completeATask(1);
    assertTrue(testTask.getCompleted().equals(true));
  }

  @Test (expected = IllegalArgumentException.class)
  public void completeATask2() {
    testTask = new Builder("task").build();
    testTaskData.completeATask(2);
  }

  @Test
  public void equalsReflexivity() {
    assertTrue(testTaskData.equals(testTaskData));
  }

  @Test
  public void equalsReflexivity2() {
    assertFalse(testTaskData.equals(testTaskData2));
  }

  @Test
  public void equalsNotSameObject() {
    assertFalse(testTaskData.equals(expectedID));
  }

  @Test
  public void equalsSameField() {
    TaskData testTaskData3 = new TaskData();
    testTaskData3.addTodo(testTask);
    assertEquals(testTaskData, testTaskData3);
  }


  @Test
  public void testHashCode() {
    int testHash = Objects.hash(testTaskData.getTodos(), testTaskData.getStringData(), expectedID);
    assertEquals(testHash, testTaskData.hashCode());
  }

  @Test
  public void testToString() {
    String test = "TaskData{" + "todos=" + testTaskData.getTodos() + ", stringData=" + testTaskData.getStringData() + ", id=" + expectedID + '}';
    assertEquals(test, testTaskData.toString());
  }
}