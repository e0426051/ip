package Duke.Commands;

import Duke.Duke;
import Duke.Exceptions.InvalidCommandException;
import Duke.Exceptions.InvalidFormatException;
import Duke.Ui;

public class Parser {
    private static int byeIndicator = 1;
    private static int listIndicator;
    private static boolean isDone;
    private static boolean isEvent;
    private static boolean isDeadline;
    private static boolean isToDo;
    private static boolean isDelete;
    private static boolean isFind;
    final static int PRESENT = 0;

    public static String parse (String input) {
        byeIndicator = input.compareToIgnoreCase("bye");
        listIndicator = input.compareToIgnoreCase("list");
        isDone = input.startsWith("done ");
        isDeadline = input.startsWith("deadline ");
        isEvent = input.startsWith("event ");
        isToDo = input.startsWith("todo ");
        isDelete = input.startsWith("delete ");
        isFind = input.startsWith("find ");

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
        } else {
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
}
