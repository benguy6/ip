import java.util.Scanner;

public class Orion {
    private static final String LINE = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        greet();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("bye")) {
                printBoxed("Bye. Hope to see you again soon!");
                break;
            }

            if (input.equals("list")) {
                printList();
                continue;
            }

            if (input.startsWith("mark ")) {
                handleMarkCommand(input, true);
                continue;
            }

            if (input.startsWith("unmark ")) {
                handleMarkCommand(input, false);
                continue;
            }

            addTask(input);
        }
    }

    private static void greet() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Orion");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);
        System.out.println();
    }

    private static void addTask(String taskText) {
        if (taskText.isEmpty()) {
            printBoxed("Please enter a task.");
            return;
        }

        if (taskCount >= MAX_TASKS) {
            printBoxed("Sorry, I can only store up to " + MAX_TASKS + " tasks.");
            return;
        }

        tasks[taskCount] = new Task(taskText);
        taskCount++;

        printBoxed("added: " + taskText);
    }

    private static void printList() {
        System.out.println(LINE);

        if (taskCount == 0) {
            System.out.println(" (no tasks yet)");
            System.out.println(LINE);
            System.out.println();
            return;
        }

        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println(" " + (i + 1) + "." + tasks[i]);
        }

        System.out.println(LINE);
        System.out.println();
    }

    private static void handleMarkCommand(String input, boolean markAsDone) {
        Integer taskNumber = parseTaskNumber(input);
        if (taskNumber == null) {
            printBoxed("Please specify a valid task number (e.g., mark 2).");
            return;
        }

        int index = taskNumber - 1;
        if (index < 0 || index >= taskCount) {
            printBoxed("Task number " + taskNumber + " does not exist.");
            return;
        }

        Task task = tasks[index];
        if (markAsDone) {
            task.markDone();
        } else {
            task.markNotDone();
        }

        String header = markAsDone
                ? "Nice! I've marked this task as done:"
                : "OK, I've marked this task as not done yet:";

        System.out.println(LINE);
        System.out.println(" " + header);
        System.out.println("   " + task);
        System.out.println(LINE);
        System.out.println();
    }

    private static Integer parseTaskNumber(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length != 2) {
            return null;
        }

        try {
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void printBoxed(String message) {
        System.out.println(LINE);
        System.out.println(" " + message);
        System.out.println(LINE);
        System.out.println();
    }
}
