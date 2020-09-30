package Duke.Tasks;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    /**
     * This function transforms the task to a String format.
     * @return a user friendly sentence about the event.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * This function returns the type of task (todo) as a string format.
     * @return in string format the type of task.
     */
    @Override
    public String getTaskType() {
        return TaskType.TODO.toString();
    }
}