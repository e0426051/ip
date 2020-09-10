import Exceptions.InvalidCommandException;
import Exceptions.InvalidFormatException;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;

import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String input;
        int byeIndicator = 1;
        int listIndicator;
        int listCount = 0;
        boolean isDone;
        boolean isEvent;
        boolean isDeadline;
        boolean isToDo;
        final int PRESENT = 0;

        Task[] tasks = new Task[100];

        displayWelcomeMessage();
        Scanner scan = new Scanner(System.in);
        while(byeIndicator != PRESENT) {
            input = scan.nextLine();
            byeIndicator = input.compareToIgnoreCase("bye");
            listIndicator = input.compareToIgnoreCase("list");
            isDone = input.startsWith("done ");
            isDeadline = input.startsWith("deadline ");
            isEvent = input.startsWith("event ");
            isToDo = input.startsWith("todo ");
            if (byeIndicator == PRESENT) {
                displayByeMessage();
            } else if (listIndicator == PRESENT) {
                displayList(listCount, tasks);
            } else if (isDone) {
                flagAsDone(input, tasks);
            } else if (listCount == 100) {
                displayMaxTaskMessage();
            } else {
                if (isDeadline) {
                    try {
                        listCount = createDeadline(input, listCount, tasks);
                    } catch (InvalidFormatException | InvalidCommandException e) {
                        e.printStackTrace();
                    }
                } else if (isEvent) {
                    try {
                        listCount = createEvent(input, listCount, tasks);
                    } catch (InvalidFormatException | InvalidCommandException e) {
                        e.printStackTrace();
                    }
                } else if (isToDo) {
                    try {
                        listCount = createToDo(input, listCount, tasks);
                    } catch (InvalidCommandException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        listCount = createTraditionalTask(input, listCount, tasks);
                    } catch (InvalidCommandException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void displayMaxTaskMessage() {
        System.out.println("You have entered 100 tasks in Duke. Please reset Duke to enter new tasks.");
    }

    public static void displayWelcomeMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What can I do for you?");
    }

    public static void flagAsDone(String input, Task[] tasks) {
        final int IS_DONE_OFFSET = 5;
        final int ARRAY_OFFSET = 1;
        final int TODO = 3;
        final int EVENT = 2;
        final int DEADLINE = 1;
        //Traditional tasks are tasks specified in Level-2
        final int TRADITIONAL_TASK = 0;
        int i = 0;
        int lastNrPosition = input.length();
        String sub = input.substring(IS_DONE_OFFSET, lastNrPosition);
        try {
            i = Integer.parseInt(sub) - ARRAY_OFFSET;
            boolean alreadyDone = tasks[i].getStatus();
            if (alreadyDone) {
                System.out.println("This task is already done!");
                return;
            }
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            //Does not show message since the function will continue to run to the bottom
        } catch (NumberFormatException e) {
            System.out.println("Please Enter a number!");
            return;
        }
        try {
            int taskType = tasks[i].getTaskType();
            System.out.println("Nice! I've marked this task as done: ");
            if (taskType == TRADITIONAL_TASK) {
                System.out.println("  [" + "\u2713" + "] " + tasks[i].getDescription());
            } else if (taskType == DEADLINE) {
                String temp = tasks[i].getTime();
                System.out.println("  [D][" + "\u2713" + "] " + tasks[i].getDescription() + "(by:" + temp + ")");
            } else if (taskType == EVENT) {
                String temp = tasks[i].getTime();
                System.out.println("  [E][" + "\u2713" + "] " + tasks[i].getDescription() + "(on:" + temp + ")");
            } else if (taskType == TODO) {
                System.out.println("  [T][" + "\u2713" + "] " + tasks[i].getDescription());
            }
            tasks[i].markAsDone();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid task number. Please try again.");
        } catch (NullPointerException e) {
            System.out.println("Task does not exist. Please try again.");
        }
    }

    public static void displayList(int listCount, Task[] tasks) {
        final int ARRAY_OFFSET = 1;
        int i;
        System.out.println("Here are the tasks in your list:");
        for (i = 0; i < listCount; i++) {
            if (tasks[i].getDescription() != null) {
                System.out.println(i + ARRAY_OFFSET + ". " + tasks[i].toString());
            }
        }
    }

    public static int createTraditionalTask(String input, int listCount, Task[] tasks)
            throws InvalidCommandException {
        //Accepts "todo", "deadline" and "event" without spaces as traditional tasks.
        int checkValid = input.compareTo("");
        if (checkValid == 0) {
            throw new InvalidCommandException("Invalid command.");
        }
        tasks[listCount] = new Task(input);
        listCount++;
        System.out.println("Added: " + input);
        return listCount;
    }

    public static int createToDo(String input, int listCount, Task[] tasks)
            throws InvalidCommandException {
        final int TO_DO_OFFSET = 5;
        int checkValid = input.compareTo("todo ");
        if (checkValid == 0) {
            throw new InvalidCommandException("Invalid command. " +
                    "If you wish to create a todo with a single space as description, " +
                    "please enter 2 spaces.");
        }
        String inputTaskDescription;
        inputTaskDescription = input.substring(TO_DO_OFFSET);
        tasks[listCount] = new Todo(inputTaskDescription);
        return listInput(listCount, tasks[listCount]);
    }

    public static int createEvent(String input, int listCount, Task[] tasks)
            throws InvalidFormatException, InvalidCommandException {
        final int INVALID = 0;
        int checkValid = input.compareTo("event ");
        if (checkValid == INVALID) {
            throw new InvalidCommandException("Invalid command. " +
                    "If you wish to create a event with a single space as description, " +
                    "please enter 2 spaces.");
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
            throw new InvalidFormatException("Invalid format. Event cannot be empty.");
        } else if (!isValidFormat) {
            throw new InvalidFormatException("Invalid format. " +
                    "Please check you have entered \"/on \" properly.");
        } else {
            String checkDate = input.substring(i + BY_ON_OFFSET + 1);
            boolean isEmpty = checkDate.isEmpty();
            if (isEmpty) {
                throw new InvalidFormatException("Invalid format. " +
                        "Please check you have entered a non-empty date/time.");
            }
        }
        inputTaskDescription = input.substring(EVENT_OFFSET, i);
        on = input.substring(i + BY_ON_OFFSET);
        tasks[listCount] = new Event(inputTaskDescription, on);
        return listInput(listCount, tasks[listCount]);
    }

    public static int createDeadline(String input, int listCount, Task[] tasks)
            throws InvalidFormatException, InvalidCommandException {
        final int INVALID = 0;
        int checkValid = input.compareTo("deadline ");
        if (checkValid == INVALID) {
            throw new InvalidCommandException("Invalid command." +
                    " If you wish to create a deadline with a single space as description, " +
                    "please enter 2 spaces.");
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
            throw new InvalidFormatException("Invalid format. Deadline cannot be empty.");
        } else if (!isValidFormat) {
            throw new InvalidFormatException("Invalid format. " +
                    "Please check you have entered \"/by \" properly.");
        } else {
            String checkDate = input.substring(i + BY_ON_OFFSET + 1);
            boolean isEmpty = checkDate.isEmpty();
            if (isEmpty) {
                throw new InvalidFormatException("Invalid format. " +
                        "Please check you have entered a non-empty date/time.");
            }
        }
        inputTaskDescription = input.substring(DEADLINE_OFFSET, i);
        by = input.substring(i + BY_ON_OFFSET);
        tasks[listCount] = new Deadline(inputTaskDescription, by);
        return listInput(listCount, tasks[listCount]);
    }

    public static int listInput(int listCount, Task task) {
        System.out.println("Got it. I've added this task: ");
        System.out.println("  " + task.toString());
        listCount++;
        System.out.println("Now you have " + listCount + " tasks in the list.");
        return listCount;
    }

    public static void displayByeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
