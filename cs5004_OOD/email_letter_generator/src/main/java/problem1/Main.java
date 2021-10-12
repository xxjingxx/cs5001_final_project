package problem1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Class Main contains main method, the entrance of this program. This program is a part of a system that would help
 * a nonprofit track donations. Specifically, this program would use templates to generate letters and/or emails to
 * be sent to the supporter.
 */
public class Main {

  private static final Integer EXTRA_CHAR = 13;

  /**
   * main method starts the program and generate the desired files based on command line arguments.
   *
   * @param args String[], command line arguments.
   * @throws IOException if I/O exceptions are encountered.
   */
  public static void main(String[] args) throws IOException {

    // parsing command line arguments and retrieve input file, output path, and template list.
    CmdLineParser cp = new CmdLineParser(args);
    String inputFile = cp.getArg(OptionConst.CSV_FILE);
    List<String> templates = cp.getTemplates();
    String outPath = cp.getArg(OptionConst.OUTPUT_DIR);

    // read input file and store user data in a Map.
    FileProcessor fp = new FileProcessor(inputFile);
    List<String> lines = fp.readFile(inputFile);
    Map<String, ArrayList<String>> users = fp.processInput(lines);

    // iterate the template list, generate output files and store them in the given output directory.
    for (int i = 0; i < templates.size(); i++) {
      FileGenerator fg = new FileGenerator();
      String ret = fg.readFile(templates.get(i));
      String templateTypeName = templates.get(i).substring(0, templates.get(i).length() - EXTRA_CHAR);
      String[][] outFiles = fg.generateOutString(ret, users, outPath, templateTypeName);
      fg.generateFile(outFiles);
    }
  }

}
