package andrewkomarow.service;

import java.nio.file.Paths;

public class Managers {
    public static TaskManager getDefaults() {
        return new FileBackedTaskManager(new InMemoryHistoryManager(), Paths.get("file.CSV"));
    }
}
