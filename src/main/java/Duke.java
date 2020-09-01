import java.util.Scanner;
import java.util.Arrays;

public class Duke {
    public static void main(String[] args) {
        String input;
        //Stores the non-date portions for Events and Deadlines
        String inputMod;
        int byeIndicator = 1;
        int listIndicator;
        int listCount = 0;
        boolean isDone;
        int i;
        int j;
        String by;
        String on;
        boolean isEvent;
        boolean isDeadline;
        boolean isToDo;

        Task[] tasks = new Task[100];

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What can I do for you?");
        Scanner scan = new Scanner(System.in);
        while(byeIndicator != 0) {
            input = scan.nextLine();
            byeIndicator = input.compareToIgnoreCase("bye");
            listIndicator = input.compareToIgnoreCase("list");
            isDone = input.startsWith("done ");
            isDeadline = input.startsWith("deadline ");
            isEvent = input.startsWith("event ");
            isToDo = input.startsWith("todo ");
            if (byeIndicator == 0) {
                System.out.println("Bye. Hope to see you again soon!");
            } else if (listIndicator == 0) {
                System.out.println("Here are the tasks in your list:");
                for (i = 0; i < listCount; i++) {
                    if (tasks[i].getDescription() != null) {
                        System.out.println(i + 1 + ". " + tasks[i].toString());
                    }
                }
            } else if (isDone) {
                int lastNrPosition = input.length();
                //Starts at the 6th position
                String sub = input.substring(5,lastNrPosition);
                j = Integer.parseInt(sub) - 1;
                int taskType = tasks[j].taskType();
                //Assumes user does not enter a number bigger than number of items in the list
                if (j >= 0 && j < 100) {
                    System.out.println("Nice! I've marked this task as done: ");
                    if (taskType == 0) {
                        System.out.println("  [" + "\u2713" + "] " + tasks[j].getDescription());
                    } else if (taskType == 1) {
                        String temp = tasks[j].getTime();
                        System.out.println("  [D][" + "\u2713" + "] " + tasks[j].getDescription() + "(by:" + temp + ")");
                    } else if (taskType == 2) {
                        String temp = tasks[j].getTime();
                        System.out.println("  [E][" + "\u2713" + "] " + tasks[j].getDescription() + "(on:" + temp + ")");
                    } else if (taskType == 3) {
                        System.out.println("  [T][" + "\u2713" + "] " + tasks[j].getDescription());
                    } else {
                        //Assumes taskType is in range
                    }
                    tasks[j].markAsDone();
                }
            } else {
                //Assumes at most one task type is true
                //TODO move these into classes instead of Duke.java where possible
                if (isDeadline) {
                    i = input.indexOf("/");
                    //Avoids printing "deadline" in list
                    inputMod = input.substring(9,i);
                    //Avoids printing "by" twice
                    by = input.substring(i + 3);
                    tasks[listCount] = new Deadline(inputMod, by);
                    System.out.println("Got it. I've added this task: ");
                    System.out.println("  " + tasks[listCount].toString());
                    listCount++;
                    System.out.println("Now you have " + listCount + " tasks in the list.");
                } else if (isEvent) {
                    i = input.indexOf("/");
                    //Avoids printing "event" in list
                    inputMod = input.substring(6,i);
                    //Avoids printing "on" twice
                    on = input.substring(i + 3);
                    tasks[listCount] = new Event(inputMod, on);
                    System.out.println("Got it. I've added this task: ");
                    System.out.println("  " + tasks[listCount].toString());
                    listCount++;
                    System.out.println("Now you have " + listCount + " tasks in the list.");
                } else if (isToDo) {
                    inputMod = input.substring(5);
                    tasks[listCount] = new Todo(inputMod);
                    System.out.println("Got it. I've added this task: ");
                    System.out.println("  " + tasks[listCount].toString());
                    listCount++;
                    System.out.println("Now you have " + listCount + " tasks in the list.");
                } else {
                    //Updates list with traditional level-2 task
                    tasks[listCount] = new Task(input);
                    listCount++;
                    System.out.println("Added: " + input);
                }
            }
        }
    }
}
