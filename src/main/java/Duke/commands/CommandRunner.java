package duke.commands;

import duke.exceptions.InvalidCommandException;
import duke.tasks.TaskList;
import duke.ui.Ui;

public class CommandRunner {

    /**
     * Takes the result from the parser and calls the TaskList functions appropriately.
     * Does not handle addition of tasks, which is dealt by taskRunner.
     * @param input input by user.
     * @param commandType type of command by parser.
     * @param listCount 
     * @return
     */
    public static int commandRunner(String input, String commandType, int listCount) {
        switch (commandType) {
        case "BYE":
            Ui.displayByeMessage();
            break;
        case "LIST":
            TaskList.displayList(listCount, TaskList.tasks);
            break;
        case "DONE":
            TaskList.flagAsDone(input, TaskList.tasks);
            break;
        case "DELETE":
            listCount = TaskList.deleteTask(input, TaskList.tasks, listCount);
            break;
        case "FIND":
            TaskList.displayFind(listCount, input);
            break;
        default:
            return taskRunner(input, commandType, listCount);
            //Fallthrough due to return
        }
        return listCount;
    }

    public static int taskRunner(String input, String commandType, int listCount) {
        switch (commandType) {
        case "DEADLINE":
            listCount = TaskList.createDeadline(input, listCount, TaskList.tasks, false);
            break;
        case "EVENT":
            listCount = TaskList.createEvent(input, listCount, TaskList.tasks, false);
            break;
        case "TODO":
            try {
                listCount = TaskList.createToDo(input, listCount, TaskList.tasks, false);
            } catch (InvalidCommandException e) {
                Ui.displayInvalidCommand();
            }
            break;
        default:
            try {
                listCount = TaskList.createTraditionalTask(input, listCount, TaskList.tasks, false);
            } catch (InvalidCommandException e) {
                Ui.displayInvalidCommand();
            }
            break;
        }
        return listCount;
    }
}
