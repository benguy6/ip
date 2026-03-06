package orion;

import orion.parser.Parser;
import orion.parser.ParsedCommand;
import orion.storage.Storage;
import orion.storage.TaskList;
import orion.task.Deadline;
import orion.task.Event;
import orion.task.Task;
import orion.task.Todo;
import orion.ui.Ui;

import java.util.Scanner;

public class OrionApp {

    private final Ui ui;
    private final TaskList taskList;
    private final Storage storage;
    private final Parser parser;

    public OrionApp() {
        ui = new Ui();
        taskList = new TaskList();
        storage = new Storage();
        parser = new Parser();

        try {
            taskList.setAll(storage.load());
        } catch (OrionException e) {
            ui.showError(e.getMessage());
        }
    }

    public void run() {
        ui.showWelcome();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            try {
                ParsedCommand cmd = parser.parse(input);
                boolean shouldContinue = execute(cmd);
                if (!shouldContinue) {
                    break;
                }
            } catch (OrionException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    private boolean execute(ParsedCommand cmd) throws OrionException {
        switch (cmd.type) {
            case BYE:
                ui.showGoodbye();
                return false;

            case LIST:
                ui.showList(taskList);
                return true;

            case TODO:
                addTask(new Todo(cmd.description));
                return true;

            case DEADLINE:
                addTask(new Deadline(cmd.description, cmd.by));
                return true;

            case EVENT:
                addTask(new Event(cmd.description, cmd.from, cmd.to));
                return true;

            case MARK:
                mark(cmd.index, true);
                return true;

            case UNMARK:
                mark(cmd.index, false);
                return true;

            case DELETE:
                delete(cmd.index);
                return true;

            case FIND:
                ui.showFindResults(taskList.find(cmd.keyword));
                return true;

            default:
                throw new OrionException("I'm sorry, but I don't know what that means :-(");
        }
    }

    private void addTask(Task task) throws OrionException {
        if (taskList.isFull()) {
            throw new OrionException("Sorry, I can only store up to 100 tasks.");
        }

        taskList.add(task);
        storage.save(taskList);
        ui.showAdded(task, taskList.size());
    }

    private void delete(int oneBasedIndex) throws OrionException {
        Task removed = taskList.remove(oneBasedIndex);
        if (removed == null) {
            throw new OrionException("Task number " + oneBasedIndex + " does not exist.");
        }

        storage.save(taskList);
        ui.showDeleted(removed, taskList.size());
    }

    private void mark(int oneBasedIndex, boolean markAsDone) throws OrionException {
        Task task = taskList.get(oneBasedIndex);
        if (task == null) {
            throw new OrionException("Task number " + oneBasedIndex + " does not exist.");
        }

        if (markAsDone) {
            task.markDone();
        } else {
            task.markNotDone();
        }

        storage.save(taskList);
        ui.showMarkResult(markAsDone, task);
    }
}