package andrewkomarow.service;

import andrewkomarow.exception.NotFoundException;
import andrewkomarow.model.Epic;
import andrewkomarow.model.Status;
import andrewkomarow.model.SubTask;
import andrewkomarow.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    protected final Map<Integer, Task> tasks;
    protected final Map<Integer, SubTask> subtasks;
    protected final Map<Integer, Epic> epics;

    protected HistoryManager manager;

    protected int counter;

    public InMemoryTaskManager(HistoryManager manager) {
        this.manager = manager;
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();

        counter = 0;
    }

    @Override
    public List<Task> getTasks() {

        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<SubTask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public Task createTask(Task task) {
        task.setId(generateId());
        task.setStatus(Status.NEW);
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epic.setStatus(Status.NEW);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public SubTask createSubtask(Epic epic, SubTask subTask) {
        subTask.setId(generateId());
        subTask.setEpic(epic);
        subTask.setStatus(Status.NEW);
        epic.getEpicSubtasks().add(subTask);
        subtasks.put(subTask.getId(), subTask);
        return subTask;
    }

    @Override
    public Task getTaskId(int id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new NotFoundException("Нет задачи по id: " + id);
        }
        manager.add(task); // Добавляем задачу в историю
        return task;

    }

    @Override
    public Epic getEpicId(int id) {
        Epic epic = epics.get(id);
        if (epic == null) {
          throw new NotFoundException("Нет такого эпика по id: " + id);
        }
        manager.add(epic);
        updateEpicStatus(epic);
        return epic;
    }

    @Override
    public SubTask getSubTaskId(int id) {
        SubTask subTask = subtasks.get(id);
        if (subTask == null) {
           throw new NotFoundException("YНет такой подзадачи по id: "+ id);
        }
        manager.add(subTask);
        return subTask;
    }

    @Override
    public void removeTasks() {
        for (Integer id : tasks.keySet()) {
            manager.remove(id);
        }
        tasks.clear();
    }

    @Override
    public void removeEpics() {
        removeSubtasks();
        for (Integer id : epics.keySet()) {
            manager.remove(id);
        }
        subtasks.clear();
        epics.clear();

    }

    @Override
    public void removeSubtasks() {
        for (Integer id : subtasks.keySet()) {
            manager.remove(id);
        }
        for (Integer id : epics.keySet()) {
            manager.remove(id);

        }
        subtasks.clear();

    }

    @Override
    public void removeTaskId(int id) {

        if (id <= 0)  {
           throw new IllegalArgumentException("id должен быть больше нуля. Ваше значение: "+ id);
        }
        if (tasks.get(id) == null) {
            throw new NotFoundException("Нет такой задачи по id: " + id);
        }
        manager.remove(id);
        tasks.remove(id);

    }

    @Override
    public void removeEpicId(int id) {
        if (id <= 0 ) {
            throw new IllegalArgumentException("id должен быть больше нуля. Ваше значение: "+ id);
        }
        if (epics.get(id) == null) {
            throw new NotFoundException("Нет такого эпика по id: " + id);
        }
        manager.remove(id);
        epics.remove(id);

    }

    @Override
    public void removeSubTaskId(int id) {
        SubTask subTask = subtasks.get(id);
        if (subTask == null) {
            throw new NotFoundException("Нет такой подзадачи по айди");
        }
        if (id <= 0 ) {
            throw new IllegalArgumentException("Неверный параметр");

        }
            Epic epic = subTask.getEpic();
            epic.getEpicSubtasks().remove(subTask);
            manager.remove(id);
            updateEpicStatus(epic);
            subtasks.remove(id);
    }

    @Override
    public Task updateTask(int id, Task task) {
        Task oldTask = tasks.get(id);
        if (!check(id, task) ) {
            throw new IllegalArgumentException(String.format("Неверные параметры id: %d, object: %b", id,task));
        }
        if(oldTask == null) {
            throw new NotFoundException("Нет такой задачи по id: "+ id);
        }
        task.setId(id);
        task.setStatus(oldTask.getStatus());
        tasks.put(id, task);
        return task;
    }

    @Override
    public Epic updateEpic(int id, Epic epic) {
        Epic oldEpic = epics.get(id);
        if (!check(id, epic) ) {
            throw new IllegalArgumentException(String.format("Неверные параметры id: %d, Object: %b", id, epic));

        }
        if (oldEpic == null) {
            throw new NotFoundException("Нет такого эпика по id: "+ id);
        }
        oldEpic.setName(epic.getName());
        oldEpic.setDescription(epic.getDescription());
        return oldEpic;
    }

    @Override
    public SubTask updateSubTask(int id, SubTask subTask) {
        SubTask oldSubtask = subtasks.get(id);
        if (!check(id, subTask) ) {
            throw new IllegalArgumentException(String.format("Неверные параметры id: %d, object: %b", id,subTask));
        }
        if (oldSubtask == null) {
            throw new NotFoundException("Нет такой подзадачи по id: " + id);
        }
        subTask.setId(oldSubtask.getId());
        subTask.setStatus(oldSubtask.getStatus());
        subTask.setEpic(oldSubtask.getEpic());
        Epic oldEpic = epics.get(oldSubtask.getEpic().getId());
        for (SubTask subTask1 : oldEpic.getEpicSubtasks()) {
            if (subTask1.equals(subTask)) {
                subTask1.setName(subTask.getName());
                subTask1.setDescription(subTask.getDescription());
            }
        }
        updateEpicStatus(oldEpic);
        subtasks.put(id, subTask);
        return subTask;
    }


    private int generateId() {
        return ++counter;
    }

    private void updateEpicStatus(Epic epic) {
        int start = 0;
        int done = 0;

        List<SubTask> newList = epic.getEpicSubtasks();
        for (SubTask subTask : newList) {
            if (subTask.getStatus().equals(Status.NEW)) {
                start++;
            }
            if (subTask.getStatus().equals(Status.NEW)) {
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

    private boolean check(int id,Task obj) {
        return id > 0 && obj != null;
    }
}
