package andrewkomarow.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тестируем создание задач")
class TaskTest {

    @Test
    public void shouldTasksEqualsCopies() {
        Task task = new Task("Задача1", "description");
        Task task2 = new Task("Задача1", "description");

        assertEquals(task2.getName(), task.getName());
    }
}