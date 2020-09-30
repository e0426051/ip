package duke;

public class Ui {

    /**
     * Displays the welcome message.
     */
    public static void displayWelcomeMessage() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("What can I do for you?");
    }


    /**
     * Displays the task removal message. Used during task deletion.
     */
    public static void displayRemoveMessage() {
        System.out.println("Noted. I've removed this task: ");
    }

    /**
     * Displays error message, when the supplied task number to the
     * delete function is not valid.
     */
    public static void displayDeleteError() {
        System.out.println("Invalid task number or input is not a number. No items are deleted.");
    }

    /**
     * Displays message stating task is already marked as done previously.
     */
    public static void displayTaskAlreadyDoneMessage() {
        System.out.println("This task is already done!");
    }

    /**
     * Displays message for use while marking a task as done.
     */
    public static void displayTaskDoneMessage() {
        System.out.println("Nice! I've marked this task as done: ");
    }

    /**
     * Displays error message, when number supplied to "mark as done" function is not valid.
     */
    public static void displayTaskErrorMessage() {
        System.out.println("Invalid task number or task does not exist. Please try again.");
    }

    /**
     * Displays the top message before the list.
     */
    public static void displayListMessage() {
        System.out.println("Here are the tasks in your list:");
    }

    /**
     * Displays the message during addition of tasks, except for traditional tasks.
     */
    public static void displayAddMessage() {
        System.out.println("Got it. I've added this task: ");
    }

    /**
     * Displays the message during addition of traditional tasks.
     * @param input the user input.
     */
    public static void displayTraditionalAddMessage(String input) {
        System.out.println("Added: " + input);
    }

    /**
     * Displays the number of tasks in the list, during addition of tasks
     * except traditional tasks.
     * @param listCount number of tasks in the list.
     */
    public static void displayNumberMessage(int listCount) {
        System.out.println("Now you have " + listCount + " tasks in the list.");
    }

    /**
     * Displays error message when a number is expected but a non-number is entered.
     * E.g. "done t" where done expects a number but "t" is not a number.
     */
    public static void displayNotNumberErrorMessage() {
        System.out.println("Please Enter a number!");
    }

    /**
     * Displays the bye message before program exits.
     */
    public static void displayByeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void displayFileNotFoundError() {
        System.out.println("File not found error.");
    }

    /**
     * Displays the traditional tasks in a human readable form.
     * @param status whether the task is done, in tick or cross.
     * @param description task description.
     */
    public static void displayTraditionalTask(String status, String description) {
        System.out.println("  [" + status + "] " + description);
    }

    /**
     * Displays the deadlines in a human readable form.
     * @param status whether the task is done, in tick or cross.
     * @param description task description.
     * @param by date and/or time specified by user.
     */
    public static void displayDeadline(String status, String description, String by) {
        System.out.println("  [D][" + status + "] " + description + "(by:" + by + ")");
    }

    /**
     * Displays the events in a human readable form.
     * @param status whether the task is done, in tick or cross.
     * @param description task description.
     * @param on date and/or time specified by user.
     */
    public static void displayEvent(String status, String description, String on) {
        System.out.println("  [E][" + status + "] " + description + "(on:" + on + ")");
    }

    /**
     * Displays the todos in a human readable form.
     * @param status whether the task is done, in tick or cross.
     * @param description task description.
     */
    public static void displayToDo(String status, String description) {
        System.out.println("  [T][" + status + "] " + description);
    }


    /**
     * Prints out the list of tasks.
     * @param number the number of the corresponding task.
     * @param info the description and date/time of the task where applicable.
     */
    public static void displayList(int number, String info) {
        final int ARRAY_OFFSET = 1;
        System.out.println(number + ARRAY_OFFSET + ". " + info);
    }

    public static void displayFindMessage() {
        System.out.println("Here are the matching tasks in your list:");
    }

    public static void displayTask(String info) {
        System.out.println("  " + info);
    }

    /**
     * Displays error message when command entered is invalid.
     */
    public static void displayInvalidCommand() {
        System.out.println("Invalid command.");
    }

    /**
     * Displays error message when command syntax entered is invalid.
     */
    public static void displayInvalidFormat() {
        System.out.println("Invalid format. Please check your syntax.");
    }

    /**
     * Displays error message related to duke.txt.
     */
    public static void displayIoError() {
        System.out.println("I/O error. File not found or corrupt.");
    }

    public static void displayMakeFileError(String input) {
        System.out.println("Unable to create file! Reason: " + input);
    }

    /**
     * Displays message when duke.txt exists.
     */
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

    public static void displayFileLocation(String input) {
        System.out.println("duke.txt is located at " + input);
    }

}
