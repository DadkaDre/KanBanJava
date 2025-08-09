package andrewkomarow.converter;

import andrewkomarow.model.Task;

public class TaskConverter {

    public static String toString(Task task) {
        String str = task.getEpic() == null ? null : task.getEpic().getId() + "";

        return task.getId() + ", " + task.getType() + ", " + task.getName() + ", " + task.getStatus() + ", " + task.getDescription() + ", " + str;
    }
}