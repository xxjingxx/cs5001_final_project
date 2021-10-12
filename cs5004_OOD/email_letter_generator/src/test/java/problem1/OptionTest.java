package problem1;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class OptionTest {
  private String o1;
  private String o2;
  private String d1;
  private String d2;
  private String a1;
  private String a2;
  private Option option1;
  private Option option2;
  private Option option3;
  private Option option4;
  private Option option5;
  private Option option6;

  @Before
  public void setUp() throws Exception {
    o1 = "option1";
    o2 = "option2";
    d1 = "description1";
    d2 = "description2";
    a1 = "argument1";
    a2 = "argument2";
    option1 = new Option(o1, d1, a1);
    option2 = new Option(o1, d1, a1);
    option3 = new Option(o2, d1, a1);
    option4 = new Option(o1, d2, a1);
    option5 = new Option(o1, d1, a2);
    option6 = new Option(o1, d1);
  }

  @Test
  public void getOption() {
    String result = option1.getOption();
    assertEquals(o1, result);
  }

  @Test
  public void getArgument() {
    String result = option1.getArgument();
    assertEquals(a1, result);
  }

  @Test
  public void getDescription() {
    String result = option1.getDescription();
    assertEquals(d1, result);
  }

  @Test
  public void isRequireArg() {
    assertTrue(option1.isRequireArg());
  }

  @Test
  public void testToString() {
    String expected =
        "Option{"
            + "option='"
            + o1
            + '\''
            + ", argument='"
            + a1
            + '\''
            + ", description='"
            + d1
            + '\''
            + ", requireArg="
            + true
            + '}';
    assertEquals(expected, option1.toString());
  }

  @Test
  public void testEquals1() {
    assertTrue(option1.equals(option1));
  }

  @Test
  public void testEquals2() {
    assertTrue(option1.equals(option2));
  }

  @Test
  public void testEquals3() {
    assertFalse(option1.equals(null));
  }

  @Test
  public void testEquals4() {
    assertFalse(option1.equals(option3));
  }

  @Test
  public void testEquals5() {
    assertFalse(option1.equals(option4));
  }

  @Test
  public void testEquals6() {
    assertFalse(option1.equals(option5));
  }

  @Test
  public void testHashCode() {
    int hash = Objects.hash(o1, a1, d1, true);
//    int result = o1.hashCode();
//    result = 31 * result + (a1 != null ? a1.hashCode() : 0);
//    result = 31 * result + d1.hashCode();
//    result = 31 * result + (true ? 1 : 0);
    assertEquals(hash, option1.hashCode());
  }

//
}
