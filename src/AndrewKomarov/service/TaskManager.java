package AndrewKomarov.service;

import AndrewKomarov.model.Epic;
import AndrewKomarov.model.SubTask;
import AndrewKomarov.model.Task;

import java.util.List;

public interface TaskManager {
    List<Task> getTasks();

    List<Epic> getEpics();

    List<SubTask> getSubtasks();


    Task createTask(Task task);

    Epic createEpic(Epic epic);

    SubTask createSubtask(Epic epic, SubTask subTask);

    Task getTaskId(int id);

    Epic getEpicId(int id);

    SubTask getSubTaskId(int id);

    void removeTasks();

    void removeEpics();

    void removeSubtasks();

    void removeTaskId(int id);

    void removeEpicId(int id);

    void removeSubTaskId(int id);

    Task updateTask(int id, Task task);

    Epic updateEpic(int id, Epic epic);

    SubTask updateSubTask(int id, SubTask subTask);

}
