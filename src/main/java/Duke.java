import java.util.Scanner;
import java.util.Arrays;

public class Duke {
    public static void main(String[] args) {
        String input;
        int byeCheck = 1;
        int listToggle = 1;
        int listCount = 0;
        int i = 0;
        String[] list = new String[100];

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
            if (byeCheck == 0) {
                System.out.println("Bye. Hope to see you again soon!");
            }
            else if (listToggle == 0) {
                for (i = 0; i < list.length; i++) {
                    if (list[i] != null) {
                        System.out.println(i + 1 + ": " + list[i]);
                    }
                }
                //continue;
            }
            else {
                //put into list
                list[listCount] = input;
                listCount++;
                System.out.println("Added: " + input);
            }
        }
    }
}
