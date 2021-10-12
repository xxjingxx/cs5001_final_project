package todoApp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a collection of task. Task Data contains a list of Tasks and a list of list
 * of string representing each task.
 */
public class TaskData {

  private List<Task> todos;
  private List<List<String>> stringData;
  private Integer id;

  /** constructs a TaskData. */
  public TaskData() {
    this.todos = new ArrayList<>();
    this.stringData = new ArrayList<>();
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TaskData taskData = (TaskData) o;
    return todos.equals(taskData.todos)
        && stringData.equals(taskData.stringData)
        && id.equals(taskData.id);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(todos, stringData, id);
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "TaskData{" + "todos=" + todos + ", stringData=" + stringData + ", id=" + id + '}';
  }

  /** @return - List, giving the tasks stored in task data. */
  public List<Task> getTodos() {
    return todos;
  }

  /** @return - List, giving the tasks strings in the task data. */
  public List<List<String>> getStringData() {
    return stringData;
  }

  /**
   * Adds a new task.
   *
   * @param task - Task, the new task to be added.
   */
  public void addTodo(Task task) {
    if (this.todos.contains(task)) {
      throw new IllegalArgumentException("Task already exists.");
    }
    this.id = todos.size() + 1;
    String quotedID = "\"" + this.id + "\"";
    task.setId(quotedID);
    this.todos.add(task);
    List<String> taskList = task.toStringList();
    this.stringData.add(taskList);
  }

  /**
   * Completes a task.
   *
   * @param id - the id of the task needs to be completed.
   */
  public void completeATask(Integer id) {
    int index = id - 1;
    if (id > this.todos.size() || id < 1) {
      throw new IllegalArgumentException("Task id is invalid.");
    }
    if (this.todos.get(index) == null) {
      throw new IllegalArgumentException("Task id is invalid.");
    }
    Task updated = this.todos.get(index);
    updated.setCompleted(true);
    this.todos.set(index, updated);
    List<String> taskList = updated.toStringList();
    this.stringData.set(index, taskList);
  }
}
