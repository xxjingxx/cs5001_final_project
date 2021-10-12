package todoApp.controller;

import todoApp.commandLineParser.CmdLineParser;
import todoApp.commandLineParser.OptionConst;
import todoApp.model.*;
import todoApp.view.DisplayHelper;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/** This class contains methods that process different commands. */
public class CommandProcessor {

  private static final Integer DEFAULT_PRIORITY = 3;

  private ErrorLogger commandErrorLog;

  /** Constructs a CommandProcessor. */
  public CommandProcessor() {
    this.commandErrorLog = new ErrorLogger();
  }

  /** @return ErrorLogger, the error logger of this command processor. */
  public ErrorLogger getCommandErrorLog() {
    return commandErrorLog;
  }

  /**
   * Build a new Task based on command line argument
   *
   * @param cp - CmbLineParser
   * @return - Task, a new task if commands ask to add a new Task, null if there is no requirement
   *     to add a new Task
   */
  public Task processNewTodo(CmdLineParser cp) {
    Map<String, String> options = cp.getOptions();
    String text;
    Integer priority = DEFAULT_PRIORITY;
    LocalDate due = null;
    String category = null;

    // add text to task object
    if (options.containsKey(OptionConst.TODO_TEXT)) {
      text = cp.getArg(OptionConst.TODO_TEXT);

      // check priority format and add it to task object
      if (options.containsKey(OptionConst.PRIORITY)) {
        try {
          priority = Integer.parseInt(cp.getArg(OptionConst.PRIORITY));
        } catch (NumberFormatException nfe) {
          this.commandErrorLog.log("Priority must be an integer.");
        }
      }

      // check due date format and add it to task object
      if (options.containsKey(OptionConst.DUE)) {
        String dueString = cp.getArg(OptionConst.DUE);
        try {
          DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
          due = LocalDate.parse(dueString, dtf);
        } catch (DateTimeException dte) {
          this.commandErrorLog.log("Please provide due date in format: YYYY-MM-DD.");
        }
      }

      // add category to task object
      if (options.containsKey(OptionConst.CATEGORY)) {
        category = cp.getArg(OptionConst.CATEGORY);
      }
      Task newTask = new Task.Builder(text).priority(priority).category(category).due(due).build();
      return newTask;
    } else {
      return null;
    }
  }

  /**
   * Completes a task that user specifies.
   *
   * @param cp - CmdLineParser
   * @param td - TaskData
   */
  public void processCompleteTodo(CmdLineParser cp, TaskData td) {
    if (cp.getOptions().containsKey(OptionConst.COMPLETE_TODO)) {
      String todoID = cp.getArg(OptionConst.COMPLETE_TODO);
      Integer ID;
      try {
        ID = Integer.parseInt(todoID);
      } catch (NumberFormatException nfe) {
        this.commandErrorLog.log("ID must be an integer.");
        return;
      }

      try {
        td.completeATask(ID);
      } catch (IllegalArgumentException iae) {
        this.commandErrorLog.log("Invalid ID number.");
        return;
      }
    }
  }

  /**
   * Select incomplete tasks.
   *
   * @param td - TaskData
   * @return - List, a list of task strings containing only incomplete tasks.
   */
  public static List<List<String>> selectIncomplete(TaskData td) {
    List<Task> tasks = td.getTodos();
    List<Task> filteredTasks = TaskFilter.incomplete(tasks);

    List<List<String>> filteredResult = new ArrayList<>();

    for (Task t : filteredTasks) {
      List<String> l = t.toStringList();
      filteredResult.add(l);
    }
    return filteredResult;
  }

  /**
   * Select tasks under a specified category.
   *
   * @param td - TaskData
   * @param category - String, the category specified by user
   * @return - List, a list of task strings under certain category.
   */
  public static List<List<String>> selectCategory(TaskData td, String category) {
    List<Task> filteredTasks = TaskFilter.category(td.getTodos(), category);

    List<List<String>> filteredResult = new ArrayList<>();

    for (Task t : filteredTasks) {
      List<String> l = t.toStringList();
      filteredResult.add(l);
    }
    return filteredResult;
  }

