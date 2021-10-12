package todoApp.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/** Task class represent a single task */
public class Task {
  private static final Integer DEFAULT_PRIORITY = 3;
  private static final Boolean DEFAULT_COMPLETED = false;
  private String text; // required
  private Boolean completed; // default false
  private LocalDate due; // optional
  private Integer priority; //  optional 1-3, default 3
  private String category; // optional
  private String id;
  private static final String QUOTED_QUESTION_MARK = "\"?\"";

  /**
   * Constructor of a task
   *
   * @param builder - Builder
   */
  public Task(Builder builder) {
    this.text = builder.text;
    this.completed = builder.completed;
    this.due = builder.due;
    this.priority = builder.priority;
    this.category = builder.category; // added category -- Jing
  }

  /** @return - String, giving the text of a task */
  public String getText() {
    return this.text;
  }

  /** @return - Boolean, true if the task is complete, false if the task is incomplete. */
  public Boolean getCompleted() {
    return this.completed;
  }

  /** @return - LocalDate, the due date of the task. */
  public LocalDate getDue() {
    return this.due;
  }

  /** @return - Integer, the priority of this task. */
  public Integer getPriority() {
    return this.priority;
  }

  /** @return - String, the category of this task. */
  public String getCategory() {
    return this.category;
  }

  /** @param completed - boolean, true if set to complete false if incomplete. */
  public void setCompleted(Boolean completed) { // added setCompleted() --Jing
    this.completed = completed;
  }

  /** @return String, giving the id of this task. */
  public String getId() {
    return id;
  }

  /** @param id - String, the id the task to be set. */
  public void setId(String id) {
    this.id = id;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Task)) {
      return false;
    }
    Task todo = (Task) o;
    return Objects.equals(getText(), todo.getText())
        && Objects.equals(getCompleted(), todo.getCompleted())
        && Objects.equals(getDue(), todo.getDue())
        && Objects.equals(getPriority(), todo.getPriority())
        && Objects.equals(getCategory(), todo.getCategory());
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(getText(), getCompleted(), getDue(), getPriority(), getCategory());
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "Task{"
        + "text='"
        + text
        + '\''
        + ", completed="
        + completed
        + ", due="
        + due
        + ", priority="
        + priority
        + ", category='"
        + category
        + '\''
        + ", id='"
        + id
        + '\''
        + '}';
  }

  /**
   * convert the task to a List of String representation.
   *
   * @return - List
   */
  public List<String> toStringList() {

    List<String> ret = new ArrayList<>();
    ret.add(this.id);

    String quotedText = this.wrapQuote(this.text);
    ret.add(quotedText);

    String quotedComp = this.wrapQuote(this.completed.toString());
    ret.add(quotedComp);

    if (this.due == null) {
      ret.add(this.QUOTED_QUESTION_MARK);
    } else {
      String quotedDue = wrapQuote(this.due.toString());
      ret.add(quotedDue);
    }

    if (this.priority == null) {
      ret.add(this.QUOTED_QUESTION_MARK);
    } else {
      String quotedPriority = wrapQuote(this.priority.toString());
      ret.add(quotedPriority);
    }

    if (this.category == null) {
      ret.add(this.QUOTED_QUESTION_MARK);
    } else {
      String quotedCategory = wrapQuote(this.category);
      ret.add(quotedCategory);
    }

    return ret;
  }

  /**
   * Wrap quote to a string
   *
   * @param s - String
   * @return - String, the string that wrapped with quote.
   */
  private String wrapQuote(String s) {
    return ("\"" + s + "\"");
  }

  /** Builder class to build a task. */
  public static class Builder {
    private String text; // required
    private Boolean completed; // default false
    private LocalDate due; // optional
    private Integer priority; //  optional 1-3, default 3
    private String category; // optional

    public Builder(String text) {
      this.text = text;
      this.completed = DEFAULT_COMPLETED;
      this.priority = DEFAULT_PRIORITY;
    }

    public Builder completed(boolean completed) { // added argument boolean completed --Jing
      this.completed = completed;
      return this;
    }

    public Builder due(LocalDate date) {
      this.due = date;
      return this;
    }

    public Builder priority(Integer priority) {
      this.priority = priority;
      return this;
    }

    public Builder category(String s) {
      this.category = s;
      return this;
    }

    public Task build() { // printout the information
      return new Task(this);
    }
  }
}
