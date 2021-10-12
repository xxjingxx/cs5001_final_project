package todoApp.model;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TaskFilterTest {
  Task task1;
  Task task2;
  List<Task> list;
  @Before
  public void setUp() throws Exception {
    task1 = new Task.Builder("finish a7").category("school").priority(3)
        .due(LocalDate.of(2019, 10, 3)).completed(true).build();
    task2 = new Task.Builder("finish work").category("work").priority(2).build();
    list = new ArrayList<>();
    list.add(task1);
    list.add(task2);
  }

  @Test
  public void incomplete() {
    List<Task> filtered = TaskFilter.incomplete(list);
    assertFalse(filtered.contains(task1));

  }

  @Test
  public void category() {
    List<Task> filtered = TaskFilter.category(list, "school");
    assertFalse(filtered.contains(task2));
  }

  @Test
  public void main() {
  }
}