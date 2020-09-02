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
            } else {
                //Assumes at most one task type is true
                if (isDeadline) {
                    listCount = createDeadline(input, listCount, tasks);
                } else if (isEvent) {
                    listCount = createEvent(input, listCount, tasks);
                } else if (isToDo) {
                    listCount = createToDo(input, listCount, tasks);
                } else {
                    listCount = createTraditionalTask(input, listCount, tasks);
                }
            }
        }
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
        int IS_DONE_OFFSET = 5;
        int ARRAY_OFFSET = 1;
        int TODO = 3;
        int EVENT = 2;
        int DEADLINE = 1;
        int TRADITIONAL_TASK = 0;
        int j;
        int lastNrPosition = input.length();
        String sub = input.substring(IS_DONE_OFFSET,lastNrPosition);
        j = Integer.parseInt(sub) - ARRAY_OFFSET;
        int taskType = tasks[j].getTaskType();
        //Assumes user does not enter a number bigger than number of items in the list
        System.out.println("Nice! I've marked this task as done: ");
        if (taskType == TRADITIONAL_TASK) {
            System.out.println("  [" + "\u2713" + "] " + tasks[j].getDescription());
        } else if (taskType == DEADLINE) {
            String temp = tasks[j].getTime();
            System.out.println("  [D][" + "\u2713" + "] " + tasks[j].getDescription() + "(by:" + temp + ")");
        } else if (taskType == EVENT) {
            String temp = tasks[j].getTime();
            System.out.println("  [E][" + "\u2713" + "] " + tasks[j].getDescription() + "(on:" + temp + ")");
        } else if (taskType == TODO) {
            System.out.println("  [T][" + "\u2713" + "] " + tasks[j].getDescription());
        }
        tasks[j].markAsDone();
    }

    public static void displayList(int listCount, Task[] tasks) {
        int ARRAY_OFFSET = 1;
        int i;
        System.out.println("Here are the tasks in your list:");
        for (i = 0; i < listCount; i++) {
            if (tasks[i].getDescription() != null) {
                System.out.println(i + ARRAY_OFFSET + ". " + tasks[i].toString());
            }
        }
    }

    public static int createTraditionalTask(String input, int listCount, Task[] tasks) {
        tasks[listCount] = new Task(input);
        listCount++;
        System.out.println("Added: " + input);
        return listCount;
    }

    public static int createToDo(String input, int listCount, Task[] tasks) {
        int TO_DO_OFFSET = 5;
        String inputTaskDescription;
        inputTaskDescription = input.substring(TO_DO_OFFSET);
        tasks[listCount] = new Todo(inputTaskDescription);
        return listInput(listCount, tasks[listCount]);
    }

    public static int createEvent(String input, int listCount, Task[] tasks) {
        int EVENT_OFFSET = 6;
        int BY_ON_OFFSET = 3;
        String on;
        String inputTaskDescription;
        int i;
        i = input.indexOf("/");
        inputTaskDescription = input.substring(EVENT_OFFSET, i);
        on = input.substring(i + BY_ON_OFFSET);
        tasks[listCount] = new Event(inputTaskDescription, on);
        return listInput(listCount, tasks[listCount]);
    }

    public static int createDeadline(String input, int listCount, Task[] tasks) {
        int BY_ON_OFFSET = 3;
        int DEADLINE_OFFSET = 9;
        String by;
        String inputTaskDescription;
        int i;
        i = input.indexOf("/");
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
