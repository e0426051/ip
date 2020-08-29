import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        String input;
        int byeCheck = 1;

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
            if (byeCheck == 0) {
                System.out.println("Bye. Hope to see you again soon!");
            } else {
                System.out.println(input);
            }
        }
    }
}
