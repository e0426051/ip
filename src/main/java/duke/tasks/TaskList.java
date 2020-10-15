package duke.tasks;

import duke.exceptions.InvalidCommandException;
import duke.exceptions.InvalidFormatException;

import duke.ui.Ui;
import duke.storage.Storage;

import java.util.ArrayList;

public class TaskList {

    public static ArrayList<Task> tasks = new ArrayList<>();

    public static int deleteTask(String input, ArrayList<Task> tasks, int listCount) {
        final int DELETE_OFFSET = 7;
        final int ARRAY_OFFSET = 1;
        int lastNrPosition = input.length();
        String sub = input.substring(DELETE_OFFSET, lastNrPosition);
        int position;
        String status;

        try {
            position = Integer.parseInt(sub) - ARRAY_OFFSET;
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            position = -1;
        }

        try {
            String taskType = tasks.get(position).getTaskType();
            status = tasks.get(position).getStatusIcon();
            String taskDescription = tasks.get(position).getDescription();
            Ui.displayRemoveMessage();
            Ui.displaySingleTask(taskDescription, taskType, status, position);
            tasks.remove(position);
            listCount--;
            Storage.refreshFile(tasks);
            return listCount;
        } catch (IndexOutOfBoundsException e) {
            Ui.displayDeleteError();
        }
        return listCount;
    }

    /**
     * Flags a task as done. Takes the positive integer the user inputs and
     * mark the corresponding task as done.
     * @param input the user input.
     * @param tasks the arraylist of tasks.
     */
    public static void flagAsDone(String input, ArrayList<Task> tasks) {
        final int IS_DONE_OFFSET = 5;
        int lastNrPosition = input.length();
        String sub = input.substring(IS_DONE_OFFSET, lastNrPosition);
        int position = checkIsDone(sub);
        if (position != -1) {
            Ui.displayTaskDoneMessage();
            String taskType = tasks.get(position).getTaskType();
            String taskDescription = tasks.get(position).getDescription();
            String tick = "\u2713";
            Ui.displaySingleTask(taskDescription, taskType, tick, position);
            tasks.get(position).setAsDone();
            Storage.refreshFile(tasks);
        }
    }

    /**
     * Displays the task list to the user.
     * @param listCount the number of tasks in the list.
     * @param tasks the arraylist of tasks.
     */
    public static void displayList(int listCount, ArrayList<Task> tasks) {
        int i;
        Ui.displayListMessage();
        for (i = 0; i < listCount; i++) {
            Ui.displayList(i, tasks.get(i).toString());
        }
    }

    /**
     * Displays the tasks that corresponds with user input. If the additional
     * input is empty, it prints out the whole list.
     * @param listCount the number of tasks in the list.
     * @param input the user's input after "find ".
     */
    public static void displayFind(int listCount, String input) {
        final int IS_FIND_OFFSET = 5;
        int i;
        int j = 0;
        int lastNrPosition = input.length();
        String sub = input.substring(IS_FIND_OFFSET, lastNrPosition);
        Ui.displayFindMessage();
        for (i = 0; i < listCount; i++) {
            j += matchResult(i, sub, j);
        }
    }

    public static int matchResult(int taskNumber, String input, int subListCount) {
        if ((tasks.get(taskNumber).getDescription().contains(input)) ||
                tasks.get(taskNumber).getTime().contains(input)) {
            Ui.displayList(subListCount, tasks.get(taskNumber).toString());
            return 1;
        }
        return 0;
    }

    /**
     * Creates a traditional task.
     * @param input the user input.
     * @param listCount the number of tasks in the list.
     * @param tasks the arraylist of tasks.
     * @param initialize boolean of whether the command is input during startup or by user.
     * @return listCount
     * @throws InvalidCommandException command is not valid.
     */
    public static int createTraditionalTask(String input, int listCount, ArrayList<Task> tasks, boolean initialize)
            throws InvalidCommandException {
        //Accepts "todo", "deadline" and "event" without spaces as traditional tasks.
        int checkValid = input.compareTo("");
        if (checkValid == 0) {
            throw new InvalidCommandException();
        }
        tasks.add(listCount, new Task(input));
        Ui.displayTraditionalAddMessage(input);

        if (!initialize) {
            Storage.updateFile(tasks.get(listCount));
        }
        listCount++;
        return listCount;
    }

