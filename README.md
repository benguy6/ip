# Orion User Guide

Orion is a lightweight task-tracking chatbot that helps you manage **todos**, **deadlines**, and **events** using simple text commands.  
Your tasks are **saved automatically** and will be loaded the next time you start Orion.

---

## Quick Start

1. Download the latest JAR (e.g., `ip.jar`) from your GitHub Release.
2. Copy the JAR into an empty folder.
3. Open a terminal in that folder and run:


java -jar ip.jar


4. Type commands and press Enter.

---

## Command Summary

| Action | Command Format | Example |
|---|---|---|
| Add todo | `todo <description>` | `todo borrow book` |
| Add deadline | `deadline <description> /by <yyyy-mm-dd>` | `deadline return book /by 2019-10-15` |
| Add event | `event <description> /from <start> /to <end>` | `event project meeting /from Mon 2pm /to 4pm` |
| List tasks | `list` | `list` |
| Mark done | `mark <task number>` | `mark 2` |
| Unmark | `unmark <task number>` | `unmark 2` |
| Delete | `delete <task number>` | `delete 3` |
| Find | `find <keyword>` | `find book` |
| Exit | `bye` | `bye` |

**Notes**
- Task numbers are **1-based** (the first task is task 1).
- Deadlines must use the date format **`yyyy-mm-dd`** (e.g., `2019-10-15`).

---

## Features

### 1) Adding a Todo

Adds a task without any date/time.

**Format**

todo <description>


**Example**

todo borrow book


---

### 2) Adding a Deadline (Dates)

Adds a task that must be completed by a specific date.

**Format**

deadline <description> /by <yyyy-mm-dd>


**Example**

deadline return book /by 2019-10-15


**Date rules**
- Orion accepts dates in **`yyyy-mm-dd`** format only.
- If the date format is invalid, Orion shows an error message explaining the correct format.

---

### 3) Adding an Event

Adds an event task with a start and end time. (At this stage, event time fields are stored as text.)

**Format**

event <description> /from <start> /to <end>


**Example**

event project meeting /from Mon 2pm /to 4pm


---

### 4) Listing Tasks

Displays all tasks currently stored.

**Format**

list


---

### 5) Marking and Unmarking Tasks

Marks tasks as done or not done.

**Formats**

mark <task number>
unmark <task number>


**Examples**

mark 2
unmark 2


---

### 6) Deleting a Task

Removes a task from the list.

**Format**

delete <task number>


**Example**

delete 3


---

### 7) Finding Tasks by Keyword

Searches for tasks whose descriptions contain a keyword.

**Format**

find <keyword>


**Example**

find book


---

### 8) Exiting the App

Exits Orion.

**Format**

bye


---

## Automatic Saving (Data)

Orion saves tasks automatically whenever the task list changes (e.g., add, delete, mark/unmark).  
When Orion starts up, it loads existing tasks from disk if they exist.

**Where data is stored**
- `data/orion.txt` (relative to the folder you run the JAR from)

If you run Orion in a new empty folder for the first time, the `data/` folder will be created automatically.

---

## Error Handling

Orion handles common errors such as:
- Empty commands
- Unknown commands
- Invalid task numbers (e.g., `mark x`, `delete 999`)
- Invalid deadline date format (must be `yyyy-mm-dd`)
- Missing parts of a command (e.g., `todo` without a description)

---

## FAQ

**Q: Where is my data saved?**  
A: In `data/orion.txt` relative to the folder you run the app from.

**Q: Can I edit the data file manually?**  
A: Yes. The file is human-editable, but you should keep the format consistent (especially for deadlines).

**Q: Do I need internet to use Orion?**  
A: No. Orion runs locally.

---
