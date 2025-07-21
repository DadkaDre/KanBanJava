package andrewKomarow.model;

import andrewKomarow.service.HistoryManager;
import andrewKomarow.service.InMemoryHistoryManager;
import andrewKomarow.service.InMemoryTaskManager;
import andrewKomarow.service.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тестируем класс Эпик")
class EpicTest {
    private HistoryManager historyManager;
    private TaskManager manager;
    private Task task;
    private Epic epic;
    private SubTask subTask;

    @BeforeEach
    void init() {
        historyManager = new InMemoryHistoryManager();
        manager = new InMemoryTaskManager(historyManager);
        task = manager.createTask(new Task("Task", "description"));
        epic = manager.createEpic(new Epic("Epic", "description"));
        subTask = manager.createSubtask(epic, new SubTask("SubTask", "description"));
    }

    @Test
    @DisplayName("Тестируем получение подзадач эпика")
    void shouldReturnEpicSubtasksListWithSize1() {
        List<SubTask> list = manager.getEpicId(2).getEpicSubtasks();

        assertEquals(1, list.size(), "Кол-во элементов д.б. 1");
    }
}