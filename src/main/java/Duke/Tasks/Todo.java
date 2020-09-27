package Duke.Tasks;

public class Todo extends Task {

    TaskType TODO;

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String getTaskType() {
        return TaskType.TODO.toString();
    }
}