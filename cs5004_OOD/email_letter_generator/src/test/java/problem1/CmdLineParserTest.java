package problem1;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class CmdLineParserTest {

  private CmdLineParser cp1;
  private String[] invalidArgs1;
  private String[] invalidArgs2;
  private String[] invalidArgs3;
  private String[] invalidArgs4;
  private String[] invalidArgs5;
  private String[] validArgs1;
  private String[] validArgs2;

  private String email = OptionConst.EMAIL;
  private String emailTemplate = OptionConst.EMAIL_TEMPLATE;
  private String outDir = OptionConst.OUTPUT_DIR;
  private String csv = OptionConst.CSV_FILE;
  private String letter = OptionConst.LETTER;
  private String letterTemplate = OptionConst.LETTER_TEMPLATE;
  private String csvName = "nonprofit-supporters.csv";
  private String emailtxt = "email-template.txt";
  private String lettertxt = "letter-template.txt";
  private String invalidEmailOption = "--emai";
  private String out = "out";
  private String invalidOutPutDirectory = "ou";

  @Before
  public void setUp() throws Exception {
    invalidArgs1 = new String[] {email, emailTemplate, emailtxt, csv, csvName};
    invalidArgs2 = new String[] {invalidEmailOption, emailTemplate, emailtxt, csv, csvName, letter, letterTemplate, lettertxt, outDir, out};
    invalidArgs3 = new String[] {email, csv, csvName, letter, letterTemplate, lettertxt, outDir, out};
    invalidArgs4 = new String[] {csv, csvName, letter, letterTemplate, outDir, out};
    invalidArgs5 = new String[] {csv, csvName, letter, letterTemplate, lettertxt, outDir, invalidOutPutDirectory};
    validArgs1 = new String[] {csv, csvName, letter, letterTemplate, lettertxt, outDir, out};
    validArgs2 = new String[] {email, emailTemplate, emailtxt, csv, csvName, letter, letterTemplate, lettertxt, outDir, out};
  }

  @Test(expected = CmdLineExceptions.MissingOptionException.class)
  public void testConstructorMissingRequiredOption() {
    cp1 = new CmdLineParser(invalidArgs1);
  }

  @Test(expected = CmdLineExceptions.InvalidOptionException.class)
  public void testConstructorInvalidOption() {
    cp1 = new CmdLineParser(invalidArgs2);
  }

  @Test(expected = CmdLineExceptions.MissingOptionException.class)
  public void testConstructorNoEmailTemplate() {
    cp1 = new CmdLineParser(invalidArgs3);
  }

  @Test(expected = CmdLineExceptions.IllegalPathTypeException.class)
  public void testConstructorNoLetterTxt() {
    cp1 = new CmdLineParser(invalidArgs4);
  }

  @Test(expected = CmdLineExceptions.IllegalPathTypeException.class)
  public void testConstructorInvalidDirectory() {
    cp1 = new CmdLineParser(invalidArgs5);
  }

  @Test
  public void getArg() {
    cp1 = new CmdLineParser(validArgs1);
    String outPath = cp1.getArg(outDir);
    assertEquals(out, outPath);
  }

  @Test
  public void getTemplates() {
    List<String> expected = new ArrayList<>();
    expected.add(emailtxt);
    expected.add(lettertxt);
    cp1 = new CmdLineParser(validArgs2);
    List<String> result = cp1.getTemplates();
    assertTrue(expected.containsAll(result) && result.containsAll(expected));
  }

  @Test
  public void getTemplate2() {
    List<String> expected = new ArrayList<>();
    expected.add(lettertxt);
    cp1 = new CmdLineParser(validArgs1);
    List<String> result = cp1.getTemplates();
    assertEquals(expected, result);
  }

  @Test(expected = CmdLineExceptions.NullArgumentException.class)
  public void getArgFail() {
    cp1 = new CmdLineParser(validArgs1);
    cp1.getArg(email);
  }

}