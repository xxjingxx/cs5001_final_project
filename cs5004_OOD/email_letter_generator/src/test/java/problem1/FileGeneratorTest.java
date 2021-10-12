package problem1;

import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileGeneratorTest {

  public static final String outPathTest = "outTest";

  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();
  String path1;
  String path2;
  FileGenerator fg;
  FileGenerator fg2;
  FileProcessor fp;
  String ret ;
  String templateTypeName1;
  Map<String, ArrayList<String>> users1;
  List<String> lines;
  ErrorLogger log1;


  @Before
  public void setUp() throws Exception {
    path1 =  "test1.csv";
    path2 = "sample.csv";

    fg = new FileGenerator();
    fp = new FileProcessor(path2);
    ret =  fg.readFile(path1);
    templateTypeName1 = "a.txt";

    List<String> lines = fp.readFile(path2);

    log1 = new ErrorLogger();
  }

  @Test
  public void readFile() throws IOException {
    String expectedString = "\"first_name\"\n";
    assertEquals(expectedString, ret);
  }


  @Test
  public void generateOutString() throws IOException {
    String path2 = "sample.csv";
    List<String> lines = fp.readFile(path2);
    Map<String, ArrayList<String>> users1 = fp.processInput(lines);
    String[] template1 = new String[] {"email-template.txt","letter-template.txt"};

    String[][] outFiles = fg.generateOutString(ret, users1,
        "outTest", templateTypeName1);
    String[][] outFilesExcepted = fg.generateOutString(ret, users1,
        "outTestExcepted", templateTypeName1);

    assertNotEquals(outFiles, outFilesExcepted);
  }

  @Test
  public void generateFile() throws IOException {
    String path2 = "sample.csv";
    List<String> lines = fp.readFile(path2);
    Map<String, ArrayList<String>> users1 = fp.processInput(lines);
    String[] template1 = new String[] {"email-template.txt","letter-template.txt"};

    String[][] outFiles = fg.generateOutString(ret, users1,
        "outTest", templateTypeName1);

    String[][] outFilesExcepted = fg.generateOutString(ret, users1,
        "outTestExcepted", templateTypeName1);

    fg.generateFile(outFiles);
    Path testPath = Paths.get("outTest/Butt-email.txt");
    assertTrue(Files.isReadable(testPath));
  }


  @Test
  public void testEqualsReflexivity() {
      assertTrue(fg.equals(fg));
  }


  @Test
  public void testEqualsSymmetry() {
    fg2 = new FileGenerator();
    assertTrue(fg.equals(fg2));
  }


  @Test
  public void testEqualsNull() {
    assertFalse(fg.equals(null));
  }


  @Test
  public void testHashCode() {
  assertFalse(0 == fg.hashCode());
  }

  @Test
  public void testHashCodeConsistency() {
    int testHashCode = fg.hashCode();
    assertEquals(testHashCode, fg.hashCode());
  }


  @Test
  public void testToString() {
    String excepted = "FileGenerator{" +
        "log=" + log1 +
        '}';
    assertEquals(excepted, fg.toString());
  }

}