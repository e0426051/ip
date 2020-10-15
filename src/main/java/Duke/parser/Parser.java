package duke.parser;

/**
 * Takes the input by user and tells the main function what the command is. Uses the CommandType
 * enum to reduce chances of errors.
 */
public class Parser {

    final static int PRESENT = 0;

    /**
     * Parser to read user input. Tasks such as events and todos are processed
     * by another parser parseTasks() to keep the functions short.
     * @param input user input.
     * @return type of task entered by user.
     */
    public static String parse (String input) {
        int byeIndicator = input.compareToIgnoreCase("bye");
        int listIndicator = input.compareToIgnoreCase("list");
        boolean isDone = input.startsWith("done ");
        boolean isDelete = input.startsWith("delete ");
        boolean isFind = input.startsWith("find ");

        if (byeIndicator == PRESENT) {
            return CommandType.BYE.toString();
        } else if (listIndicator == PRESENT) {
            return CommandType.LIST.toString();
        } else if (isDone) {
            return CommandType.DONE.toString();
        } else if (isDelete) {
            return CommandType.DELETE.toString();
        } else if (isFind) {
            return CommandType.FIND.toString();
        } else return parseTasks (input);
    }

    public static String parseTasks (String input) {
        boolean isDeadline = input.startsWith("deadline ");
        boolean isEvent = input.startsWith("event ");
        boolean isToDo = input.startsWith("todo ");

        if (isDeadline) {
            return CommandType.DEADLINE.toString();
        } else if (isEvent) {
            return CommandType.EVENT.toString();
        } else if (isToDo) {
            return CommandType.TODO.toString();
        } else {
            return CommandType.TRADITIONAL_TASK.toString();
        }
    }
}
