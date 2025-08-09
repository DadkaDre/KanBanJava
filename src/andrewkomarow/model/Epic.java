package andrewkomarow.model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<SubTask> epicSubtasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(Integer id, Type type, String name, Status status, String description) {
        super(id, type, name, status, description);
    }

    public List<SubTask> getEpicSubtasks() {
        return epicSubtasks;
    }

    @Override
    public Type getType() {
        return Type.EPIC;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id= " + super.getId() +
                ", name= " + super.getName() +
                ", description= " + super.getDescription() +
                ", status= " + super.getStatus() +
                "}";
    }
}
