package Duke.Tasks;

public class Event extends Task {

    protected String on;

    public Event(String description, String on) {
        super(description);
        this.on = on;
    }

    /**
     * This function returns the time/date as String entered by the user after "/on".
     * @return this.on
     */
    @Override
    public String getTime() {
        return this.on;
    }

    /**
     * This function transforms the task to a String format.
     * @return a user friendly sentence about the event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + "(on:" + on + ")";
    }

    /**
     * This function returns the type of task (event) as a string format.
     * @return in string format the type of task.
     */
    @Override
    public String getTaskType() {
        return TaskType.EVENT.toString();
    }
}