import java.util.Scanner;

public class Orion {
    private static final String LINE = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String PREFIX_MARK = "mark ";
    private static final String PREFIX_UNMARK = "unmark ";
    private static final String PREFIX_TODO = "todo ";
    private static final String PREFIX_DEADLINE = "deadline ";
    private static final String PREFIX_EVENT = "event ";

    private static final String HELP_TODO =
            "Your todo is missing a description.\n"
                    + "Try: todo <what to do>\n"
                    + "Example: todo borrow book";

    private static final String HELP_DEADLINE =
            "Your deadline format looks wrong.\n"
                    + "Try: deadline <what to do> /by <when>\n"
                    + "Example: deadline return book /by Sunday";

    private static final String HELP_EVENT =
            "Your event format looks wrong.\n"
                    + "Try: event <what> /from <start> /to <end>\n"
                    + "Example: event project meeting /from Mon 2pm /to 4pm";

    private static final Task[] tasks = new Task[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        greet();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            boolean shouldContinue = processInput(input);
            if (!shouldContinue) {
                break;
            }
        }
    }

    private static boolean processInput(String input) {
        if (input.isEmpty()) {
            printBoxed("Please enter a command.");
            return true;
        }

        if (input.equals(COMMAND_BYE)) {
            printBoxed("Bye. Hope to see you again soon!");
            return false;
        }

        if (input.equals(COMMAND_LIST)) {
            printList();
            return true;
        }

        if (input.startsWith(PREFIX_MARK)) {
            handleMarkOrUnmark(input, true);
            return true;
        }

        if (input.startsWith(PREFIX_UNMARK)) {
            handleMarkOrUnmark(input, false);
            return true;
        }

        handleAddCommands(input);
        return true;
    }

    private static void handleAddCommands(String input) {
        if (input.equals("todo")) {
            printBoxed(HELP_TODO);
            return;
        }

        if (input.startsWith(PREFIX_TODO)) {
            String desc = input.substring(PREFIX_TODO.length()).trim();
            if (desc.isEmpty()) {
                printBoxed(HELP_TODO);
                return;
            }
            addTask(new Todo(desc));
            return;
        }

        if (input.startsWith(PREFIX_DEADLINE)) {
            Task deadline = parseDeadline(input);
            if (deadline != null) {
                addTask(deadline);
            }
            return;
        }

        if (input.startsWith(PREFIX_EVENT)) {
            Task event = parseEvent(input);
            if (event != null) {
                addTask(event);
            }
            return;
        }

        printBoxed("I don't understand. Try: todo/deadline/event/list/mark/unmark/bye");
    }

    private static Task parseDeadline(String input) {
        String rest = input.substring(PREFIX_DEADLINE.length()).trim();
        String[] parts = rest.split(" /by ", 2);
        if (parts.length < 2) {
            printBoxed(HELP_DEADLINE);
            return null;
        }

        String desc = parts[0].trim();
        String by = parts[1].trim();

        if (desc.isEmpty() || by.isEmpty()) {
            printBoxed(HELP_DEADLINE);
            return null;
        }

        return new Deadline(desc, by);
    }

    private static Task parseEvent(String input) {
        String rest = input.substring(PREFIX_EVENT.length()).trim();
        String[] p1 = rest.split(" /from ", 2);
        if (p1.length < 2) {
            printBoxed(HELP_EVENT);
            return null;
        }

        String desc = p1[0].trim();
        String[] p2 = p1[1].split(" /to ", 2);
        if (p2.length < 2) {
            printBoxed(HELP_EVENT);
            return null;
        }

        String from = p2[0].trim();
        String to = p2[1].trim();

        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            printBoxed(HELP_EVENT);
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

    private static void handleMarkOrUnmark(String input, boolean markAsDone) {
        Integer taskNumber = parseTaskNumber(input);
        if (taskNumber == null) {
            String example = markAsDone ? "mark 2" : "unmark 2";
            printBoxed("Please specify a valid task number.\nTry: " + example);
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
        for (String line : message.split("\n")) {
            System.out.println(" " + line);
        }
        System.out.println(LINE);
        System.out.println();
    }
}
