package duke;

import duke.commands.CommandRunner;

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
            listCount = CommandRunner.commandRunner(input, commandType, listCount);
        }
    }
}
