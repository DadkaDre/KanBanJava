package AndrewKomarov;

import AndrewKomarov.model.Epic;
import AndrewKomarov.model.Status;
import AndrewKomarov.model.SubTask;
import AndrewKomarov.service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        Epic epic = manager.createEpic(new Epic("Epic", "description"));
        SubTask subTask = manager.createSubtask(epic, new SubTask("Subtask","description"));
        SubTask subTask1 = manager.createSubtask(epic, new SubTask("subTask1","Description1"));
        SubTask newSubTask = new SubTask("NewSubTask", "NewDescription");

        System.out.println(manager.getEpicId(epic.getId()).getEpicSubtasks().toString());
        manager.updateSubTask(subTask.getId(), newSubTask);

        System.out.println(manager.getEpicId(epic.getId()).getEpicSubtasks().toString());

        subTask1.setStatus(Status.DONE);

        System.out.println(manager.getEpicId(epic.getId()));

    }
}
