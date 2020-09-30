package duke.exceptions;

/**
 * Exception that is thrown when the user enters an invalid command.
 * Invalid commands are commands that are otherwise valid without any parameters entered,
 * when said command expects parameters.
 * Examples of an invalid command: "done ", "event ", "delete ".
 */
public class InvalidCommandException extends Exception {
}