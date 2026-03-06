package orion.ui;

import orion.storage.TaskList;
import orion.task.Task;

import java.util.ArrayList;

public class Ui {
    private static final String LINE = "____________________________________________________________";

    public void showDeleted(Task removed, int taskCount) {
        System.out.println(LINE);
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + removed);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
        System.out.println();
    }

    public void showList(TaskList taskList) {
        System.out.println(LINE);

        if (taskList.size() == 0) {
            System.out.println(" (no tasks yet)");
            System.out.println(LINE);
            System.out.println();
            return;
        }

        System.out.println(" Here are the tasks in your list:");
        ArrayList<Task> tasks = taskList.getAll();
        for (int i = 0; i < taskList.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }

        System.out.println(LINE);
        System.out.println();
    }

    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Orion");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);
        System.out.println();
    }

    public void showGoodbye() {
        printBoxed("Bye. Hope to see you again soon!");
    }

    public void showError(String message) {
        printBoxed("OOPS!!! " + message);
    }

    public void showAdded(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
        System.out.println();
    }

    public void showMarkResult(boolean markAsDone, Task task) {
        String header = markAsDone
                ? "Nice! I've marked this task as done:"
                : "OK, I've marked this task as not done yet:";

        System.out.println(LINE);
        System.out.println(" " + header);
        System.out.println("   " + task);
        System.out.println(LINE);
        System.out.println();
    }

    public void showFindResults(ArrayList<Task> matches) {
        System.out.println(LINE);

        if (matches.isEmpty()) {
            System.out.println(" No matching tasks found.");
            System.out.println(LINE);
            System.out.println();
            return;
        }

        System.out.println(" Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println(" " + (i + 1) + "." + matches.get(i));
        }

        System.out.println(LINE);
        System.out.println();
    }

    private void printBoxed(String message) {
        System.out.println(LINE);
        for (String line : message.split("\n")) {
            System.out.println(" " + line);
        }
        System.out.println(LINE);
        System.out.println();
    }
}
