import java.util.Scanner;
import java.util.Arrays;

public class Duke {
    public static void main(String[] args) {
        String input;
        int byeCheck = 1;
        int listToggle = 1;
        int listCount = 0;
        boolean doneToggle = false;
        int i = 0;
        int j = -1;
        String isDone = new String();
        //String[] list = new String[100];
        Task[] tasks = new Task[100];

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What can I do for you?");
        Scanner scan = new Scanner(System.in);
        while(byeCheck != 0) {
            input = scan.nextLine();
            byeCheck = input.compareToIgnoreCase("bye");
            listToggle = input.compareToIgnoreCase("list");
            doneToggle = input.startsWith("done ");
            if (byeCheck == 0) {
                System.out.println("Bye. Hope to see you again soon!");
            }
            else if (listToggle == 0) {
                System.out.println("Here are the tasks in your list:");
                //for (i = 0; i < list.length; i++) {
                for (i = 0; i < listCount; i++) {
                    //if (list[i] != null) {
                    isDone = tasks[i].getStatusIcon();
                    if (tasks[i].getDescription() != null) {
                        //System.out.println(i + 1 + ": " + list[i]);
                        System.out.println(i + 1 + ".[" + isDone + "] " + tasks[i].getDescription());
                    }
                }
            }
            else if (doneToggle == true) {
                int lastNrPosition = input.length();
                String sub = input.substring(5,lastNrPosition); //start at the 6th position
                j = Integer.parseInt(sub) - 1;
                if (j >= 0 && j < 100) {
                    System.out.println("Nice! I've marked this task as done: ");
                    System.out.println("  [" + "\u2713" + "] " + tasks[j].getDescription());
                    tasks[j].markAsDone();
                    j = -1;
                }
            }
            else {
                //put into list
                //list[listCount] = input;
                tasks[listCount] = new Task(input);
                listCount++;
                System.out.println("Added: " + input);
            }
        }
    }
}
