package todoApp.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class PriorityComparatorTest {
  Task task1;
  Task task2;
  Task task3;
  private PriorityComparator<Task> comparator;

  @Before
  public void setUp() throws Exception {
    task1 = new Task.Builder("finish a7").category("school").priority(3)
        .due(LocalDate.of(2019, 10, 3)).completed(true).build();
    task2 = new Task.Builder("finish work").category("work").priority(2).build();
    comparator = new PriorityComparator<>();
    task3 = new Task.Builder("finish cooking").category("work").priority(2).build();
    comparator = new PriorityComparator<>();
  }

  @Test
  public void compare() {
    int expected = comparator.compare(task1, task2);
    assertEquals(expected, 1);
  }

  @Test
  public void compare2() {
    int expected = comparator.compare(task2, task1);
    assertEquals(expected, -1);
  }

  @Test
  public void compare3() {
    int expected = comparator.compare(task2, task3);
    assertEquals(expected, 0);
  }
}