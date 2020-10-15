package duke;

import duke.exceptions.InvalidCommandException;

import duke.parser.Parser;

import duke.storage.Storage;
import duke.tasks.TaskList;
import duke.ui.Ui;

import java.util.Scanner;

public class Duke {
    /**
     * Main duke function. Backbone function that calls Ui, TaskList, Storage and Parser classes.
     * @see TaskList
     * @see Ui
     * @see Storage
     * @see Parser
     */
    public static void main(String[] args) {
        String input;
        String commandType;
        int byeIndicator = 1;
        int listCount = 0;
        final int PRESENT = 0;

        listCount = Storage.parseFile(TaskList.tasks, listCount);

        Ui.displayWelcomeMessage();

        Scanner scan = new Scanner(System.in);
        while (byeIndicator != PRESENT) {
            input = scan.nextLine();
            byeIndicator = input.compareToIgnoreCase("bye");
            commandType = Parser.parse(input);
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
        }
    }
}