  /**
   * Sort tasks by due date
   *
   * @param td - TaskData
   * @return - List, a list of task strings sorted by due date.
   */
  public static List<List<String>> selectSortByDate(TaskData td) {
    List<Task> sortedList = new ArrayList<>(td.getTodos());
    Collections.sort(sortedList, new DueComparator<Task>());
    List<List<String>> sortedResult = new ArrayList<>();

    for (Task t : sortedList) {
      List<String> l = t.toStringList();
      sortedResult.add(l);
    }
    return sortedResult;
  }

  /**
   * Sort tasks by priority.
   *
   * @param td - TaskData
   * @return - List, a list of task strings sorted by priority.
   */
  public static List<List<String>> selectSortByPriority(TaskData td) {
    List<Task> sortedList = new ArrayList<>(td.getTodos());
    Collections.sort(sortedList, new PriorityComparator<>());
    List<List<String>> sortedResult = new ArrayList<>();

    for (Task t : sortedList) {
      List<String> l = t.toStringList();
      sortedResult.add(l);
    }
    return sortedResult;
  }

  /**
   * Display per user's request
   *
   * @param cp - CmdLineParser
   * @param fp - FileProcessor
   */
  public static void processDisplay(CmdLineParser cp, FileProcessor fp) {
    TaskData current = fp.getTaskData();
    if (cp.getOptions().containsKey(OptionConst.DISPLAY)) {

      if (!cp.getOptions().containsKey(OptionConst.SHOW_INCOMPLETE)
          && !cp.getOptions().containsKey(OptionConst.SHOW_CATEGORY)
          && !cp.getOptions().containsKey(OptionConst.SORT_BY_DATE)
          && !cp.getOptions().containsKey(OptionConst.SORT_BY_PRIORITY)) {
        System.out.println("Displaying All Todos...");
        System.out.println(DisplayHelper.createTable(fp.getHeaders(), current.getStringData()));
      }

      String output;
      if (cp.getOptions().containsKey(OptionConst.SHOW_INCOMPLETE)) {
        System.out.println("Displaying incomplete todos...");
        output =
            DisplayHelper.createTable(fp.getHeaders(), CommandProcessor.selectIncomplete(current));
        System.out.println(output);
      }

      if (cp.getOptions().containsKey(OptionConst.SHOW_CATEGORY)) {
        System.out.println(
            "Displaying todos in category " + "\"" + cp.getArg(OptionConst.SHOW_CATEGORY) + "\"");
        output =
            DisplayHelper.createTable(
                fp.getHeaders(),
                CommandProcessor.selectCategory(current, cp.getArg(OptionConst.SHOW_CATEGORY)));
        System.out.println(output);
      }

      // if --sort-by-date and --sort-by-priority both exists, program chooses to execute only
      // --sort-by-date and remind user.
      if (cp.getOptions().containsKey(OptionConst.SORT_BY_DATE)) {
        if (cp.getOptions().containsKey(OptionConst.SORT_BY_PRIORITY)) {
          System.out.println("--sort-by-date cannot be combined with --sort-by-priority.");
        }
        System.out.println("Displaying todos sorted by due date...");
        output =
            DisplayHelper.createTable(fp.getHeaders(), CommandProcessor.selectSortByDate(current));
        System.out.println(output);
      } else {
        if (cp.getOptions().containsKey(OptionConst.SORT_BY_PRIORITY)) {
          System.out.println("Displaying todos sorted by priority...");
          output =
              DisplayHelper.createTable(
                  fp.getHeaders(), CommandProcessor.selectSortByPriority(current));
          System.out.println(output);
        }
      }
    }
  }
}
