package Duke.Tasks;

import Duke.Exceptions.InvalidCommandException;
import Duke.Exceptions.InvalidFormatException;
import Duke.Ui;
import com.sun.source.util.TaskListener;

import java.util.ArrayList;

public class TaskList {

    public static ArrayList<Task> tasks = new ArrayList<>();

    public static int deleteTask(String input, ArrayList<Task> tasks, int listCount) {
        final int DELETE_OFFSET = 7;
        final int ARRAY_OFFSET = 1;
        int lastNrPosition = input.length();
        String sub = input.substring(DELETE_OFFSET, lastNrPosition);
        int i = 0;
        String status;
        String temp;

        try {
            i = Integer.parseInt(sub) - ARRAY_OFFSET;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid task number. No items are deleted.");
        } catch (NumberFormatException e) {
            Ui.displayNotNumberErrorMessage();
        }
        try {
            String taskType = tasks.get(i).getTaskType();
            status = tasks.get(i).getStatusIcon();
            Ui.displayRemoveMessage();
            switch (taskType) {
                //Traditional tasks are tasks specified in Level-2
                case "TRADITIONAL_TASK":
                    Ui.displayTraditionalTask(status, tasks.get(i).getDescription());
                    break;
                case "DEADLINE":
                    temp = tasks.get(i).getTime();
                    Ui.displayDeadline(status, tasks.get(i).getDescription(), temp);
                    break;
                case "EVENT":
                    temp = tasks.get(i).getTime();
                    Ui.displayEvent(status, tasks.get(i).getDescription(), temp);
                    break;
                case "TODO":
                    Ui.displayToDo(status, tasks.get(i).getDescription());
                    break;
            }
            tasks.remove(i);
            listCount--;
            Storage.refreshFile(tasks);
            return listCount;
        } catch (IndexOutOfBoundsException e) {
            Ui.displayDeleteIndexOutOfBoundsError();
        }
        return listCount;
    }

    public static void flagAsDone(String input, ArrayList<Task> tasks) {
        final int IS_DONE_OFFSET = 5;
        final int ARRAY_OFFSET = 1;
        int i = 0;
        int lastNrPosition = input.length();
        String sub = input.substring(IS_DONE_OFFSET, lastNrPosition);
        String temp;

        try {
            i = Integer.parseInt(sub) - ARRAY_OFFSET;
            boolean alreadyDone = tasks.get(i).getStatus();
            if (alreadyDone) {
                Ui.displayTaskAlreadyDoneMessage();
                return;
            }
        } catch (IndexOutOfBoundsException e) {
            //Does not show message since the function will continue to run to the bottom
        } catch (NumberFormatException e) {
            Ui.displayNotNumberErrorMessage();
            return;
        }
        try {
            Ui.displayTaskDoneMessage();
            String taskType = tasks.get(i).getTaskType();
            switch (taskType) {
                case "TRADITIONAL_TASK":
                    Ui.displayTraditionalTask("\u2713", tasks.get(i).getDescription());
                    break;
                case "DEADLINE":
                    temp = tasks.get(i).getTime();
                    Ui.displayDeadline("\u2713", tasks.get(i).getDescription(), temp);
                    break;
                case "EVENT":
                    temp = tasks.get(i).getTime();
                    Ui.displayEvent("\u2713", tasks.get(i).getDescription(), temp);
                    break;
                case "TODO":
                    Ui.displayToDo("\u2713", tasks.get(i).getDescription());
                    break;
            }
            tasks.get(i).markAsDone();
            Storage.refreshFile(tasks);
        } catch (IndexOutOfBoundsException e) {
            Ui.taskErrorMessage();
        }
    }

    public static void displayList(int listCount, ArrayList<Task> tasks) {
        int i;
        Ui.displayListMessage();
        for (i = 0; i < listCount; i++) {
            if (tasks.get(i).getDescription() != null) {
                Ui.displayList(i, tasks.get(i).toString());
            }
        }
    }

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

    public static int createToDo(String input, int listCount, ArrayList<Task> tasks, boolean initialize)
            throws InvalidCommandException {
        final int TO_DO_OFFSET = 5;
        int checkValid = input.compareTo("todo ");
        if (checkValid == 0) {
            throw new InvalidCommandException();
        }
        String inputTaskDescription;
        inputTaskDescription = input.substring(TO_DO_OFFSET);
        tasks.add(listCount, new Todo(inputTaskDescription));
        if (!initialize) {
            Storage.updateFile(tasks.get(listCount));
        }
        return listInput(listCount, tasks.get(listCount));
    }

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
        int i;
        i = input.indexOf("/");
        String checkMinInputFormat;
        if (i != SLASH_NOT_FOUND) {
            checkMinInputFormat = input.substring(i);
        } else {
            checkMinInputFormat = INVALID_INPUT;
        }
        boolean isValidFormat;
        if (checkMinInputFormat.length() >= SLASH_ON_SPACE_OFFSET) {
            String checkFormat = input.substring(i, i + BY_ON_OFFSET + 1);
            isValidFormat = checkFormat.equalsIgnoreCase("/on ");
        } else {
            isValidFormat = false;
        }
        if (i == EVENT_OFFSET) {
            throw new InvalidFormatException();
        } else if (!isValidFormat) {
            throw new InvalidFormatException();
        } else {
            String checkDate = input.substring(i + BY_ON_OFFSET + 1);
            boolean isEmpty = checkDate.isEmpty();
            if (isEmpty) {
                throw new InvalidFormatException();
            }
        }
        inputTaskDescription = input.substring(EVENT_OFFSET, i);
        on = input.substring(i + BY_ON_OFFSET);
        tasks.add(listCount, new Event(inputTaskDescription, on));
        if (!initialize) {
            Storage.updateFile(tasks.get(listCount));
        }
        return listInput(listCount, tasks.get(listCount));
    }

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
        int i;
        i = input.indexOf("/");
        String checkMinInputFormat;
        if (i != SLASH_NOT_FOUND) {
            checkMinInputFormat = input.substring(i);
        } else {
            checkMinInputFormat = INVALID_INPUT;
        }
        boolean isValidFormat;
        if (checkMinInputFormat.length() >= SLASH_BY_SPACE_OFFSET) {
            String checkFormat = input.substring(i, i + BY_ON_OFFSET + 1);
            isValidFormat = checkFormat.equalsIgnoreCase("/by ");
        } else {
            isValidFormat = false;
        }
        if (i == DEADLINE_OFFSET) {
            throw new InvalidFormatException();
        } else if (!isValidFormat) {
            throw new InvalidFormatException();
        } else {
            String checkDate = input.substring(i + BY_ON_OFFSET + 1);
            boolean isEmpty = checkDate.isEmpty();
            if (isEmpty) {
                throw new InvalidFormatException();
            }
        }
        inputTaskDescription = input.substring(DEADLINE_OFFSET, i);
        by = input.substring(i + BY_ON_OFFSET);
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