    /**
     * Creates a todo
     * @param input the user input.
     * @param listCount the number of tasks in the list.
     * @param tasks the arraylist of tasks.
     * @param initialize boolean of whether the command is input during startup or by user.
     * @return listCount
     * @throws InvalidCommandException command is not valid.
     */
    public static int createToDo(String input, int listCount, ArrayList<Task> tasks, boolean initialize)
            throws InvalidCommandException {
        final int TO_DO_OFFSET = 5;
        int checkValid = input.compareTo("todo ");
        if (checkValid == 0) {
            throw new InvalidCommandException();
        }

        String inputTaskDescription;
        inputTaskDescription = input.substring(TO_DO_OFFSET);
        tasks.add(listCount, new ToDo(inputTaskDescription));

        if (!initialize) {
            Storage.updateFile(tasks.get(listCount));
        }

        return listInput(listCount, tasks.get(listCount));
    }

    /**
     * Creates a event.
     * @param input the user input.
     * @param listCount the number of tasks in the list.
     * @param tasks the arraylist of tasks.
     * @param initialize boolean of whether the command is input during startup or by user.
     * @return listCount
     */
    public static int createEvent(String input, int listCount, ArrayList<Task> tasks, boolean initialize) {

        final int EVENT_OFFSET = 6;
        final int BY_ON_OFFSET = 3;

        int position;
        position = input.indexOf("/");
        try {
            checkEventFormat(input);
        } catch (InvalidFormatException e) {
            Ui.displayInvalidFormat();
            return listCount;
        } catch (InvalidCommandException e) {
            Ui.displayInvalidCommand();
            return listCount;
        }

        String inputTaskDescription = input.substring(EVENT_OFFSET, position);
        String on = input.substring(position + BY_ON_OFFSET);
        tasks.add(listCount, new Event(inputTaskDescription, on));

        if (!initialize) {
            Storage.updateFile(tasks.get(listCount));
        }

        return listInput(listCount, tasks.get(listCount));
    }

    /**
     * Checks whether the format of Event is valid.
     * @param input input by the user.
     * @throws InvalidCommandException invalid command entered.
     * @throws InvalidFormatException invalid format entered.
     */
    public static void checkEventFormat(String input)
            throws InvalidCommandException, InvalidFormatException{
        final int INVALID = 0;
        int checkValid = input.compareTo("event ");
        if (checkValid == INVALID) {
            throw new InvalidCommandException();
        }

        final int EVENT_OFFSET = 6;
        final int BY_ON_OFFSET = 3;
        int position;
        position = input.indexOf("/");
        String checkMinInputFormat = checkSlash(input, position);

        boolean isValidFormat = checkFormat(checkMinInputFormat, input,
                position, true);

        if (position == EVENT_OFFSET || !isValidFormat) {
            throw new InvalidFormatException();
        } else {
            String checkDate = input.substring(position + BY_ON_OFFSET + 1);
            boolean isEmpty = checkDate.isEmpty();
            if (isEmpty) {
                throw new InvalidFormatException();
            }
        }
    }

    /**
     * Checks whether user inputs a slash in a event or deadline.
     * @param input user's input.
     * @param position the position of the slash in the string.
     * @return the position of the slash, or -1 if no slash found.
     */
    public static String checkSlash(String input, int position) {
        final int SLASH_NOT_FOUND = -1;
        final String INVALID_INPUT = "INV";
        if (position != SLASH_NOT_FOUND) {
            return input.substring(position);
        } else {
            return INVALID_INPUT;
        }
    }

