package Duke.Exceptions;

/**
 * Exception that is thrown when the user enters an invalid format for otherwise valid commands.
 * For example, the user enters "delete t" where t is not a positive integer that is not larger
 * than the number of tasks in the list. Another example would be the user entering "event holiday"
 * without specifying the time/date (on:).
 */
public class InvalidFormatException extends Exception {
}