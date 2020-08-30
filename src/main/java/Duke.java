import java.util.Scanner;
import java.util.Arrays;

public class Duke {
    public static void main(String[] args) {
        String input;
        int byeIndicator = 1;
        int listIndicator = 1;
        int listCount = 0;
        boolean isDone = false;
        int i = 0;
        int j = -1;
        String statusIcon = new String();
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
            if (byeIndicator == 0) {
                System.out.println("Bye. Hope to see you again soon!");
            } else if (listIndicator == 0) {
                System.out.println("Here are the tasks in your list:");
                for (i = 0; i < listCount; i++) {
                    statusIcon = tasks[i].getStatusIcon();
                    if (tasks[i].getDescription() != null) {
                        System.out.println(i + 1 + ".[" + statusIcon + "] " + tasks[i].getDescription());
                    }
                }
            } else if (isDone == true) {
                int lastNrPosition = input.length();
                //Starts at the 6th position
                String sub = input.substring(5,lastNrPosition);
                j = Integer.parseInt(sub) - 1;
                //Assumes user does not enter a number bigger than number of items in the list
                if (j >= 0 && j < 100) {
                    System.out.println("Nice! I've marked this task as done: ");
                    System.out.println("  [" + "\u2713" + "] " + tasks[j].getDescription());
                    tasks[j].markAsDone();
                }
            } else {
                tasks[listCount] = new Task(input);
                listCount++;
                System.out.println("Added: " + input);
            }
        }
    }
}