    /**
     * Checks the format of deadline and event tasks, specifically whether
     * the user entered "/by " for deadlines or "/on " for events.
     * @param checkLength the substring containing the keywords with the slash
     * @param input the user's input.
     * @param position position of the slash.
     * @param isEvent whether the task is a event. If false, the task is a deadline.
     * @return true if the keywords are found, and false if the keywords are not found.
     */
    public static boolean checkFormat(String checkLength, String input,
            int position, boolean isEvent) {
        final int BY_ON_OFFSET = 3;
        final int FORMAT_OFFSET = 4;
        if (checkLength.length() >= FORMAT_OFFSET) {
            String checkFormat = input.substring(position, position + BY_ON_OFFSET + 1);
            if (isEvent) {
                return checkFormat.equalsIgnoreCase("/on ");
            } else {
                return checkFormat.equalsIgnoreCase("/by ");
            }
        } else {
            return false;
        }
    }

    /**
     * Checks whether the format of Deadline is valid.
     * @param input input by the user.
     * @throws InvalidCommandException invalid command entered.
     * @throws InvalidFormatException invalid format entered.
     */
    public static void checkDeadlineFormat(String input)
            throws InvalidCommandException, InvalidFormatException {
        final int INVALID = 0;
        int checkValid = input.compareTo("deadline ");
        if (checkValid == INVALID) {
            throw new InvalidCommandException();
        }

        final int BY_ON_OFFSET = 3;
        final int DEADLINE_OFFSET = 9;
        int position;
        position = input.indexOf("/");
        String checkMinInputFormat = checkSlash(input, position);

        boolean isValidFormat = checkFormat(checkMinInputFormat, input,
                position, false);

        if (position == DEADLINE_OFFSET || !isValidFormat) {
            throw new InvalidFormatException();
        } else {
            String checkDate = input.substring(position + BY_ON_OFFSET + 1);
            boolean isEmpty = checkDate.isEmpty();
            if (isEmpty) {
                throw new InvalidFormatException();
            }
        }
    }

    /**
     * Creates a deadline.
     * @param input the user input.
     * @param listCount the number of tasks in the list.
     * @param tasks the arraylist of tasks.
     * @param initialize boolean of whether the command is input during startup or by user.
     * @return listCount
     */
    public static int createDeadline(String input, int listCount, ArrayList<Task> tasks, boolean initialize) {
        final int BY_ON_OFFSET = 3;
        final int DEADLINE_OFFSET = 9;

        int position;
        position = input.indexOf("/");

        try {
            checkDeadlineFormat(input);
        } catch (InvalidFormatException e) {
            Ui.displayInvalidFormat();
            return listCount;
        } catch (InvalidCommandException e) {
            Ui.displayInvalidCommand();
            return listCount;
        }

        String inputTaskDescription = input.substring(DEADLINE_OFFSET, position);
        String by = input.substring(position + BY_ON_OFFSET);
        tasks.add(listCount, new Deadline(inputTaskDescription, by));

        if (!initialize) {
            Storage.updateFile(tasks.get(listCount));
        }

        return listInput(listCount, tasks.get(listCount));
    }

    public static int listInput(int listCount, Task task) {
        Ui.displayAddMessage();
        Ui.displayTask(task.toString());
        listCount++;
        Ui.displayNumberMessage(listCount);
        return listCount;
    }

    /**
     * This function checks whether the task is already marked done.
     * It returns the number of the task, or -1 if the task is already
     * done or the input is not valid.
     * @param sub a substring of the input.
     * @return whether there is an error.
     */
    public static int checkIsDone(String sub){
        final int ARRAY_OFFSET = 1;
        try {
            int position = Integer.parseInt(sub) - ARRAY_OFFSET;
            boolean alreadyDone = tasks.get(position).getStatus();
            if (alreadyDone) {
                Ui.displayTaskAlreadyDoneMessage();
                return -1;
            }
            return position;
        } catch (IndexOutOfBoundsException e) {
            Ui.displayTaskErrorMessage();
            return -1;
        } catch (NumberFormatException e) {
            Ui.displayNotNumberErrorMessage();
            return -1;
        }
    }
}
