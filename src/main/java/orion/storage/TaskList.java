package orion.storage;

import orion.task.Task;

public class TaskList {
    private static final int MAX_TASKS = 100;

    private final Task[] tasks = new Task[MAX_TASKS];
    private int size = 0;

    public int size() {
        return size;
    }

    public boolean isFull() {
        return size >= MAX_TASKS;
    }

    public void add(Task task) {
        tasks[size] = task;
        size++;
    }

    public Task get(int oneBasedIndex) {
        int idx = oneBasedIndex - 1;
        if (idx < 0 || idx >= size) {
            return null;
        }
        return tasks[idx];
    }

    public Task[] getAllTasks() {
        return tasks;
    }
}
