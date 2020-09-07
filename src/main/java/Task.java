public class Task {

    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public String getStatusIcon() {
        //Returns tick or X symbol
        return (isDone ? "\u2713" : "\u2718");
    }

    public boolean getStatus(){
        return (isDone);
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }

    public int getTaskType(){
        return 0;
    }

    public String getTime() {
        //Does nothing, meant for subclass usage
        return null;
    }
}