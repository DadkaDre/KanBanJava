package andrewkomarow.model;

import javax.crypto.spec.DESedeKeySpec;

public class Task {
    private int id;
    private String name;
    private String description;
    private Status status;

    private Type type;
    private Epic epic;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Task(Integer id, Type type, String name, Status status, String description) {
         this.id = id;
         this.type = type;
         this.name = name;
         this.status = status;
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
    public Type getType() {return Type.TASK;}
    public Epic getEpic() {
        return null;
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
