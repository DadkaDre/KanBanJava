package andrewKomarow.model;

public class Task {
    private int id;
    private String name;
    private String description;
    private Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task task = (Task) obj;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        int prime = 31;
        hash = hash * prime + (id);
        return hash;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id= " + id +
                ", name= " + name +
                ", description= " + description +
                ", status= " + status + "}";
    }
}
