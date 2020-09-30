package Duke.Tasks;

public class Deadline extends Task {

    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * This function returns the time/date as String entered by the user after "/by".
     * @return this.by
     */
    @Override
    public String getTime() {
        return this.by;
    }

    /**
     * This function transforms the task to a String format.
     * @return a user friendly sentence about the deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by:" + by + ")";
    }

    /**
     * This function returns the type of task (deadline) as a string format.
     * @return in string format the type of task.
     */
    @Override
    public String getTaskType() {
        return TaskType.DEADLINE.toString();
    }
}