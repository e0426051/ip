package Duke;

import Duke.Exceptions.InvalidCommandException;
import Duke.Exceptions.InvalidFormatException;
import Duke.Commands.Parser;
import Duke.Tasks.Storage;
import Duke.Tasks.TaskList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {

    //private Ui ui;
    //public static final Ui ui = new Ui();
    //private Parser parser;

    public static void main(String[] args) {
        String input;
        String commandType;
        int byeIndicator = 1;
        int listIndicator;
        //Replaceable with size() function. Won't be replaced.
        int listCount = 0;
        //ui = new Ui(listCount);
        //parser = new Parser();
        final int PRESENT = 0;

        //ArrayList<Task> tasks = new ArrayList<>();

        listCount = Storage.fileParser(TaskList.tasks, listCount);

        Ui.displayWelcomeMessage();
        Scanner scan = new Scanner(System.in);
        while(byeIndicator != PRESENT) {
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
            case "DEADLINE":
                try {
                    listCount = TaskList.createDeadline(input, listCount, TaskList.tasks, false);
                } catch (InvalidFormatException e) {
                    Ui.displayInvalidFormat();
                } catch (InvalidCommandException e) {
                    Ui.displayInvalidCommand();
                }
                break;
            case "EVENT":
                try {
                    listCount = TaskList.createEvent(input, listCount, TaskList.tasks, false);
                } catch (InvalidFormatException e) {
                    Ui.displayInvalidFormat();
                } catch (InvalidCommandException e) {
                    Ui.displayInvalidCommand();
                }
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

/*
    public static int deleteTask(String input, ArrayList<Task> tasks, int listCount) {
        final int DELETE_OFFSET = 7;
        final int ARRAY_OFFSET = 1;
        int lastNrPosition = input.length();
        String sub = input.substring(DELETE_OFFSET, lastNrPosition);
        int i = 0;
        String status;
        String temp;

        try {
            i = Integer.parseInt(sub) - ARRAY_OFFSET;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid task number. No items are deleted.");
        } catch (NumberFormatException e) {
            Ui.displayNotNumberErrorMessage();
        }
        try {
            String taskType = tasks.get(i).getTaskType();
            status = tasks.get(i).getStatusIcon();
            Ui.displayRemoveMessage();
            switch (taskType) {
            //Traditional tasks are tasks specified in Level-2
            case "TRADITIONAL_TASK":
                Ui.displayTraditionalTask(status, tasks.get(i).getDescription());
                break;
            case "DEADLINE":
                temp = tasks.get(i).getTime();
                Ui.displayDeadline(status, tasks.get(i).getDescription(), temp);
                break;
            case "EVENT":
                temp = tasks.get(i).getTime();
                Ui.displayEvent(status, tasks.get(i).getDescription(), temp);
                break;
            case "TODO":
                Ui.displayToDo(status, tasks.get(i).getDescription());
                break;
            }
            tasks.remove(i);
            listCount--;
            refreshFile(tasks);
            return listCount;
        } catch (IndexOutOfBoundsException e) {
            Ui.displayDeleteIndexOutOfBoundsError();
        }
        return listCount;
    }

    public static void flagAsDone(String input, ArrayList<Task> tasks) {
        final int IS_DONE_OFFSET = 5;
        final int ARRAY_OFFSET = 1;
        int i = 0;
        int lastNrPosition = input.length();
        String sub = input.substring(IS_DONE_OFFSET, lastNrPosition);
        String temp;

        try {
            i = Integer.parseInt(sub) - ARRAY_OFFSET;
            boolean alreadyDone = tasks.get(i).getStatus();
            if (alreadyDone) {
                Ui.displayTaskAlreadyDoneMessage();
                return;
            }
        } catch (IndexOutOfBoundsException e) {
            //Does not show message since the function will continue to run to the bottom
        } catch (NumberFormatException e) {
            Ui.displayNotNumberErrorMessage();
            return;
        }
        try {
            Ui.displayTaskDoneMessage();
            String taskType = tasks.get(i).getTaskType();
            switch (taskType) {
            case "TRADITIONAL_TASK":
                Ui.displayTraditionalTask("\u2713", tasks.get(i).getDescription());
                break;
            case "DEADLINE":
                temp = tasks.get(i).getTime();
                Ui.displayDeadline("\u2713", tasks.get(i).getDescription(), temp);
                break;
            case "EVENT":
                temp = tasks.get(i).getTime();
                Ui.displayEvent("\u2713", tasks.get(i).getDescription(), temp);
                break;
            case "TODO":
                Ui.displayToDo("\u2713", tasks.get(i).getDescription());
                break;
            }
            tasks.get(i).markAsDone();
            refreshFile(tasks);
        } catch (IndexOutOfBoundsException e) {
            Ui.taskErrorMessage();
        }
    }

    public static void displayList(int listCount, ArrayList<Task> tasks) {
        int i;
        Ui.displayListMessage();
        for (i = 0; i < listCount; i++) {
            if (tasks.get(i).getDescription() != null) {
                Ui.displayList(i, tasks.get(i).toString());
            }
        }
    }

    public static int createTraditionalTask(String input, int listCount, ArrayList<Task> tasks, boolean initialize)
            throws InvalidCommandException {
        //Accepts "todo", "deadline" and "event" without spaces as traditional tasks.
        int checkValid = input.compareTo("");
        if (checkValid == 0) {
            throw new InvalidCommandException();
        }
        tasks.add(listCount, new Task(input));

        Ui.displayTraditionalAddMessage(input);
        if (!initialize) {
            updateFile(tasks.get(listCount));
        }
        listCount++;
        return listCount;
    }

    public static int createToDo(String input, int listCount, ArrayList<Task> tasks, boolean initialize)
            throws InvalidCommandException {
        final int TO_DO_OFFSET = 5;
        int checkValid = input.compareTo("todo ");
        if (checkValid == 0) {
            throw new InvalidCommandException();
        }
        String inputTaskDescription;
        inputTaskDescription = input.substring(TO_DO_OFFSET);
        tasks.add(listCount, new Todo(inputTaskDescription));
        if (!initialize) {
            updateFile(tasks.get(listCount));
        }
        return listInput(listCount, tasks.get(listCount));
    }

    public static int createEvent(String input, int listCount, ArrayList<Task> tasks, boolean initialize)
            throws InvalidFormatException, InvalidCommandException {
        final int INVALID = 0;
        int checkValid = input.compareTo("event ");
        if (checkValid == INVALID) {
            throw new InvalidCommandException();
        }
        final int EVENT_OFFSET = 6;
        final int BY_ON_OFFSET = 3;
        final int SLASH_ON_SPACE_OFFSET = 4;
        final int SLASH_NOT_FOUND = -1;
        final String INVALID_INPUT = "INV";
        String on;
        String inputTaskDescription;
        int i;
        i = input.indexOf("/");
        String checkMinInputFormat;
        if (i != SLASH_NOT_FOUND) {
            checkMinInputFormat = input.substring(i);
        } else {
            checkMinInputFormat = INVALID_INPUT;
        }
        boolean isValidFormat;
        if (checkMinInputFormat.length() >= SLASH_ON_SPACE_OFFSET) {
            String checkFormat = input.substring(i, i + BY_ON_OFFSET + 1);
            isValidFormat = checkFormat.equalsIgnoreCase("/on ");
        } else {
            isValidFormat = false;
        }
        if (i == EVENT_OFFSET) {
            throw new InvalidFormatException();
        } else if (!isValidFormat) {
            throw new InvalidFormatException();
        } else {
            String checkDate = input.substring(i + BY_ON_OFFSET + 1);
            boolean isEmpty = checkDate.isEmpty();
            if (isEmpty) {
                throw new InvalidFormatException();
            }
        }
        inputTaskDescription = input.substring(EVENT_OFFSET, i);
        on = input.substring(i + BY_ON_OFFSET);
        tasks.add(listCount, new Event(inputTaskDescription, on));
        if (!initialize) {
            updateFile(tasks.get(listCount));
        }
        return listInput(listCount, tasks.get(listCount));
    }

    public static int createDeadline(String input, int listCount, ArrayList<Task> tasks, boolean initialize)
            throws InvalidFormatException, InvalidCommandException {
        final int INVALID = 0;
        int checkValid = input.compareTo("deadline ");
        if (checkValid == INVALID) {
            throw new InvalidCommandException();
        }
        final int BY_ON_OFFSET = 3;
        final int DEADLINE_OFFSET = 9;
        final int SLASH_BY_SPACE_OFFSET = 4;
        final int SLASH_NOT_FOUND = -1;
        final String INVALID_INPUT = "INV";
        String by;
        String inputTaskDescription;
        int i;
        i = input.indexOf("/");
        String checkMinInputFormat;
        if (i != SLASH_NOT_FOUND) {
            checkMinInputFormat = input.substring(i);
        } else {
            checkMinInputFormat = INVALID_INPUT;
        }
        boolean isValidFormat;
        if (checkMinInputFormat.length() >= SLASH_BY_SPACE_OFFSET) {
            String checkFormat = input.substring(i, i + BY_ON_OFFSET + 1);
            isValidFormat = checkFormat.equalsIgnoreCase("/by ");
        } else {
            isValidFormat = false;
        }
        if (i == DEADLINE_OFFSET) {
            throw new InvalidFormatException();
        } else if (!isValidFormat) {
            throw new InvalidFormatException();
        } else {
            String checkDate = input.substring(i + BY_ON_OFFSET + 1);
            boolean isEmpty = checkDate.isEmpty();
            if (isEmpty) {
                throw new InvalidFormatException();
            }
        }
        inputTaskDescription = input.substring(DEADLINE_OFFSET, i);
        by = input.substring(i + BY_ON_OFFSET);
        tasks.add(listCount, new Deadline(inputTaskDescription, by));
        if (!initialize) {
            updateFile(tasks.get(listCount));
        }
        return listInput(listCount, tasks.get(listCount));
    }

    public static int listInput(int listCount, Task task) {
        Ui.displayAddMessage();
        Ui.displayTask(task.toString());
        listCount++;
        Ui.displayNumberMessage(listCount);
        return listCount;
    }


public static void updateFile(Task tasks) {
    try {
        FileWriter dukeSave = new FileWriter("./duke.txt", true);
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
            FileWriter dukeUpdate = new FileWriter("./duke.txt", false);
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
                //System.out.println("duke.txt exists! Loading file contents...");
                Ui.displayFilePresentMessage();
                return;
            }
            if (!duke.getParentFile().exists()) {
                boolean isFileMade = duke.getParentFile().mkdirs();
                Ui.displayFileNotPresentMessage(duke.getAbsolutePath(), isFileMade);
            }
            duke.createNewFile();
        } catch (IOException e) {
            Ui.displayMakeFileError(e.getMessage());
        }
    }

    public static int fileParser(ArrayList<Task> tasks, int listCount) {
        createFile(new File("./duke.txt"));
        Path path = Paths.get("./duke.txt");

        Scanner loadFile = null;
        try {
            loadFile = new Scanner(path);
        } catch (IOException e) {
            Ui.displayFileNotFoundError();
        }
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
                listCount = createDeadline(input, listCount, tasks, true);
            } catch (InvalidFormatException e) {
                Ui.displayInvalidFormat();
            } catch (InvalidCommandException e) {
                Ui.displayInvalidCommand();
            }
            break;
        case "EVENT":
            try {
                listCount = createEvent(input, listCount, tasks, true);
            } catch (InvalidFormatException e) {
                Ui.displayInvalidFormat();
            } catch (InvalidCommandException e) {
                Ui.displayInvalidCommand();
            }
            break;
        case "TODO":
            try {
                listCount = createToDo(input, listCount, tasks, true);
            } catch (InvalidCommandException e) {
                Ui.displayInvalidCommand();
            }
            break;
        default:
            try {
                listCount = createTraditionalTask(input, listCount, tasks, true);
            } catch (InvalidCommandException e) {
                Ui.displayInvalidCommand();
            }
            break;
        }
        return listCount;
    }

 */
}
