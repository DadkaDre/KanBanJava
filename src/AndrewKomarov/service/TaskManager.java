package AndrewKomarov.service;

import AndrewKomarov.model.Epic;
import AndrewKomarov.model.Status;
import AndrewKomarov.model.SubTask;
import AndrewKomarov.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskManager {
    private final Map<Integer, Task> tasks;
    private final Map<Integer, SubTask> subtasks;
    private final Map<Integer, Epic> epics;

    private int counter;

    public TaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        counter = 0;
    }

    public ArrayList<Object> getTasks() {
        return new ArrayList<Object>(tasks.values());
    }

    public ArrayList<Object> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Object> getSubtask() {
        return new ArrayList<>(subtasks.values());
    }

    public Task createTask(Task task) {
        task.setId(generateId());
        task.setStatus(Status.NEW);
        tasks.put(task.getId(), task);
        return task;
    }

    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epic.setStatus(Status.NEW);
        epics.put(epic.getId(), epic);
        return epic;
    }

    public SubTask createSubtask(Epic epic, SubTask subTask) {
        subTask.setId(generateId());
        subTask.setEpic(epic);
        subTask.setStatus(Status.NEW);
        epic.getEpicSubtasks().add(subTask);
        subtasks.put(subTask.getId(), subTask);
        return subTask;
    }

    public Task getIdTask(int id) {
        if (tasks.get(id) == null) {
            System.out.println("Такой задачи нет");
        }
        return tasks.get(id);

    }

    public Epic getEpicId(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
            System.out.println("Такого эпика нет");
            return null;
        }else {

            updateEpicStatus(epic);
            return epic;
        }
    }

    public SubTask getSubTaskId(int id) {
        SubTask subTask = subtasks.get(id);
        if (subTask == null) {
            System.out.println("Такой подзадачи нет");
            return null;
        } else {
            return subTask;
        }
    }

    public void removeTasks() {
        tasks.clear();
    }

    public void removeEpics() {
        epics.clear();
    }

    public void removeSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getEpicSubtasks().clear();
        }
        epics.clear();
        subtasks.clear();
    }
    public void removeTaskId(int id) {

        if (id <= 0 || tasks.get(id) == null) {
            System.out.println("Неверный параметр");

        } else {
            tasks.remove(id);
        }
    }
    public void removeSubTaskId(int id) {
        SubTask subTask = subtasks.get(id);
        if (id <= 0 || subTask == null) {
            System.out.println("Неверный параметр");

        } else {
            Epic epic = subTask.getEpic();
            epic.getEpicSubtasks().remove(subTask);
            updateEpicStatus(epic);
            subtasks.remove(id);
        }
    }

    public Task updateTask(int id, Task task) {
        Task oldTask = tasks.get(id);
        if (!check(id, task) || oldTask == null) {
            System.out.println("Неверные параметры");
            return null;
        }
        task.setId(id);
        task.setStatus(oldTask.getStatus());
        tasks.put(id, task);

        return task;

    }

    public Epic updateEpic(int id, Epic epic) {
        Epic oldEpic = epics.get(id);
        if (!check(id, epic) || oldEpic == null) {
            System.out.println("Неверные параметры");
            return null;
        }
        oldEpic.setName(epic.getName());
        oldEpic.setDescription(epic.getDescription());
        return oldEpic;
    }
    public SubTask updateSubTask(int id, SubTask subTask) {
        SubTask oldSubtask = subtasks.get(id);
        if (!check(id, subTask)|| oldSubtask == null) {
            System.out.println("Неверные параметры");
            return null;
        }
        subTask.setId(oldSubtask.getId());
        subTask.setStatus(oldSubtask.getStatus());
        subTask.setEpic(oldSubtask.getEpic());
        Epic oldEpic = epics.get(oldSubtask.getEpic().getId());
        for (SubTask subTask1: oldEpic.getEpicSubtasks()) {
            if (subTask1.equals(subTask)) {
                subTask1.setName(subTask.getName());
                subTask1.setDescription(subTask.getDescription());
            }
        }
        updateEpicStatus(oldEpic);
        subtasks.put(id,subTask);
        return subTask;
    }

    private int generateId() {
        return ++counter;
    }

    private void updateEpicStatus(Epic epic) {
        int start = 0;
        int done = 0;

        List newList = epic.getEpicSubtasks();
        for (Object subTask : newList) {
            SubTask newSubtask = (SubTask) subTask;
            if (newSubtask.getStatus().equals(Status.NEW)) {
                start++;
            }
            if (newSubtask.getStatus().equals(Status.NEW)) {
                done++;
            }
        }
        if (start == newList.size()) {
            epic.setStatus(Status.NEW);
        } else if (done == newList.size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    private boolean check(int id, Object obj) {
        return id > 0 && obj != null;
    }

}
