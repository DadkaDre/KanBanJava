package AndrewKomarov.model;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<SubTask> epicSubtasks = new ArrayList<>();

    public  ArrayList<SubTask> getEpicSubtasks() {
        return epicSubtasks;
    }
    public Epic (String name, String description) {
        super(name, description);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id= "+ super.getId() +
                ", name= "+ super.getName() +
                ", description= "+super.getDescription()+
                ", status= "+super.getStatus()+
                "}";
    }
}
