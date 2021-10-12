package todoApp.commandLineParser;

import java.util.Objects;

/** Class to represent a single option. */
public class Option {

  private String option;
  private String argument;
  private String description;
  private boolean requireArg;

  /**
   * Constructs an Option without argument.
   *
   * @param option - String, option name.
   * @param description - String, description of the option.
   */
  public Option(String option, String description) {
    this.option = option;
    this.description = description;
  }

  /**
   * Constructs an Option with argument.
   *
   * @param option - String, option name.
   * @param description - String, option description.
   * @param argument - String, option argument.
   */
  public Option(String option, String description, String argument) {
    this.option = option;
    this.description = description;
    this.requireArg = true;
    this.argument = argument;
  }

  /** @return - String, giving option name. */
  public String getOption() {
    return option;
  }

  /** @return - String, giving argument. */
  public String getArgument() {
    return argument;
  }

  /** @return String, giving description. */
  public String getDescription() {
    return description;
  }

  /** @return - boolean, true is the option requires argument, false if not. */
  public boolean isRequireArg() {
    return requireArg;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return "Option{"
        + "option='"
        + option
        + '\''
        + ", argument='"
        + argument
        + '\''
        + ", description='"
        + description
        + '\''
        + ", requireArg="
        + requireArg
        + '}';
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Option option1 = (Option) o;
    return requireArg == option1.requireArg
        && option.equals(option1.option)
        && Objects.equals(argument, option1.argument)
        && description.equals(option1.description);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(option, argument, description, requireArg);
  }
}
