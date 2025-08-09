package andrewkomarow.model;

public class SubTask extends Task {
    private Epic epic;

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    @Override
    public Epic getEpic() {
        return epic;
    }

    public SubTask(String name, String description) {
        super(name, description);
    }

    public SubTask(Integer id, Type type, String name, Status status, String description) {
        super(id, type, name, status, description);
        this.epic = epic;
    }

    @Override
    public Type getType() {
        return Type.SUB_TASK;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id= " + super.getId() +
                ", name= " + super.getName() +
                ", description= " + super.getDescription() +
                ", status= " + super.getStatus() +
                ", epic= " + getEpic() +
                "}";
    }
}
