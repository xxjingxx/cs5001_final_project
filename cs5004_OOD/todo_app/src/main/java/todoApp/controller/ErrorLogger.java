package todoApp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** A class represents ErrorLogger */
public class ErrorLogger {

  private List<String> log;

  /** Constructor for ErrorLogger class */
  public ErrorLogger() {
    this.log = new ArrayList<>();
  }

  /**
   * add method for ErrorLogger class
   *
   * @param event - String
   */
  public void log(String event) {
    this.log.add(event);
  }

  public List<String> getLog() {
    return log;
  }

  /**
   * check to see if ErrorLogger is empty
   *
   * @return - boolean
   */
  public boolean isEmpty() {
    return this.log.size() == 0;
  }

  /**
   * equals method for ErrorLogger class
   *
   * @param o - Object
   * @return boolean
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ErrorLogger that = (ErrorLogger) o;
    return Objects.equals(log, that.log);
  }

  /**
   * hashCode method for ErrorLogger
   *
   * @return int
   */
  @Override
  public int hashCode() {
    return Objects.hash(log);
  }

  /**
   * toString method for ErrorLogger
   *
   * @return String
   */
  @Override
  public String toString() {
    if (this.log.isEmpty()) return "Empty log";
    String out = "";
    for (String entry : this.log) {
      out += entry + System.lineSeparator();
    }
    return out;
  }
}
