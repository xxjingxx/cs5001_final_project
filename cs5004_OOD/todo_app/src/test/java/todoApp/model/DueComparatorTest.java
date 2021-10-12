package todoApp.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

public class DueComparatorTest {
  private Task task1;
  private Task task2;
  private Task task3;
  private Task task4;
  private Task task5;
  private DueComparator<Task> comparator;

  @Before
  public void setUp() throws Exception {
    task1 = new Task.Builder("finish a7").category("school").priority(3)
        .due(LocalDate.of(2019, 10, 3)).completed(true).build();
    task3 = new Task.Builder("finish a8").category("school").priority(1)
        .due(LocalDate.of(2020, 3, 1)).build();
    task2 = new Task.Builder("finish work").category("work").priority(2).build();
    comparator = new DueComparator<>();
    task4 = new Task.Builder("finish shopping").category("shopping").priority(2).build();
    comparator = new DueComparator<>();
    task5 = new Task.Builder("finish a6").category("school").priority(3)
        .due(LocalDate.of(2019, 10, 3)).completed(true).build();
  }

  @Test
  public void compare() {
    int result = comparator.compare(task1, task2);
    assertEquals(result, -1);
  }

  @Test
  public void compare2() {
    int result = comparator.compare(task2, task1);
    assertEquals(result, 1);
  }

  @Test
  public void compare3() {
    int result = comparator.compare(task2, task4);
    assertEquals(result, 0);
  }

  @Test
  public void compare4() {
    int result = comparator.compare(task1, task3);
    assertEquals(result, -1);
  }

  @Test
  public void compare5() {
    int result = comparator.compare(task3, task1);
    assertEquals(result, 1);
  }

  @Test
  public void compare6() {
    int result = comparator.compare(task1, task5);
    assertEquals(result, 1);
  }

}