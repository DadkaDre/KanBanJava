package AndrewKomarov.service;

import AndrewKomarov.model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();
}
