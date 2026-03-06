/**
 * Represents a task that must be completed by a specific date.
 */

package orion.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private static final DateTimeFormatter OUT_FMT = DateTimeFormatter.ofPattern("MMM dd yyyy");

    private final LocalDate by;

    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    public LocalDate getBy() {
        return by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUT_FMT) + ")";
    }
}