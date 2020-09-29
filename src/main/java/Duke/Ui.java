package Duke;

public class Ui {

    public static void displayWelcomeMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What can I do for you?");
    }

    public static void displayRemoveMessage() {
        System.out.println("Noted. I've removed this task: ");
    }

    public static void displayDeleteIndexOutOfBoundsError() {
        System.out.println("Invalid task number. No items are deleted.");
    }

    public static void displayTaskAlreadyDoneMessage() {
        System.out.println("This task is already done!");
    }

    public static void displayTaskDoneMessage() {
        System.out.println("Nice! I've marked this task as done: ");
    }

    public static void taskErrorMessage() {
        System.out.println("Invalid task number or task does not exist. Please try again.");
    }

    public static void displayListMessage() {
        System.out.println("Here are the tasks in your list:");
    }

    public static void displayAddMessage() {
        System.out.println("Got it. I've added this task: ");
    }

    public static void displayTraditionalAddMessage(String input) {
        System.out.println("Added: " + input);
    }

    public static void displayNumberMessage(int listCount) {
        //TEST whether the listCount is updated from zero. Call new Ui ui at beginning.
        System.out.println("Now you have " + listCount + " tasks in the list.");
    }

    public static void displayNotNumberErrorMessage() {
        System.out.println("Please Enter a number!");
    }

    public static void displayByeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void displayFileNotFoundError() {
        System.out.println("File not found error.");
    }

    public static void displayTraditionalTask(String status, String description) {
        System.out.println("  [" + status + "] " + description);
    }

    public static void displayDeadline(String status, String description, String by) {
        System.out.println("  [D][" + status + "] " + description + "(by:" + by + ")");
    }

    public static void displayEvent(String status, String description, String on) {
        System.out.println("  [E][" + status + "] " + description + "(on:" + on + ")");
    }

    public static void displayToDo(String status, String description) {
        System.out.println("  [T][" + status + "] " + description);
    }

    public static void displayList(int Number, String info) {
        final int ARRAY_OFFSET = 1;
        System.out.println(Number + ARRAY_OFFSET + ". " + info);
    }

    public static void displayTask(String info) {
        System.out.println("  " + info);
    }

    public static void displayInvalidCommand() {
        System.out.println("Invalid command.");
    }

    public static void displayInvalidFormat() {
        System.out.println("Invalid format. Please check your syntax.");
    }

    public static void displayIOError() {
        System.out.println("I/O error. File not found or corrupt.");
    }

    public static void displayMakeFileError(String input) {
        System.out.println("Unable to create file! Reason: " + input);
    }

    public static void displayFilePresentMessage() {
        System.out.println("duke.txt exists! Loading file contents...");
    }

    public static void displayFileNotPresentMessage(String directory, boolean isFileMade) {
        if (isFileMade) {
            System.out.println("duke.txt does NOT exist. A new file has been created.");
            System.out.println("duke.txt has been created at: " + directory);
            System.out.println("WARNING: Please do not move or delete duke.txt.");
        } else {
            System.out.println("There is a problem preventing a new file from being created.");
        }
    }

}
