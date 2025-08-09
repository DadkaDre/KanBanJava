package andrewkomarow.service;

import andrewkomarow.converter.TaskConverter;
import andrewkomarow.exception.ManagerIOException;
import andrewkomarow.model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Integer> map = new HashMap<>();
    private final Path path;

    public FileBackedTaskManager(HistoryManager manager, Path path) {
        super(manager);
        this.path = path;
    }

    public static FileBackedTaskManager loadFromFile(Path path) {
        FileBackedTaskManager manager = new FileBackedTaskManager(new InMemoryHistoryManager(), path);
        manager.load();
        return manager;
    }

    private Task fromString(String value) {

        String[] array = value.split(",");
        int id = Integer.parseInt(array[0]);
        Type type = Type.valueOf(array[1].trim());
        String name = array[2];
        Status status = Status.valueOf(array[3].trim());
        String description = array[4];

        if (type.equals(Type.SUB_TASK)) {
            map.put(id, Integer.parseInt(array[5].trim()));
        }
        return switch (type) {
            case TASK -> new Task(id, type, name, status, description);
            case EPIC -> new Epic(id, type, name, status, description);
            case SUB_TASK -> new SubTask(id, type, name, status, description);
        };
    }

    private void save() {
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            bw.write("id,type,name,status,description.epic");
            bw.newLine();
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                bw.write(TaskConverter.toString(entry.getValue()));
                bw.newLine();
            }
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                bw.write(TaskConverter.toString(entry.getValue()));
                bw.newLine();
            }
            for (Map.Entry<Integer, SubTask> entry : subtasks.entrySet()) {
                bw.write(TaskConverter.toString(entry.getValue()));
                bw.newLine();
            }

        } catch (IOException e) {
            throw new ManagerIOException("Проблемы с доступом к файлу");
        }
    }

    private void load() {
        int maxId = 0;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                Task task = fromString(line);
                int id = task.getId();
                switch (task.getType()) {
                    case TASK -> tasks.put(id, task);
                    case EPIC -> epics.put(id, (Epic) task);
                    case SUB_TASK -> subtasks.put(id, (SubTask) task);
                }
                maxId = Math.max(id, maxId);
            }
            counter = maxId;
            for (SubTask subTask : subtasks.values()) {
                subTask.setEpic(epics.get(map.get(subTask.getId())));
            }
        } catch (IOException e) {
            throw new ManagerIOException("Нет доступа к файлу ");
        }
    }

    @Override
    public Task createTask(Task task) {
        Task createdTask = super.createTask(task);
        save();
        return createdTask;
    }

    @Override
    public Epic createEpic(Epic epic) {
        Epic createdEpic = super.createEpic(epic);
        save();
        return createdEpic;
    }

    @Override
    public SubTask createSubtask(Epic epic, SubTask subTask) {
        SubTask createdSubTask = super.createSubtask(epic, subTask);
        save();
        return createdSubTask;
    }

    @Override
    public void removeTasks() {
        super.removeTasks();
        save();
    }

    @Override
    public void removeEpics() {
        super.removeEpics();
        save();
    }

    @Override
    public void removeSubtasks() {
        super.removeSubtasks();
        save();
    }

    @Override
    public void removeTaskId(int id) {
        super.removeTaskId(id);
        save();
    }

    @Override
    public void removeEpicId(int id) {
        super.removeEpicId(id);
        save();
    }

    @Override
    public void removeSubTaskId(int id) {
        super.removeSubTaskId(id);
        save();
    }

    @Override
    public Task updateTask(int id, Task task) {
        Task updatedTask = super.updateTask(id, task);
        save();
        return updatedTask;
    }

    @Override
    public Epic updateEpic(int id, Epic epic) {
        Epic updatedEpic = super.updateEpic(id, epic);
        save();
        return updatedEpic;
    }

    @Override
    public SubTask updateSubTask(int id, SubTask subTask) {
        SubTask updatedSubTask = super.updateSubTask(id, subTask);
        save();
        return updatedSubTask;
    }
}


