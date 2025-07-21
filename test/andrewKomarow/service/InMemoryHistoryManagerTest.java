package andrewKomarow.service;

import andrewKomarow.model.Epic;
import andrewKomarow.model.SubTask;
import andrewKomarow.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тестируем класс менеджер историй")
class InMemoryHistoryManagerTest {
    private HistoryManager historyManager;
    private TaskManager manager;
    private Task task;
    private Task task2;
    private Task task3;
    private Epic epic;
    private Epic epic2;
    private Epic epic3;
    private SubTask subTask2;
    private SubTask subtask3;
    private SubTask subTask;

    @BeforeEach
    public void init() {
        historyManager = new InMemoryHistoryManager();
        manager = new InMemoryTaskManager(historyManager);
        task = manager.createTask(new Task("Task", "description"));
        task2 = manager.createTask(new Task("Task2", "description2"));
        task3 = manager.createTask(new Task("Task3", "description3"));
        epic = manager.createEpic(new Epic("Epic", "description"));
        epic2 = manager.createEpic(new Epic("Epic2", "description2"));
        epic3 = manager.createEpic(new Epic("Epic3", "description3"));
        subTask = manager.createSubtask(epic, new SubTask("SubTask", "description"));
        subTask2 = manager.createSubtask(epic2, new SubTask("Subtask2", "description2"));
        subtask3 = manager.createSubtask(epic3, new SubTask("Subtask3", "description3"));

        manager.getTaskId(task.getId());
        manager.getTaskId(task2.getId());
        manager.getTaskId(task3.getId());
        manager.getEpicId(epic.getId());
        manager.getEpicId(epic2.getId());
        manager.getEpicId(epic3.getId());
        manager.getSubTaskId(subTask.getId());
        manager.getSubTaskId(subTask2.getId());
        manager.getSubTaskId(subtask3.getId());
    }

    @Test
    @DisplayName("Тестируем метод добавления")
    void shouldAdd2Tasks() {

        assertEquals(historyManager.getHistory(), List.of(task, task2, task3, epic, epic2, epic3, subTask, subTask2, subtask3), "Кол-во не совпадает");
    }

    @Test
    @DisplayName("Тестируем получение истории")
    void shouldReturn9elementsInList() {
        assertEquals(historyManager.getHistory().size(), 9, "Кол-во в листе не совпадает");
    }

    @Test
    @DisplayName("Тестируем удаления всех Задач из истории")
    void shouldRemoveAllTasks() {
        manager.removeTasks();
        assertEquals(historyManager.getHistory(), List.of(epic, epic2, epic3, subTask, subTask2, subtask3));
    }

    @Test
    @DisplayName("Тестируем удаление всех эпиков из истории")
    void shouldRemoveAllEpics() {
        manager.removeEpics();
        assertEquals(historyManager.getHistory(), List.of(task, task2, task3));
    }

    @Test
    @DisplayName("Тестируем удаление всех подзадач")
    void shouldRemoveAllSubTasks() {
        manager.removeSubtasks();
        assertEquals(historyManager.getHistory(), List.of(task, task2, task3));
    }

}