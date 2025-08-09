package andrewkomarow;

import andrewkomarow.service.FileBackedTaskManager;
import andrewkomarow.service.TaskManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        /*String str = "file.CSV";
        readFile(str);*/
        Path path = Paths.get("resources/test.CSV");
        System.out.println("Ищем файл по пути: " + path.toAbsolutePath());
        TaskManager manager = FileBackedTaskManager.loadFromFile(path);
        System.out.println(manager.getSubtasks());
    }

  /*  public static void readFile(String str) {
        ClassLoader cl = Main.class.getClassLoader();
        InputStream is = cl.getResourceAsStream("file.CSV");
        if (is == null) {
            throw new IllegalArgumentException("Файл не найден");
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8))) {
            String line;
            while(br.ready()) {
                line = br.readLine();
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
