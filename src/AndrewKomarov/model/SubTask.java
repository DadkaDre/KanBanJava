package AndrewKomarov.model;

public class SubTask extends Task {
    private Epic epic;

    public void setEpic(Epic epic) {
        this.epic = epic;
    }
    public Epic getEpic() {
        return epic;
    }
    public SubTask(String name, String description) {
        super(name, description);
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id= "+ super.getId() +
                ", name= "+ super.getName() +
                ", description= "+super.getDescription()+
                ", status= "+super.getStatus()+
                "}";
    }
}
