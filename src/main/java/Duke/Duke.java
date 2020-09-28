package Duke;

import Duke.Exceptions.InvalidCommandException;
import Duke.Exceptions.InvalidFormatException;
import Duke.Tasks.Deadline;
import Duke.Tasks.Event;
import Duke.Tasks.Task;
import Duke.Tasks.Todo;

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

    public static void main(String[] args) {
        String input;
        int byeIndicator = 1;
        int listIndicator;
        //Replaceable with size() function. Won't be replaced.
        int listCount = 0;
        //ui = new Ui(listCount);
        boolean isDone;
        boolean isEvent;
        boolean isDeadline;
        boolean isToDo;
        boolean isDelete;
        final int PRESENT = 0;

        ArrayList<Task> tasks = new ArrayList<>();

        listCount = fileParser(tasks, listCount);

        Ui.displayWelcomeMessage();
        Scanner scan = new Scanner(System.in);
        while(byeIndicator != PRESENT) {
            input = scan.nextLine();
            byeIndicator = input.compareToIgnoreCase("bye");
            listIndicator = input.compareToIgnoreCase("list");
            isDone = input.startsWith("done ");
            isDeadline = input.startsWith("deadline ");
            isEvent = input.startsWith("event ");
            isToDo = input.startsWith("todo ");
            isDelete = input.startsWith("delete ");
            if (byeIndicator == PRESENT) {
                Ui.displayByeMessage();
            } else if (listIndicator == PRESENT) {
                displayList(listCount, tasks);
            } else if (isDone) {
                flagAsDone(input, tasks);
            } else if (isDelete) {
                listCount = deleteTask(input, tasks, listCount);
            } else {
                if (isDeadline) {
                    try {
                        listCount = createDeadline(input, listCount, tasks, false);
                    } catch (InvalidFormatException e) {
                        Ui.displayInvalidFormat();
                    } catch (InvalidCommandException e) {
                        Ui.displayInvalidCommand();
                    }
                } else if (isEvent) {
                    try {
                        listCount = createEvent(input, listCount, tasks, false);
                    } catch (InvalidFormatException e) {
                        Ui.displayInvalidFormat();
                    } catch (InvalidCommandException e) {
                        Ui.displayInvalidCommand();
                    }
                } else if (isToDo) {
                    try {
                        listCount = createToDo(input, listCount, tasks, false);
                    } catch (InvalidCommandException e) {
                        Ui.displayInvalidCommand();
                    }
                } else {
                    try {
                        listCount = createTraditionalTask(input, listCount, tasks, false);
                    } catch (InvalidCommandException e) {
                        Ui.displayInvalidCommand();
                    }
                }
            }
        }
    }


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
            //System.out.println("Please enter a number!");
            Ui.displayNotNumberErrorMessage();
        }
        try {
            String taskType = tasks.get(i).getTaskType();
            status = tasks.get(i).getStatusIcon();
            //System.out.println("Noted. I've removed this task: ");
            Ui.displayRemoveMessage();
            switch (taskType) {
            //Traditional tasks are tasks specified in Level-2
            case "TRADITIONAL_TASK":
                //System.out.println("  [" + status + "] " + tasks.get(i).getDescription());
                Ui.displayTraditionalTask(status, tasks.get(i).getDescription());
                break;
            case "DEADLINE":
                temp = tasks.get(i).getTime();
                //System.out.println("  [D][" + status + "] " + tasks.get(i).getDescription() + "(by:" + temp + ")");
                Ui.displayDeadline(status, tasks.get(i).getDescription(), temp);
                break;
            case "EVENT":
                temp = tasks.get(i).getTime();
                //System.out.println("  [E][" + status + "] " + tasks.get(i).getDescription() + "(on:" + temp + ")");
                Ui.displayEvent(status, tasks.get(i).getDescription(), temp);
                break;
            case "TODO":
                //System.out.println("  [T][" + status + "] " + tasks.get(i).getDescription());
                Ui.displayToDo(status, tasks.get(i).getDescription());
                break;
            }
            tasks.remove(i);
            listCount--;
            refreshFile(tasks);
            return listCount;
        } catch (IndexOutOfBoundsException e) {
            //System.out.println("Invalid task number. No items are deleted.");
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
                //System.out.println("This task is already done!");
                return;
            }
        } catch (IndexOutOfBoundsException e) {
            //Does not show message since the function will continue to run to the bottom
        } catch (NumberFormatException e) {
            //System.out.println("Please Enter a number!");
            Ui.displayNotNumberErrorMessage();
            return;
        }
        try {
            //System.out.println("Nice! I've marked this task as done: ");
            Ui.displayTaskDoneMessage();
            String taskType = tasks.get(i).getTaskType();
            switch (taskType) {
            case "TRADITIONAL_TASK":
                //System.out.println("  [" + "\u2713" + "] " + tasks.get(i).getDescription());
                Ui.displayTraditionalTask("\u2713", tasks.get(i).getDescription());
                break;
            case "DEADLINE":
                temp = tasks.get(i).getTime();
                //System.out.println("  [D][" + "\u2713" + "] " + tasks.get(i).getDescription() + "(by:" + temp + ")");
                Ui.displayDeadline("\u2713", tasks.get(i).getDescription(), temp);
                break;
            case "EVENT":
                temp = tasks.get(i).getTime();
                //System.out.println("  [E][" + "\u2713" + "] " + tasks.get(i).getDescription() + "(on:" + temp + ")");
                Ui.displayEvent("\u2713", tasks.get(i).getDescription(), temp);
                break;
            case "TODO":
                //System.out.println("  [T][" + "\u2713" + "] " + tasks.get(i).getDescription());
                Ui.displayToDo("\u2713", tasks.get(i).getDescription());
                break;
            }
            tasks.get(i).markAsDone();
            refreshFile(tasks);
        } catch (IndexOutOfBoundsException e) {
            //System.out.println("Invalid task number or task does not exist. Please try again.");
            Ui.taskErrorMessage();
        }
    }

    public static void displayList(int listCount, ArrayList<Task> tasks) {
        final int ARRAY_OFFSET = 1;
        int i;
        //System.out.println("Here are the tasks in your list:");
        Ui.displayListMessage();
        for (i = 0; i < listCount; i++) {
            if (tasks.get(i).getDescription() != null) {
                //System.out.println(i + ARRAY_OFFSET + ". " + tasks.get(i).toString());
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

        //System.out.println("Added: " + input);
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
            /*
            throw new InvalidCommandException("Invalid command. " +
                    "If you wish to create a todo with a single space as description, " +
                    "please enter 2 spaces.");
             */
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
            /*
            throw new InvalidCommandException("Invalid command. " +
                    "If you wish to create a event with a single space as description, " +
                    "please enter 2 spaces.");
             */
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
            //throw new InvalidFormatException("Invalid format. Event cannot be empty.");
            throw new InvalidFormatException();
        } else if (!isValidFormat) {
            /*
            throw new InvalidFormatException("Invalid format. " +
                    "Please check you have entered \"/on \" properly.");
             */
            throw new InvalidFormatException();
        } else {
            String checkDate = input.substring(i + BY_ON_OFFSET + 1);
            boolean isEmpty = checkDate.isEmpty();
            if (isEmpty) {
                /*
                throw new InvalidFormatException("Invalid format. " +
                        "Please check you have entered a non-empty date/time.");
                 */
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
            /*
            throw new InvalidCommandException("Invalid command." +
                    " If you wish to create a deadline with a single space as description, " +
                    "please enter 2 spaces.");
             */
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
            //throw new InvalidFormatException("Invalid format. Deadline cannot be empty.");
            throw new InvalidFormatException();
        } else if (!isValidFormat) {
            /*
            throw new InvalidFormatException("Invalid format. " +
                    "Please check you have entered \"/by \" properly.");
             */
            throw new InvalidFormatException();
        } else {
            String checkDate = input.substring(i + BY_ON_OFFSET + 1);
            boolean isEmpty = checkDate.isEmpty();
            if (isEmpty) {
                /*
                throw new InvalidFormatException("Invalid format. " +
                        "Please check you have entered a non-empty date/time.");
                 */
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
        //System.out.println("Got it. I've added this task: ");
        Ui.displayAddMessage();
        //System.out.println("  " + task.toString());
        Ui.displayTask(task.toString());
        listCount++;
        //System.out.println("Now you have " + listCount + " tasks in the list.");
        Ui.displayNumberMessage(listCount);
        return listCount;
    }


public static void updateFile(Task tasks){
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

    public static void refreshFile(ArrayList<Task> tasks){
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
                System.out.println("duke.txt exists! Loading file contents...");
                return;
            }
            if (!duke.getParentFile().exists()) {
                duke.getParentFile().mkdirs();
                System.out.println("duke.txt does NOT exist. A new file has been created.");
                System.out.println("duke.txt has been created at: " + duke.getAbsolutePath());
                System.out.println("WARNING: Please do not move or delete duke.txt.");
            }
            duke.createNewFile();
        } catch (IOException e) {
            //System.out.println("Unable to create file! Reason: " + e.getMessage());
            Ui.displayMakeFileError(e.getMessage());
        }
    }

    public static int fileParser(ArrayList<Task> tasks, int listCount){
        createFile(new File("./duke.txt"));
        Path path = Paths.get("./duke.txt");

        Scanner loadFile = null;
        try {
            loadFile = new Scanner(path);
        } catch (IOException e) {
            //System.out.println("File not found error.");
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

    public static String reformatDate(String input, char taskType){

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

    public static int loadFileAtStartup(String input, int listCount, ArrayList<Task> tasks){
        boolean isDeadline;
        boolean isEvent;
        boolean isToDo;

        isDeadline = input.startsWith("deadline ");
        isEvent = input.startsWith("event ");
        isToDo = input.startsWith("todo ");
        if (isDeadline) {
            try {
                listCount = createDeadline(input, listCount, tasks, true);
            } catch (InvalidFormatException e) {
                Ui.displayInvalidFormat();
            } catch (InvalidCommandException e) {
                Ui.displayInvalidCommand();
            }
        } else if (isEvent) {
            try {
                listCount = createEvent(input, listCount, tasks, true);
            } catch (InvalidFormatException e) {
                Ui.displayInvalidFormat();
            } catch (InvalidCommandException e) {
                Ui.displayInvalidCommand();
            }
        } else if (isToDo) {
            try{
                listCount = createToDo(input, listCount, tasks, true);
            } catch (InvalidCommandException e) {
                Ui.displayInvalidCommand();
            }
        } else {
            try{
                listCount = createTraditionalTask(input, listCount, tasks, true);
            } catch (InvalidCommandException e) {
                Ui.displayInvalidCommand();
            }
        }
        return listCount;
    }
}
