package todoApp.view;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DisplayHelperTest {
  private List<String> header;
  private List<List<String>> values;
  private DisplayHelper displayHelper;
  private List<String> row1;
  private List<String> row2;

  private List<String> header2;


  @Before
  public void setUp() throws Exception {
    List<String> header = Arrays.asList(new String[]{"id", "text", "completed", "due",
        "priority", "category"});
    List<String> row1 = Arrays.asList(new String[]{"1","Finish HW9","true","?","1","school"});
    List<String> row2 = Arrays.asList(new String[]{"2","Mail passport","true","?","3","?"});
    values = new ArrayList<>();
    values.add(header);
    values.add(row1);
    values.add(row2);
  }

  @Test
  public void createTable() {
    String table = DisplayHelper.createTable(header, values);
    assertTrue(table.contains("id"));
  }

  @Test
  public void createTable2() {
    List<List<String>> values2 = new ArrayList<>();
    header2 = new ArrayList<>();
    String table2 = DisplayHelper.createTable(header2, values2);
    assertTrue(table2.contains("-"));
  }

  @Test
  public void createLine() {
    StringBuilder line = new StringBuilder("");
    for(int i = 0; i <= 4; i++) {
      line = line.append("-");
    }
    line.append("\n");
    assertEquals(line.toString(), DisplayHelper.createLine("-", 5));

  }
}