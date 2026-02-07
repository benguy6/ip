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

            handleCommand(input);

        }
    }

    private static void handleCommand(String input) {
        if (input.isEmpty()) {
            printBoxed("Please enter a command.");
            return;
        }

        if (input.startsWith("todo ")) {
            String desc = input.substring(5).trim();
            addTask(new Todo(desc));
            return;
        }

        if (input.startsWith("deadline ")) {
            Task deadline = parseDeadline(input);
            if (deadline != null) {
                addTask(deadline);
            }
            return;
        }

        if (input.startsWith("event ")) {
            Task event = parseEvent(input);
            if (event != null) {
                addTask(event);
            }
            return;
        }

        printBoxed("I don't understand. Try: todo/deadline/event/list/mark/unmark/bye");
    }

    private static Task parseDeadline(String input) {
        String rest = input.substring(9).trim(); // after "deadline "
        String[] parts = rest.split(" /by ", 2);
        if (parts.length < 2) {
            printBoxed("Usage: deadline <desc> /by <by>");
            return null;
        }

        String desc = parts[0].trim();
        String by = parts[1].trim();

        if (desc.isEmpty() || by.isEmpty()) {
            printBoxed("Usage: deadline <desc> /by <by>");
            return null;
        }

        return new Deadline(desc, by);
    }

    private static Task parseEvent(String input) {
        String rest = input.substring(6).trim(); // after "event "
        String[] p1 = rest.split(" /from ", 2);
        if (p1.length < 2) {
            printBoxed("Usage: event <desc> /from <from> /to <to>");
            return null;
        }

        String desc = p1[0].trim();
        String[] p2 = p1[1].split(" /to ", 2);
        if (p2.length < 2) {
            printBoxed("Usage: event <desc> /from <from> /to <to>");
            return null;
        }

        String from = p2[0].trim();
        String to = p2[1].trim();

        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            printBoxed("Usage: event <desc> /from <from> /to <to>");
            return null;
        }

        return new Event(desc, from, to);
    }


    private static void greet() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Orion");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);
        System.out.println();
    }
    private static void addTask(Task task) {
        if (taskCount >= MAX_TASKS) {
            printBoxed("Sorry, I can only store up to " + MAX_TASKS + " tasks.");
            return;
        }

        tasks[taskCount] = task;
        taskCount++;

        System.out.println(LINE);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
        System.out.println();
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
