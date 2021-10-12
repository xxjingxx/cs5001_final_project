package problem1;

import java.util.*;

/**
 * Maintains all the option names, descriptions, and related String constants for this program.
 */
public final class OptionConst {

  public static Map<String, Option> allOptions = new HashMap<>();
  public static List<String> requiredOptions = new ArrayList<>();
  public static List<String> optionalOptions = new ArrayList<>();
  public static List<String> validOptions = new ArrayList<>();

  public static final String EMAIL = "--email";
  public static final String EMAIL_TEMPLATE = "--email-template";
  public static final String LETTER = "--letter";
  public static final String LETTER_TEMPLATE = "--letter-template";
  public static final String OUTPUT_DIR = "--output-dir";
  public static final String CSV_FILE = "--csv-file";

  public static final String EMAIL_DESCRIPTION =
          "--email Generate email messages. If this option is provided, then -- email-template must also be provided.";
  public static final String EMAIL_TEMPLATE_DESCRIPTION =
          "--email-template <path/to/file> A filename for the email template.";
  public static final String LETTER_DESCRIPTION =
          "--letter Generate letters. If this option is provided, then --letter- template must also be provided.";
  public static final String LETTER_TEMPLATE_DESCRIPTION =
          "--letter-template <path/to/file> A filename for the letter template.";
  public static final String OUTPUT_DIR_DESCRIPTION =
          "--output-dir <path/to/folder> The folder to store all generated files. This option is required.";
  public static final String CSV_FILE_DESCRIPTION =
          "--csv-file <path/to/folder> The CSV file to process. This option is required.";

  public static final String CSV = ".*\\.csv$";
  public static final String TXT = ".*\\.txt$";
  public static final String OPTION = "--.*";

  static {
    allOptions.put(EMAIL, new Option(EMAIL, EMAIL_DESCRIPTION));
    allOptions.put(EMAIL_TEMPLATE, new Option(EMAIL_TEMPLATE, EMAIL_TEMPLATE_DESCRIPTION));
    allOptions.put(LETTER, new Option(LETTER, LETTER_DESCRIPTION));
    allOptions.put(LETTER_TEMPLATE, new Option(LETTER_TEMPLATE, LETTER_TEMPLATE_DESCRIPTION));
    allOptions.put(OUTPUT_DIR, new Option(OUTPUT_DIR, OUTPUT_DIR_DESCRIPTION));
    allOptions.put(CSV_FILE, new Option(CSV_FILE, CSV_FILE_DESCRIPTION));
  }

  static {
    requiredOptions.add(CSV_FILE);
    requiredOptions.add(OUTPUT_DIR);
  }

  static {
    validOptions.add(CSV_FILE);
    validOptions.add(OUTPUT_DIR);
    validOptions.add(EMAIL);
    validOptions.add(LETTER);
    validOptions.add(EMAIL_TEMPLATE);
    validOptions.add(LETTER_TEMPLATE);
  }

  static {
    optionalOptions.add(EMAIL);
    optionalOptions.add(LETTER);
  }
}
