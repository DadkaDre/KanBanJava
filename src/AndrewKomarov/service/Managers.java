package AndrewKomarov.service;

public class Managers {
    public static TaskManager getDefaults() {
        return new InMemoryTaskManager(new InMemoryHistoryManager());
    }
}
