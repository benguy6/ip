package orion;

import orion.storage.TaskList;
import orion.task.Deadline;
import orion.task.Event;
import orion.task.Task;
import orion.task.Todo;
import orion.ui.Ui;

import java.util.Scanner;

public class Orion {
    private static final String COMMAND_BYE = "bye";
    private static final String COMMAND_LIST = "list";
    private static final String PREFIX_MARK = "mark ";
    private static final String PREFIX_UNMARK = "unmark ";
    private static final String PREFIX_TODO = "todo ";
    private static final String PREFIX_DEADLINE = "deadline ";
    private static final String PREFIX_EVENT = "event ";

    private static final String HELP_TODO =
            "The description of a todo cannot be empty.\n"
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

    private static final Ui ui = new Ui();
    private static final TaskList taskList = new TaskList();

    public static void main(String[] args) {
        ui.showWelcome();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            try {
                boolean shouldContinue = processInput(input);
                if (!shouldContinue) {
                    break;
                }
            } catch (OrionException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    private static boolean processInput(String input) throws OrionException {
        if (input.isEmpty()) {
            throw new OrionException("Please enter a command.");
        }

        if (input.equals(COMMAND_BYE)) {
            ui.showGoodbye();
            return false;
        }

        if (input.equals(COMMAND_LIST)) {
            ui.showList(taskList);
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

    private static void handleAddCommands(String input) throws OrionException {
        if (input.equals("todo")) {
            throw new OrionException(HELP_TODO);
        }

        if (input.startsWith(PREFIX_TODO)) {
            String desc = input.substring(PREFIX_TODO.length()).trim();
            if (desc.isEmpty()) {
                throw new OrionException(HELP_TODO);
            }
            addTask(new Todo(desc));
            return;
        }

        if (input.startsWith(PREFIX_DEADLINE)) {
            addTask(parseDeadline(input));
            return;
        }

        if (input.startsWith(PREFIX_EVENT)) {
            addTask(parseEvent(input));
            return;
        }

        throw new OrionException("I'm sorry, but I don't know what that means :-(");
    }

    private static Task parseDeadline(String input) throws OrionException {
        String rest = input.substring(PREFIX_DEADLINE.length()).trim();
        String[] parts = rest.split(" /by ", 2);
        if (parts.length < 2) {
            throw new OrionException(HELP_DEADLINE);
        }

        String desc = parts[0].trim();
        String by = parts[1].trim();

        if (desc.isEmpty() || by.isEmpty()) {
            throw new OrionException(HELP_DEADLINE);
        }

        return new Deadline(desc, by);
    }

    private static Task parseEvent(String input) throws OrionException {
        String rest = input.substring(PREFIX_EVENT.length()).trim();
        String[] p1 = rest.split(" /from ", 2);
        if (p1.length < 2) {
            throw new OrionException(HELP_EVENT);
        }

        String desc = p1[0].trim();
        String[] p2 = p1[1].split(" /to ", 2);
        if (p2.length < 2) {
            throw new OrionException(HELP_EVENT);
        }

        String from = p2[0].trim();
        String to = p2[1].trim();

        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new OrionException(HELP_EVENT);
        }

        return new Event(desc, from, to);
    }

    private static void addTask(Task task) throws OrionException {
        if (taskList.isFull()) {
            throw new OrionException("Sorry, I can only store up to 100 tasks.");
        }

        taskList.add(task);
        ui.showAdded(task, taskList.size());
    }

    private static void handleMarkOrUnmark(String input, boolean markAsDone) throws OrionException {
        Integer taskNumber = parseTaskNumber(input);
        if (taskNumber == null) {
            String example = markAsDone ? "mark 2" : "unmark 2";
            throw new OrionException("Please specify a valid task number.\nTry: " + example);
        }

        Task task = taskList.get(taskNumber);
        if (task == null) {
            throw new OrionException("Task number " + taskNumber + " does not exist.");
        }

        if (markAsDone) {
            task.markDone();
        } else {
            task.markNotDone();
        }

        ui.showMarkResult(markAsDone, task);
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
}
