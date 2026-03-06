/**
 * Represents a parsed user command produced by {@link Parser}.
 * Stores the command type and any associated arguments (e.g., index, description, dates).
 */

package orion.parser;

import java.time.LocalDate;

public class ParsedCommand {

    public enum Type {
        BYE, LIST,
        TODO, DEADLINE, EVENT,
        MARK, UNMARK, DELETE,
        FIND
    }

    public final Type type;

    public final String description;
    public final LocalDate by;     // for DEADLINE
    public final String from;      // for EVENT
    public final String to;        // for EVENT
    public final String keyword;   // for FIND
    public final Integer index;    // for MARK/UNMARK/DELETE

    private ParsedCommand(Type type, String description, LocalDate by, String from, String to, String keyword, Integer index) {
        this.type = type;
        this.description = description;
        this.by = by;
        this.from = from;
        this.to = to;
        this.keyword = keyword;
        this.index = index;
    }

    public static ParsedCommand bye() {
        return new ParsedCommand(Type.BYE, null, null, null, null, null, null);
    }

    public static ParsedCommand list() {
        return new ParsedCommand(Type.LIST, null, null, null, null, null, null);
    }

    public static ParsedCommand todo(String desc) {
        return new ParsedCommand(Type.TODO, desc, null, null, null, null, null);
    }

    public static ParsedCommand deadline(String desc, LocalDate by) {
        return new ParsedCommand(Type.DEADLINE, desc, by, null, null, null, null);
    }

    public static ParsedCommand event(String desc, String from, String to) {
        return new ParsedCommand(Type.EVENT, desc, null, from, to, null, null);
    }

    public static ParsedCommand mark(int idx) {
        return new ParsedCommand(Type.MARK, null, null, null, null, null, idx);
    }

    public static ParsedCommand unmark(int idx) {
        return new ParsedCommand(Type.UNMARK, null, null, null, null, null, idx);
    }

    public static ParsedCommand delete(int idx) {
        return new ParsedCommand(Type.DELETE, null, null, null, null, null, idx);
    }

    public static ParsedCommand find(String keyword) {
        return new ParsedCommand(Type.FIND, null, null, null, null, keyword, null);
    }
}