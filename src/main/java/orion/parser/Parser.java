package orion.parser;

import orion.OrionException;

public class Parser {

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

    public ParsedCommand parse(String input) throws OrionException {
        String trimmed = input.trim();

        if (trimmed.isEmpty()) {
            throw new OrionException("Please enter a command.");
        }

        if (trimmed.equals("bye")) {
            return ParsedCommand.bye();
        }

        if (trimmed.equals("list")) {
            return ParsedCommand.list();
        }

        if (trimmed.equals("delete")) {
            throw new OrionException("Please specify which task to delete.\nTry: delete 3");
        }

        if (trimmed.startsWith("delete ")) {
            int idx = parseIndex(trimmed, "delete");
            return ParsedCommand.delete(idx);
        }

        if (trimmed.startsWith("mark ")) {
            int idx = parseIndex(trimmed, "mark");
            return ParsedCommand.mark(idx);
        }

        if (trimmed.startsWith("unmark ")) {
            int idx = parseIndex(trimmed, "unmark");
            return ParsedCommand.unmark(idx);
        }

        if (trimmed.equals("todo")) {
            throw new OrionException(HELP_TODO);
        }

        if (trimmed.startsWith("todo ")) {
            String desc = trimmed.substring(5).trim();
            if (desc.isEmpty()) {
                throw new OrionException(HELP_TODO);
            }
            return ParsedCommand.todo(desc);
        }

        if (trimmed.startsWith("deadline ")) {
            return parseDeadline(trimmed);
        }

        if (trimmed.startsWith("event ")) {
            return parseEvent(trimmed);
        }

        // Optional for Level-9 later (safe to include now)
        if (trimmed.equals("find")) {
            throw new OrionException("Please provide a keyword.\nTry: find book");
        }

        if (trimmed.startsWith("find ")) {
            String keyword = trimmed.substring(5).trim();
            if (keyword.isEmpty()) {
                throw new OrionException("Please provide a keyword.\nTry: find book");
            }
            return ParsedCommand.find(keyword);
        }

        throw new OrionException("I'm sorry, but I don't know what that means :-(");
    }

    private ParsedCommand parseDeadline(String input) throws OrionException {
        String rest = input.substring(9).trim(); // after "deadline "
        String[] parts = rest.split(" /by ", 2);
        if (parts.length < 2) {
            throw new OrionException(HELP_DEADLINE);
        }

        String desc = parts[0].trim();
        String by = parts[1].trim();
        if (desc.isEmpty() || by.isEmpty()) {
            throw new OrionException(HELP_DEADLINE);
        }

        return ParsedCommand.deadline(desc, by);
    }

    private ParsedCommand parseEvent(String input) throws OrionException {
        String rest = input.substring(6).trim(); // after "event "
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

        return ParsedCommand.event(desc, from, to);
    }

    private int parseIndex(String input, String commandWord) throws OrionException {
        String[] parts = input.split("\\s+");
        if (parts.length != 2) {
            throw new OrionException("Please specify a valid task number.\nTry: " + commandWord + " 2");
        }
        try {
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new OrionException("Please specify a valid task number.\nTry: " + commandWord + " 2");
        }
    }
}