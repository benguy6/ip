package orion.storage;

import orion.task.Task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    public int size() {
        return tasks.size();
    }

    public boolean isFull() {
        return false;
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int oneBasedIndex) {
        int idx = oneBasedIndex - 1;
        if (idx < 0 || idx >= tasks.size()) {
            return null;
        }
        return tasks.get(idx);
    }

    public Task remove(int oneBasedIndex) {
        int idx = oneBasedIndex - 1;
        if (idx < 0 || idx >= tasks.size()) {
            return null;
        }
        return tasks.remove(idx);
    }

    public ArrayList<Task> getAll() {
        return tasks;
    }
}
