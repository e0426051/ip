package Duke.Tasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import Duke.Commands.Parser;
import Duke.Exceptions.InvalidCommandException;
import Duke.Exceptions.InvalidFormatException;
import Duke.Ui;

public class Storage {

    public static void updateFile(Task tasks) {
        try {
            //FileWriter dukeSave = new FileWriter("./duke.txt", true);
            FileWriter dukeSave = new FileWriter("duke.txt", true);
            BufferedWriter duke = new BufferedWriter(dukeSave);
            duke.write(tasks.toString());
            duke.newLine();
            duke.close();
        } catch (IOException e) {
            Ui.displayIOError();
        }
    }

    public static void refreshFile(ArrayList<Task> tasks) {
        try {
            //FileWriter dukeUpdate = new FileWriter("./duke.txt", false);
            FileWriter dukeUpdate = new FileWriter("duke.txt", false);
            for (Task task : tasks) {
                dukeUpdate.write(String.format("%s\n", task.toString()));
            }
            dukeUpdate.close();
        } catch (IOException e) {
            Ui.displayIOError();
        }
    }

    public static void createFile(File duke) {
        try {
            if (duke.exists()) {
                Ui.displayFilePresentMessage();
                return;
            }
            /*
            if (!duke.getParentFile().exists()) {
                boolean isDirectoryMade = duke.getParentFile().mkdirs();
                boolean isFileMade = duke.createNewFile();
                boolean isStructureMade = (isDirectoryMade && isFileMade);
                Ui.displayFileNotPresentMessage(duke.getAbsolutePath(), isStructureMade);
            }

             */
            if(!duke.exists()) {
                boolean isFileMade = duke.createNewFile();
                Ui.displayFileNotPresentMessage(duke.getAbsolutePath(), isFileMade);
            }

        } catch (IOException e) {
            Ui.displayMakeFileError(e.getMessage());
        }
    }

    public static int fileParser(ArrayList<Task> tasks, int listCount) {
        //createFile(new File("./duke.txt"));
        //Path path = Paths.get("./duke.txt");
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
                    tasks.get(tasks.size() - 1).markAsDone();
                    //tasks.get(listCount - 1).markAsDone();
                }
            }
        }
        return listCount;
    }


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
