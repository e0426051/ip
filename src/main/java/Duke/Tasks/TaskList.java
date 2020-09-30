package duke.tasks;

import duke.exceptions.InvalidCommandException;
import duke.exceptions.InvalidFormatException;

import duke.Ui;

import java.util.ArrayList;

public class TaskList {

    public static ArrayList<Task> tasks = new ArrayList<>();

    public static int deleteTask(String input, ArrayList<Task> tasks, int listCount) {
        final int DELETE_OFFSET = 7;
        final int ARRAY_OFFSET = 1;
        int lastNrPosition = input.length();
        String sub = input.substring(DELETE_OFFSET, lastNrPosition);
        int position = 0;
        String status;
        String temp;

        try {
            position = Integer.parseInt(sub) - ARRAY_OFFSET;
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            position = -1;
        }

        try {
            String taskType = tasks.get(position).getTaskType();
            status = tasks.get(position).getStatusIcon();
            Ui.displayRemoveMessage();
            switch (taskType) {
                //Traditional tasks are tasks specified in Level-2
                case "TRADITIONAL_TASK":
                    Ui.displayTraditionalTask(status, tasks.get(position).getDescription());
                    break;
                case "DEADLINE":
                    temp = tasks.get(position).getTime();
                    Ui.displayDeadline(status, tasks.get(position).getDescription(), temp);
                    break;
                case "EVENT":
                    temp = tasks.get(position).getTime();
                    Ui.displayEvent(status, tasks.get(position).getDescription(), temp);
                    break;
                case "TODO":
                    Ui.displayToDo(status, tasks.get(position).getDescription());
                    break;
            }
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
        final int ARRAY_OFFSET = 1;
        int position = 0;
        int lastNrPosition = input.length();
        boolean isError = false;
        String sub = input.substring(IS_DONE_OFFSET, lastNrPosition);
        String temp;

        try {
            position = Integer.parseInt(sub) - ARRAY_OFFSET;
            boolean alreadyDone = tasks.get(position).getStatus();
            if (alreadyDone) {
                Ui.displayTaskAlreadyDoneMessage();
                return;
            }
        } catch (IndexOutOfBoundsException e) {
            isError = true;
        } catch (NumberFormatException e) {
            Ui.displayNotNumberErrorMessage();
            return;
        }

        try {
            if (!isError) {
                Ui.displayTaskDoneMessage();
            }
            String taskType = tasks.get(position).getTaskType();
            switch (taskType) {
                case "TRADITIONAL_TASK":
                    Ui.displayTraditionalTask("\u2713", tasks.get(position).getDescription());
                    break;
                case "DEADLINE":
                    temp = tasks.get(position).getTime();
                    Ui.displayDeadline("\u2713", tasks.get(position).getDescription(), temp);
                    break;
                case "EVENT":
                    temp = tasks.get(position).getTime();
                    Ui.displayEvent("\u2713", tasks.get(position).getDescription(), temp);
                    break;
                case "TODO":
                    Ui.displayToDo("\u2713", tasks.get(position).getDescription());
                    break;
            }
            tasks.get(position).setAsDone();
            Storage.refreshFile(tasks);
        } catch (IndexOutOfBoundsException e) {
            Ui.displayTaskErrorMessage();
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
            if (tasks.get(i).getDescription() != null) {
                Ui.displayList(i, tasks.get(i).toString());
            }
        }
    }

    /**
     * Displays the tasks that corresponds with user input. If the additional
     * input is empty, it prints out the whole list.
     * @param listCount the number of tasks in the list.
     * @param tasks the arraylist of tasks.
     * @param input the user's input after "find ".
     */
    public static void displayFind(int listCount, ArrayList<Task> tasks, String input) {
        final int IS_FIND_OFFSET = 5;
        int i;
        int j = 0;
        int lastNrPosition = input.length();
        String sub = input.substring(IS_FIND_OFFSET, lastNrPosition);
        Ui.displayFindMessage();
        for (i = 0; i < listCount; i++) {
            if (tasks.get(i).getDescription() != null && (tasks.get(i).
                    getDescription().contains(sub)) ||
                    tasks.get(i).getTime().contains(sub)) {
                Ui.displayList(j, tasks.get(i).toString());
                j++;
            }
        }
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
     * @throws InvalidCommandException command is not valid.
     */
    public static int createEvent(String input, int listCount, ArrayList<Task> tasks, boolean initialize)
            throws InvalidFormatException, InvalidCommandException {
        final int INVALID = 0;
        int checkValid = input.compareTo("event ");
        if (checkValid == INVALID) {
            throw new InvalidCommandException();
        }
        final int EVENT_OFFSET = 6;
        final int BY_ON_OFFSET = 3;
        final int SLASH_ON_SPACE_OFFSET = 4;
        final int SLASH_NOT_FOUND = -1;
        final String INVALID_INPUT = "INV";
        String on;
        String inputTaskDescription;
        int position;
        position = input.indexOf("/");
        String checkMinInputFormat;

        if (position != SLASH_NOT_FOUND) {
            checkMinInputFormat = input.substring(position);
        } else {
            checkMinInputFormat = INVALID_INPUT;
        }

        boolean isValidFormat;
        if (checkMinInputFormat.length() >= SLASH_ON_SPACE_OFFSET) {
            String checkFormat = input.substring(position, position + BY_ON_OFFSET + 1);
            isValidFormat = checkFormat.equalsIgnoreCase("/on ");
        } else {
            isValidFormat = false;
        }

        if (position == EVENT_OFFSET) {
            throw new InvalidFormatException();
        } else if (!isValidFormat) {
            throw new InvalidFormatException();
        } else {
            String checkDate = input.substring(position + BY_ON_OFFSET + 1);
            boolean isEmpty = checkDate.isEmpty();
            if (isEmpty) {
                throw new InvalidFormatException();
            }
        }

        inputTaskDescription = input.substring(EVENT_OFFSET, position);
        on = input.substring(position + BY_ON_OFFSET);
        tasks.add(listCount, new Event(inputTaskDescription, on));

        if (!initialize) {
            Storage.updateFile(tasks.get(listCount));
        }

        return listInput(listCount, tasks.get(listCount));
    }

    /**
     * Creates a deadline.
     * @param input the user input.
     * @param listCount the number of tasks in the list.
     * @param tasks the arraylist of tasks.
     * @param initialize boolean of whether the command is input during startup or by user.
     * @return listCount
     * @throws InvalidCommandException command is not valid.
     */
    public static int createDeadline(String input, int listCount, ArrayList<Task> tasks, boolean initialize)
            throws InvalidFormatException, InvalidCommandException {
        final int INVALID = 0;
        int checkValid = input.compareTo("deadline ");
        if (checkValid == INVALID) {
            throw new InvalidCommandException();
        }

        final int BY_ON_OFFSET = 3;
        final int DEADLINE_OFFSET = 9;
        final int SLASH_BY_SPACE_OFFSET = 4;
        final int SLASH_NOT_FOUND = -1;
        final String INVALID_INPUT = "INV";
        String by;
        String inputTaskDescription;
        int position;
        position = input.indexOf("/");
        String checkMinInputFormat;

        if (position != SLASH_NOT_FOUND) {
            checkMinInputFormat = input.substring(position);
        } else {
            checkMinInputFormat = INVALID_INPUT;
        }

        boolean isValidFormat;
        if (checkMinInputFormat.length() >= SLASH_BY_SPACE_OFFSET) {
            String checkFormat = input.substring(position, position + BY_ON_OFFSET + 1);
            isValidFormat = checkFormat.equalsIgnoreCase("/by ");
        } else {
            isValidFormat = false;
        }

        if (position == DEADLINE_OFFSET) {
            throw new InvalidFormatException();
        } else if (!isValidFormat) {
            throw new InvalidFormatException();
        } else {
            String checkDate = input.substring(position + BY_ON_OFFSET + 1);
            boolean isEmpty = checkDate.isEmpty();
            if (isEmpty) {
                throw new InvalidFormatException();
            }
        }

        inputTaskDescription = input.substring(DEADLINE_OFFSET, position);
        by = input.substring(position + BY_ON_OFFSET);
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


}
