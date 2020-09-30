package duke.tasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Scanner;

import duke.commands.Parser;

import duke.exceptions.InvalidCommandException;
import duke.exceptions.InvalidFormatException;

import duke.Ui;

public class Storage {

    /**
     * This function updates the file in terms of adding new lines to an existing file.
     * This function is used for new tasks.
     * @param tasks the arraylist of tasks.
     */
    public static void updateFile(Task tasks) {
        try {
            FileWriter dukeSave = new FileWriter("duke.txt", true);
            BufferedWriter duke = new BufferedWriter(dukeSave);
            duke.write(tasks.toString());
            duke.newLine();
            duke.close();
        } catch (IOException e) {
            Ui.displayIoError();
        }
    }

    /**
     * This function updates the file in terms of modifying existing lines in the file.
     * This function is used while setting a task as done, or while deleting a task.
     * @param tasks the arraylist of tasks.
     */
    public static void refreshFile(ArrayList<Task> tasks) {
        try {
            FileWriter dukeUpdate = new FileWriter("duke.txt", false);
            for (Task task : tasks) {
                dukeUpdate.write(String.format("%s\n", task.toString()));
            }
            dukeUpdate.close();
        } catch (IOException e) {
            Ui.displayIoError();
        }
    }

    /**
     * This function scans for duke.txt. If the function detects the file, it prints out a message
     * and does nothing. Otherwise, duke.txt is created and the directory is shown to the user.
     * @param duke the file object.
     */
    public static void createFile(File duke) {
        try {
            if (duke.exists()) {
                Ui.displayFilePresentMessage();
                return;
            }

            if (!duke.exists()) {
                boolean isFileMade = duke.createNewFile();
                Ui.displayFileNotPresentMessage(duke.getAbsolutePath(), isFileMade);
            }
        } catch (IOException e) {
            Ui.displayMakeFileError(e.getMessage());
        }
    }

    /**
     * This function takes duke.txt and transform each line into a valid command,
     * so that the commands can be added during startup of the program.
     * @param tasks the arraylist of tasks.
     * @param listCount the variable tracking the number of tasks in the arraylist.
     * @return listCount
     */
    public static int parseFile(ArrayList<Task> tasks, int listCount) {
        createFile(new File("duke.txt"));
        Path path = Paths.get("duke.txt");
        Scanner loadFile = null;

        try {
            loadFile = new Scanner(path);
        } catch (IOException e) {
            Ui.displayFileNotFoundError();
        }

        assert loadFile != null;
        loadFile.useDelimiter("\n");

        while (loadFile.hasNext()) {
            String position = loadFile.next();
            if (!position.isEmpty()) {
                char isDone = position.charAt(4);
                char tradIsDone = position.charAt(1);
                String taskType = getTaskType(position.charAt(1));
                String inputFormat = taskType + reformatDate(position.split(" ", 2)[1],
                        position.charAt(1));
                listCount = loadFileAtStartup(inputFormat, listCount, tasks);

                if (isDone == '\u2713' || tradIsDone == '\u2713') {
                    tasks.get(tasks.size() - 1).setAsDone();
                }
            }
        }
        return listCount;
    }

    /**
     * This function reformats the syntax of dates in deadlines and events while loading
     * duke.txt into the programs.
     * @param input the line of data in duke.txt.
     * @param taskType the type of task. Ignores traditional tasks and todo which has no dates.
     * @return input.trim()
     */
    public static String reformatDate(String input, char taskType) {

        switch (taskType) {
            case 'D':
                return input.trim().replace("(by:", "/by")
                        .replace(")", "");
            //Fallthrough due to return
            case 'E':
                return input.trim().replace("(on:", "/on")
                        .replace(")", "");
            //Fallthrough due to return
            default:
                return input.trim();
        }
    }

    /**
     * Returns the type of task for fileParser in a valid command format.
     * @param input the character in the saved file denoting the type of task.
     * @return type of task.
     */
    public static String getTaskType(char input) {

        switch (input) {
            case 'T':
                return "todo ";
            //Fallthrough due to return
            case 'D':
                return "deadline ";
            //Fallthrough due to return
            case 'E':
                return "event ";
            //Fallthrough due to return
            default:
                //Returns nothing for traditional tasks. At this position, traditional tasks have
                //ticks or crosses.
                return "";
        }
    }

    /**
     * Loads duke.txt at startup by fileParser after fileParser reformats the data
     * in duke.txt into valid commands.
     * @param input the augmented data in valid format.
     * @param listCount the number of tasks in the list.
     * @param tasks the arraylist of tasks.
     * @return listCount
     */
    public static int loadFileAtStartup(String input, int listCount, ArrayList<Task> tasks) {
        String commandType = Parser.parse(input);

        switch (commandType) {
            case "DEADLINE":
                try {
                    listCount = TaskList.createDeadline(input, listCount, tasks, true);
                } catch (InvalidFormatException e) {
                    Ui.displayInvalidFormat();
                } catch (InvalidCommandException e) {
                    Ui.displayInvalidCommand();
                }
                break;
            case "EVENT":
                try {
                    listCount = TaskList.createEvent(input, listCount, tasks, true);
                } catch (InvalidFormatException e) {
                    Ui.displayInvalidFormat();
                } catch (InvalidCommandException e) {
                    Ui.displayInvalidCommand();
                }
                break;
            case "TODO":
                try {
                    listCount = TaskList.createToDo(input, listCount, tasks, true);
                } catch (InvalidCommandException e) {
                    Ui.displayInvalidCommand();
                }
                break;
            default:
                try {
                    listCount = TaskList.createTraditionalTask(input, listCount, tasks, true);
                } catch (InvalidCommandException e) {
                    Ui.displayInvalidCommand();
                }
                break;
        }
        return listCount;
    }
}
