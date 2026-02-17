package orion.storage;

import orion.OrionException;
import orion.task.Deadline;
import orion.task.Event;
import orion.task.Task;
import orion.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private static final String DATA_DIR = "data";
    private static final String FILE_PATH = "data/orion.txt";

    public ArrayList<Task> load() throws OrionException {
        ensureDataDirExists();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        ArrayList<Task> loaded = new ArrayList<>();
        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                Task t = parseLine(line);
                if (t != null) {
                    loaded.add(t);
                }
            }
        } catch (IOException e) {
            throw new OrionException("Could not load saved tasks.");
        }

        return loaded;
    }

    public void save(TaskList taskList) throws OrionException {
        ensureDataDirExists();

        try (FileWriter fw = new FileWriter(FILE_PATH)) {
            Task[] tasks = taskList.getAll().toArray(new Task[0]);
            for (int i = 0; i < taskList.size(); i++) {
                fw.write(encodeTask(tasks[i]));
                fw.write(System.lineSeparator());
            }
        } catch (IOException e) {
            throw new OrionException("Could not save tasks.");
        }
    }

    private void ensureDataDirExists() throws OrionException {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            boolean ok = dir.mkdirs();
            if (!ok) {
                throw new OrionException("Could not create data folder.");
            }
        }
    }

    private Task parseLine(String line) {
        String[] p = line.split("\\s*\\|\\s*");
        if (p.length < 3) {
            return null;
        }

        String type = p[0];
        boolean done = "1".equals(p[1]);
        String desc = p[2];

        Task t;
        switch (type) {
            case "T":
                t = new Todo(desc);
                break;
            case "D":
                if (p.length < 4) return null;
                t = new Deadline(desc, p[3]);
                break;
            case "E":
                if (p.length < 5) return null;
                t = new Event(desc, p[3], p[4]);
                break;
            default:
                return null;
        }

        if (done) {
            t.markDone();
        }
        return t;
    }

    private String encodeTask(Task t) {
        String done = t.isDone() ? "1" : "0";

        if (t instanceof Todo) {
            return "T | " + done + " | " + t.getDescription();
        }
        if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return "D | " + done + " | " + d.getDescription() + " | " + d.getBy();
        }
        if (t instanceof Event) {
            Event e = (Event) t;
            return "E | " + done + " | " + e.getDescription() + " | " + e.getFrom() + " | " + e.getTo();
        }
        return "T | " + done + " | " + t.getDescription();
    }
}
