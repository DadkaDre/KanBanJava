package andrewKomarow.service;

import andrewKomarow.model.Epic;
import andrewKomarow.model.SubTask;
import andrewKomarow.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тестируем менеджер")
class InMemoryTaskManagerTest {


    private HistoryManager historyManager;
    private TaskManager manager;
    private Task task;
    private Epic epic;
    private SubTask subTask;
    private SubTask subTask2;

    @BeforeEach
    void init() {
        historyManager = new InMemoryHistoryManager();
        manager = new InMemoryTaskManager(historyManager);
        task = manager.createTask(new Task("Task", "description"));
        epic = manager.createEpic(new Epic("Epic", "description"));
        subTask = manager.createSubtask(epic, new SubTask("SubTask", "description"));
        subTask2 = manager.createSubtask(epic, new SubTask("SubTask", "description2"));

    }

    @Test
    @DisplayName("Тестируем метод получения задач")
    void shouldReturnTaskList() {
        List<Task> tasksList = manager.getTasks();
        assertEquals(1, tasksList.size(), "Кол-во элементов в листе д.б. 1");
    }

    @Test
    @DisplayName("Тестируем метод плучения всех Эпиков")
    void shouldReturnEpicList() {
        List<Epic> epicList = manager.getEpics();
        assertEquals(1, epicList.size(), "Кол-во елементов в листе должно быть 1");
    }

    @Test
    @DisplayName("Метод получения эпиков должен возвращать добавленный эпик в лист")
    void shouldReturnAddedEpicInEpicList() {
        Epic newEpic = manager.getEpics().getFirst();
        assertEquals(epic.getName(), newEpic.getName());
    }

    @Test
    @DisplayName("Тестируем метод получения подзадач")
    void shouldReturnSubtasksList() {
        List<SubTask> listSubTask = manager.getSubtasks();
        assertEquals(2, listSubTask.size(), "Кол-во элементов в листе д.б. 1");
    }

    @Test
    @DisplayName("Тестируем метод создания подзадачи")
    void shouldCreateTask() {
        Task newTask = manager.getTasks().getFirst();
        assertEquals(task.getName(), newTask.getName(), "Имена задач должны совпадать");
        assertEquals(task.getDescription(), newTask.getDescription(), "Описание должно совпадать");
    }

    @Test
    @DisplayName("Тестируем метод создания Эпика")
    void shouldCreateEpic() {
        Epic newEpic = manager.getEpics().getFirst();
        assertEquals(epic.getName(), newEpic.getName(), "Имя должно совпадать");
        assertEquals(epic.getDescription(), newEpic.getDescription(), "Описание должно совпадать");
    }

    @Test
    @DisplayName("Тестируем метод создание подзадачи")
    void shouldCreateSubtask() {
        SubTask newSubTask = manager.getSubtasks().getFirst();
        assertEquals(subTask.getName(), newSubTask.getName(), "Имена должны совпадать");
        assertEquals(subTask.getDescription(), newSubTask.getDescription(), "Описание должно совпадать");
    }

    @Test
    @DisplayName("Тестируем получение задачи по id")
    void shouldReturnTaskWithId1() {
        Task newTask = manager.getTaskId(1);
        Task task2 = new Task("Task", "description");

        assertEquals(newTask.getName(), task2.getName(), "Имена должны совпадать");
        assertEquals(newTask.getDescription(), task2.getDescription(), "Описание должно совпадать");
    }

    @Test
    @DisplayName("Тестируем метод получения эпика по id")
    void shouldReturnEpicWithId2() {
        Epic newEpic = manager.getEpicId(2);
        Epic epic2 = new Epic("Epic", "description");

        assertEquals(epic2.getName(), newEpic.getName(), "Имена должны совпадать");
        assertEquals(epic2.getDescription(), newEpic.getDescription(), "Описание должно совпадать");
    }

    @Test
    @DisplayName("Тестируем метод получения подзадачи по id")
    void shouldReturnSubTaskWithId3() {
        SubTask newSubTask = manager.getSubTaskId(3);
        SubTask subTask1 = new SubTask("SubTask", "description");

        assertEquals(newSubTask.getName(), subTask1.getName(), "Имена не совпадают");
        assertEquals(newSubTask.getDescription(), subTask1.getDescription(), "Описание не совпадает");
    }

    @Test
    @DisplayName("Тестируем метод удаления всех задач")
    void shouldReturnEmptyTasksList() {
        manager.removeTasks();
        List<Task> list = manager.getTasks();

        assertEquals(0, list.size(), "Список должен быть пуст");
    }

    @Test
    @DisplayName("Тестируем метод удаления всех эпиков")
    void shouldReturnEmptyEpicsList() {
        manager.removeEpics();
        List<Epic> list = manager.getEpics();

        assertEquals(0, list.size(), "Список должен быть пуст");
    }

    @Test
    @DisplayName("Тестируем метод удаления  подзадач")
    void shouldReturnEmptySubtasksList() {
        manager.removeSubtasks();
        List<SubTask> list = manager.getSubtasks();

        assertEquals(0, list.size(), "Лист должен быть пуст");
    }

    @Test
    @DisplayName("Тестируем метод удаления задачи по id")
    void shouldRemoveTaskWithId1() {
        manager.removeTaskId(1);

        assertNull(manager.getTaskId(1));
    }

    @Test
    @DisplayName("Тестируем метод удаления по id")
    void shouldRemoveEpicWithId2() {
        manager.removeEpicId(2);

        assertNull(manager.getEpicId(2));
    }

    @Test
    @DisplayName("Тестируем метод удаления подзадачи по id")
    void shouldRemoveSubTaskWithId3() {
        manager.removeSubTaskId(3);

        assertNull(manager.getSubTaskId(3));
    }

    @Test
    @DisplayName("Тестируем метод обновления Задачи")
    void shouldReturnTaskWithNameNewTask() {
        Task newTask = new Task("newTask", "newDescription");
        manager.updateTask(1, newTask);

        assertEquals(newTask.getName(), manager.getTaskId(1).getName(), "Имена должны совпадать");
    }

    @Test
    @DisplayName("Тестируем метод обновления Эпика")
    void shouldReturnUpdateEpic() {
        Epic oldEpic = manager.getEpicId(2);
        Epic newEpic = new Epic("NewEpic", "NewDescription");
        manager.updateEpic(2, newEpic);

        assertEquals(newEpic.getName(), manager.getEpicId(2).getName(), "Имена должны совпадать");
        assertEquals(oldEpic.getStatus(), manager.getEpicId(2).getStatus(), "Status олжен быть одинаковым");
        assertEquals(oldEpic.getEpicSubtasks(), manager.getEpicId(2).getEpicSubtasks(), "Подзадачи должны совпадать");
    }

    @Test
    @DisplayName("Тестируем метод обновления подзадачи")
    void shouldReturnUpdateSubTask() {
        SubTask oldSubTask = manager.getSubTaskId(3);
        SubTask newSubTask = new SubTask("NewSubTask", "NewDescription");
        manager.updateSubTask(3, newSubTask);

        assertEquals(newSubTask.getName(), manager.getSubTaskId(3).getName(), "Имена должны совпадать");
        assertEquals(oldSubTask.getStatus(), manager.getSubTaskId(3).getStatus(), "Статусы должны совпадать");
        assertEquals(oldSubTask.getEpic(), manager.getSubTaskId(3).getEpic(), "Епики у подзадачи д.б. одинаковые");
    }
}