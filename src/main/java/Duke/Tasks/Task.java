package Duke.Tasks;

public class Task {

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the raw description of the task.
     * @return description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a tick(done) or X(not done) symbol.
     * @return a tick or cross.
     */
    public String getStatusIcon() {
        //Returns tick or X symbol
        return (isDone ? "\u2713" : "\u2718");
    }

    /**
     * Returns a boolean whether a particular task is done or not.
     * @return isDone a boolean.
     */
    public boolean getStatus(){
        return (isDone);
    }

    /**
     * Marks a task as done.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * This function transforms the task to a String format.
     * @return a user friendly sentence about the event.
     */
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }

    /**
     * This function returns the type of task (traditional task) as a string format.
     * @return in string format the type of task.
     */
    public String getTaskType(){
        return TaskType.TRADITIONAL_TASK.toString();
    }

    public String getTime() {
        //Does nothing, meant for subclass usage
        return null;
    }
}