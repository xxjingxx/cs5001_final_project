package todoApp.commandLineParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Maintains all the option names, descriptions, and related String constants for this program. */
public final class OptionConst {

  public static Map<String, Option> allOptions = new HashMap<>(); // used to generate error message.
  public static List<String> requiredOptions = new ArrayList<>();
  public static List<String> validOptions = new ArrayList<>();

  public static final String CSV_FILE = "--csv-file"; // required
  public static final String ADD_TODO = "--add-todo"; // if provided, needs TODO_TEXT
  public static final String TODO_TEXT = "--todo-text";
  public static final String COMPLETED = "--completed"; // optional
  public static final String DUE = "--due"; // optional
  public static final String PRIORITY = "--priority"; // optional
  public static final String CATEGORY = "--category"; // optional
  public static final String COMPLETE_TODO = "--complete-todo"; // needs an ID after it
  public static final String DISPLAY =
      "--display"; // display all todos if no following arg provided
  public static final String SHOW_INCOMPLETE = "--show-incomplete"; // optional
  public static final String SHOW_CATEGORY = "--show-category"; //  optional
  public static final String SORT_BY_DATE = "--sort-by-date"; // optional
  public static final String SORT_BY_PRIORITY = "--sort-by-priority"; // optional

  public static final String CSV_FILE_DESCRIPTION =
      "--csv-file  <path/to/file> The CSV file containing the todos. This option is required";
  public static final String ADD_TODO_DESCRIPTION =
      "--add-todo Add a new todo.  If this option is provided, then --todo-text must also be provided.";
  public static final String TOTO_TEXT_DESCRIPTION =
      "--todo-text <description of todo>  A description of the todo.";
  public static final String COMPLETED_DESCRIPTION =
      "--completed  (Optional) Sets the completed status of a new todo to true.";
  public static final String DUE_DESCRIPTION =
      "--due <due date>  (Optional) Sets the due date of a new todo. You may choose how the data should be formatted.";
  public static final String PRIORITY_DESCRIPTION =
      "--priority <1, 2, or 3>  (Optional) Sets the priority of a new todo. The value can be 1, 2, or 3.";
  public static final String CATEGORY_DESCRIPTION =
      "--category <a category name>  (Optional) Sets the category of a new todo. The value can be any String. Categories do not need to be pre-defined.";
  public static final String COMPLETE_TODO_DESCRIPTION =
      "--complete-todo <id>  Mark the Todo with the provided ID as complete.";
  public static final String DISPLAY_DESCRIPTION =
      "--display  Display todos. If none of the following optional arguments are provided, displays all todos.";
  public static final String SHOW_INCOMPLETE_DESCRIPTION =
      "--show-incomplete  (Optional) If --display is provided, only incomplete todos should be displayed.";
  public static final String SHOW_CATEGORY_DESCRIPTION =
      "--show-category <category>  (Optional) If --display is provided, only todos with the given category should be displayed.";
  public static final String SORT_BY_DATE_DESCRIPTION =
      "--sort-by-date  (Optional) If --display is provided, sort the list of todos by date order (ascending). Cannot be combined with --sort-by-priority.";
  public static final String SORT_BY_PRIORITY_DESCRIPTION =
      "--sort-by-priority  (Optional) If --display is provided, sort the list of todos by priority (ascending). Cannot be combined with --sort-by-date";

  public static final String CSV = ".*\\.csv$";
  public static final String OPTION = "--.*";

  static {
    allOptions.put(CSV_FILE, new Option(CSV_FILE, CSV_FILE_DESCRIPTION));
    allOptions.put(ADD_TODO, new Option(ADD_TODO, ADD_TODO_DESCRIPTION));
    allOptions.put(TODO_TEXT, new Option(TODO_TEXT, TOTO_TEXT_DESCRIPTION));
    allOptions.put(COMPLETED, new Option(COMPLETED, COMPLETED_DESCRIPTION));
    allOptions.put(DUE, new Option(DUE, DUE_DESCRIPTION));
    allOptions.put(PRIORITY, new Option(PRIORITY, PRIORITY_DESCRIPTION));
    allOptions.put(CATEGORY, new Option(CATEGORY, CATEGORY_DESCRIPTION));
    allOptions.put(COMPLETE_TODO, new Option(COMPLETE_TODO, COMPLETE_TODO_DESCRIPTION));
    allOptions.put(DISPLAY, new Option(DISPLAY, DISPLAY_DESCRIPTION));
    allOptions.put(SHOW_INCOMPLETE, new Option(SHOW_INCOMPLETE, SHOW_INCOMPLETE_DESCRIPTION));
    allOptions.put(SHOW_CATEGORY, new Option(SHOW_CATEGORY, SHOW_CATEGORY_DESCRIPTION));
    allOptions.put(SORT_BY_DATE, new Option(SORT_BY_DATE, SORT_BY_DATE_DESCRIPTION));
    allOptions.put(SORT_BY_PRIORITY, new Option(SORT_BY_PRIORITY, SORT_BY_PRIORITY_DESCRIPTION));
  }

  static {
    requiredOptions.add(CSV_FILE);
  }

  static {
    validOptions.add(CSV_FILE);
    validOptions.add(ADD_TODO);
    validOptions.add(TODO_TEXT);
    validOptions.add(COMPLETED);
    validOptions.add(DUE);
    validOptions.add(PRIORITY);
    validOptions.add(CATEGORY);
    validOptions.add(COMPLETE_TODO);
    validOptions.add(DISPLAY);
    validOptions.add(SHOW_INCOMPLETE);
    validOptions.add(SHOW_CATEGORY);
    validOptions.add((SORT_BY_DATE));
    validOptions.add(SORT_BY_PRIORITY);
  }
}
