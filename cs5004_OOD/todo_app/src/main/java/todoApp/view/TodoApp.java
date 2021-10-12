package todoApp.view;

import todoApp.commandLineParser.CmdLineParser;
import todoApp.commandLineParser.OptionConst;
import todoApp.commandLineParser.ParserDisplayHelper;
import todoApp.controller.FileProcessor;
import todoApp.model.*;
import todoApp.controller.*;

import java.io.IOException;
import java.util.*;

/** This class contains the entrance of this program. */
public class TodoApp {

  public static void main(String[] args) throws IOException {
    System.out.println(
        "----------------------TodoApp v1.0------------------------\n"
            + "----------------------Welcome-----------------------------\n"
            + "Please enter commands, type \"quit\" to quit program."
            + "\nThe following are valid commands");
    String usage = ParserDisplayHelper.createUsage(OptionConst.allOptions);
    System.out.println(usage);
    Scanner console = new Scanner(System.in);
    String input = "";

    while (true) {
      System.out.println("Enter your commands: ");
      input = console.nextLine();
      if (input.equals("quit")) break;

      InputProcessor ip = new InputProcessor();
      String[] arguments = ip.processInput(input);

      CmdLineParser cp = new CmdLineParser(arguments);
      System.out.println("------The options user entered: " + cp.getOptions());
      String csvPath = cp.getArg(OptionConst.CSV_FILE);

      FileProcessor fp = new FileProcessor(csvPath);

      if (!fp.getLog().isEmpty()) {
        System.out.println(fp.getLog().toString());
        continue;
      }
      TaskData current = fp.getTaskData();

      CommandProcessor cmdProcessor = new CommandProcessor();

      // add a new task
      Task newTask = cmdProcessor.processNewTodo(cp);
      if (!cmdProcessor.getCommandErrorLog().isEmpty()) {
        System.out.println(cmdProcessor.getCommandErrorLog());
        continue;
      }

      if (newTask != null) {
        try {
          current.addTodo(newTask);
        } catch (IllegalArgumentException iae) {
          System.out.println("Task already exists.");
          continue;
        }

        System.out.println("Adding new todo...\n");
      }

      // complete a task
      cmdProcessor.processCompleteTodo(cp, current);
      if (!cmdProcessor.getCommandErrorLog().isEmpty()) {
        System.out.println(cmdProcessor.getCommandErrorLog().toString());
      }

      // display todos
      CommandProcessor.processDisplay(cp, fp);

      fp.writeFile(current.getStringData(), "todos.csv");

      if (!fp.getLog().isEmpty()) {
        System.out.println(fp.getLog().toString());
        continue;
      }
    }
  }
}
