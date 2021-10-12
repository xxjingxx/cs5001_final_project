package todoApp.model;

import java.util.List;
import java.util.stream.Collectors;

/** Represents a task filter. */
public class TaskFilter {

  /**
   * Filters incomplete task.
   *
   * @param task - List, a list of tasks.
   * @return List, filter result
   */
  public static List<Task> incomplete(List<Task> task) {
    return task.stream().filter(t -> t.getCompleted() == false).collect(Collectors.toList());
  }

  /**
   * Filters tasks under certain category.
   *
   * @param task - List, a list of tasks.
   * @param showCategory - String, a category user specifies
   * @return - List, filter result
   */
  public static List<Task> category(List<Task> task, String showCategory) {
    return task.stream()
        .filter(t -> t.getCategory().equals(showCategory))
        .collect(Collectors.toList());
  }
}
