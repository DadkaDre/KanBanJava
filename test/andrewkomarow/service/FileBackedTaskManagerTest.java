package andrewkomarow.service;

import andrewkomarow.model.Epic;
import andrewkomarow.model.SubTask;
import andrewkomarow.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тестируем класс Backed manager")
class FileBackedTaskManagerTest {

    TaskManager manager;
    Task task;
    Epic epic;
    SubTask subTask;

    @BeforeEach
    public void init() {
        manager = new FileBackedTaskManager(new InMemoryHistoryManager(), Paths.get("resources/test.CSV"));

        task = manager.createTask(new Task("task1", "description1"));
        epic = manager.createEpic(new Epic("epic", "descriptionEpic"));
        subTask = manager.createSubtask(epic, new SubTask("subTask", "descriptionSub"));
    }

    @Test
    void shouldSaveTasks() {

        TaskManager manager2 = FileBackedTaskManager.loadFromFile(Paths.get("resources/test.CSV"));
        manager2.createTask(new Task("task2", "description"));
        assertEquals(4, manager2.getTaskId(4).getId(), "id должен быть равен 4");

    }

    @Test
    void shouldLoadManager() {
        TaskManager manager2 = FileBackedTaskManager.loadFromFile(Paths.get("resources/test.CSV"));
        assertEquals(manager.getTasks(), manager2.getTasks(), "Задачи должны быть одинаковые");
    }
}