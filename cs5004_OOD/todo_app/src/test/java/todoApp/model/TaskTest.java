package todoApp.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;
import todoApp.model.Task.Builder;

public class TaskTest {
  private String expectedText;
  private Boolean expectedCompleted;
  private LocalDate expectedDue;
  private Integer expectedPriority;
  private String expectedCategory;
  private String expectedId;
  private Task testTask;

  private String expectedText2;
  private Boolean expectedCompleted2;
  private LocalDate expectedDue2;
  private Integer expectedPriority2;
  private String expectedCategory2;
  private String expectedId2;
  private Task testTask2;

  @Before
  public void setUp() throws Exception {
    expectedText = "hw9";
    expectedCompleted = true;
    expectedDue = LocalDate.of(2021, 3, 4);
    expectedPriority = 2;
    expectedCategory = "school";
    expectedId = null;
    testTask = new Builder(expectedText).priority(expectedPriority).category(expectedCategory)
        .due(expectedDue).completed(expectedCompleted).build();

    expectedText2 = "finish project";
    expectedCompleted2 = false;
    expectedDue2 = LocalDate.of(2022, 3, 1);
    expectedPriority2 = 1;
    expectedCategory2 = "work";
    testTask2 = new Builder(expectedText2).priority(expectedPriority2).category(expectedCategory2)
        .due(expectedDue2).completed(expectedCompleted2).build();
  }

  @Test
  public void getText() {
    assertEquals(expectedText, testTask.getText());
  }

  @Test
  public void getCompleted() {
    assertEquals(expectedCompleted, testTask.getCompleted());
  }

  @Test
  public void getDue() {
    assertEquals(expectedDue, testTask.getDue());
  }

  @Test
  public void getPriority() {
    assertEquals(expectedPriority, testTask.getPriority());
  }

  @Test
  public void getCategory() {
    assertEquals(expectedCategory, testTask.getCategory());
  }

  @Test
  public void getID() {
    assertEquals(expectedId, testTask.getId());
  }

  @Test
  public void setID() {
    testTask.setId("5");
    assertTrue(testTask.getId().equals("5"));

  }

  @Test
  public void setCompleted() {
    testTask.setCompleted(false);
    assertTrue(testTask.getCompleted().equals(false));
  }

  @Test
  public void equalsReflexivity() {
    assertTrue(testTask.equals(testTask));
  }

  @Test
  public void equalsReflexivity2() {
    assertFalse(testTask.equals(testTask2));
  }

  @Test
  public void equalsNotSameObject() {
    assertFalse(testTask.equals(expectedCategory));
  }

  @Test
  public void equalsSameField() {
    Task testTask2 = new Builder(expectedText).priority(expectedPriority).category(expectedCategory)
        .due(expectedDue).completed(expectedCompleted).build();
    assertEquals(testTask, testTask2);
  }

  @Test
  public void equalsNotSamePriority() {
    Task testTask2 = new Builder(expectedText).priority(expectedPriority2).category(expectedCategory)
        .due(expectedDue).completed(expectedCompleted).build();
    assertFalse(testTask2.equals(testTask));
  }

  @Test
  public void equalsNotSameCategory() {
    Task testTask2 = new Builder(expectedText2).priority(expectedPriority).category(expectedCategory2)
        .due(expectedDue).completed(expectedCompleted).build();
    assertFalse(testTask2.equals(testTask));
  }

  @Test
  public void equalsNotSameDue() {
    Task testTask2 = new Builder(expectedText).priority(expectedPriority).category(expectedCategory)
        .due(expectedDue2).completed(expectedCompleted).build();
    assertFalse(testTask2.equals(testTask));
  }

  @Test
  public void equalsNotSameText() {
    Task testTask2 = new Builder(expectedText2).priority(expectedPriority).category(expectedCategory)
        .due(expectedDue).completed(expectedCompleted).build();
    assertFalse(testTask2.equals(testTask));
  }

  @Test
  public void equalsNotSameCompleted() {
    Task testTask2 = new Builder(expectedText2).priority(expectedPriority).category(expectedCategory)
        .due(expectedDue).completed(expectedCompleted2).build();
    assertFalse(testTask2.equals(testTask));
  }

  @Test
  public void testHashCode() {
    int testHashCode = Objects
        .hash(expectedText, expectedCompleted, expectedDue, expectedPriority, expectedCategory);
    assertEquals(testHashCode, testTask.hashCode());
  }

  @Test
  public void testToString() {
    String testString = "Task{"
        + "text='"
        + expectedText
        + '\''
        + ", completed="
        + expectedCompleted
        + ", due="
        + expectedDue
        + ", priority="
        + expectedPriority
        + ", category='"
        + expectedCategory
        + '\''
        + ", id='"
        + expectedId
        + '\''
        + '}';
    assertEquals(testString, testTask.toString());
  }

  @Test
  public void toStringList() {
    List<String> test = new ArrayList<>();
    test.add(null);
    test.add("\"hw9\"");
    test.add("\"true\"");
    test.add("\"2021-03-04\"");
    test.add("\"2\"");
    test.add("\"school\"");

    assertEquals(test, testTask.toStringList());
  }
}