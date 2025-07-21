package andrewkomarow;

import andrewkomarow.model.Epic;
import andrewkomarow.model.SubTask;
import andrewkomarow.model.Task;
import andrewkomarow.service.Managers;
import andrewkomarow.service.TaskManager;

public class Main {
    public static void main(String[] args) {

        TaskManager manager = Managers.getDefaults();
        Task task = manager.createTask(new Task("Task", "description"));
        Task task2 = manager.createTask(new Task("Task", "description"));
        Task task3 = manager.createTask(new Task("Task", "description"));
        Task task4 = manager.createTask(new Task("Task", "description"));
        Task task5 = manager.createTask(new Task("Task", "description"));
        Task task6 = manager.createTask(new Task("Task", "description"));
        Task task7 = manager.createTask(new Task("Task", "description"));
        Task task8 = manager.createTask(new Task("Task", "description"));
        manager.removeTaskId(1);

        System.out.println(manager.getTaskId(1));







        Epic epic = manager.createEpic(new Epic("Epic", "description"));
        SubTask subTask = manager.createSubtask(epic, new SubTask("Subtask","description"));
        SubTask subTask1 = manager.createSubtask(epic, new SubTask("subTask1","Description1"));
        SubTask newSubTask = new SubTask("NewSubTask", "NewDescription");



       /* System.out.println(manager.getEpicId(epic.getId()).getEpicSubtasks().toString());
        manager.updateSubTask(subTask.getId(), newSubTask);

        System.out.println(manager.getEpicId(epic.getId()).getEpicSubtasks().toString());

        subTask1.setStatus(Status.DONE);

        System.out.println(manager.getEpicId(epic.getId()));*/

    }
}
