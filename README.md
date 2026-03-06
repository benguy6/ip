Orion User Guide

Orion is a lightweight task-tracking chatbot that helps you manage todos, deadlines, and events using simple text commands.
Your tasks are saved automatically and loaded again the next time you start Orion.

Quick Start

Download the latest JAR file, for example ip.jar, from your GitHub Release.

Copy the JAR file into an empty folder.

Open a terminal in that folder.

Run the following command:

java -jar ip.jar

Type your commands and press Enter to interact with Orion.

Features
1. Adding a Todo

Adds a task without any date or time attached.

Format:
todo <description>

Example:
todo borrow book

2. Adding a Deadline

Adds a task that must be completed by a specific date.

Format:
deadline <description> /by <yyyy-mm-dd>

Example:
deadline return book /by 2019-10-15

Date rules:

Orion only accepts dates in the format yyyy-mm-dd

Example of a valid date: 2019-10-15

If the date format is invalid, Orion will show an error message explaining the correct format

3. Adding an Event

Adds an event task with a start and end time.

Format:
event <description> /from <start> /to <end>

Example:
event project meeting /from Mon 2pm /to 4pm

Note:
At this stage, the start and end fields are stored as text.

4. Listing All Tasks

Displays all tasks currently stored in Orion.

Format:
list

5. Marking a Task as Done

Marks a task as completed.

Format:
mark <task number>

Example:
mark 2

6. Unmarking a Task

Marks a completed task as not done.

Format:
unmark <task number>

Example:
unmark 2

7. Deleting a Task

Deletes a task from the list.

Format:
delete <task number>

Example:
delete 3

8. Finding Tasks

Searches for tasks whose descriptions contain a given keyword.

Format:
find <keyword>

Example:
find book

9. Exiting Orion

Closes the application.

Format:
bye

Command Summary
Action	Command Format	Example
Add todo	todo <description>	todo borrow book
Add deadline	deadline <description> /by <yyyy-mm-dd>	deadline return book /by 2019-10-15
Add event	event <description> /from <start> /to <end>	event project meeting /from Mon 2pm /to 4pm
List tasks	list	list
Mark task	mark <task number>	mark 2
Unmark task	unmark <task number>	unmark 2
Delete task	delete <task number>	delete 3
Find task	find <keyword>	find book
Exit	bye	bye
Notes

Task numbers are 1-based. This means the first task is task 1.

Deadline dates must follow the format yyyy-mm-dd.

Orion saves your tasks automatically whenever the task list changes.

Saving Data

Orion automatically saves your tasks whenever you:

add a task

delete a task

mark a task

unmark a task

When Orion starts again, it will load the saved tasks from disk.

Data file location:
data/orion.txt

This path is relative to the folder where you run the JAR file.

If you run Orion in a new empty folder for the first time, the data/ folder will be created automatically.

Error Handling

Orion handles common user errors, including:

empty commands

unknown commands

missing task descriptions

invalid task numbers

invalid deadline date formats

incomplete command formats

Examples of invalid input:

todo

mark x

delete 999

deadline return book /by tomorrow

For such cases, Orion shows an error message to guide you toward the correct format.

FAQ

Q: Where is my data stored?
A: Your data is stored in data/orion.txt relative to the folder where you run Orion.

Q: Can I edit the data file manually?
A: Yes, but you should keep the format consistent so Orion can read it properly.

Q: Do I need internet to use Orion?
A: No. Orion runs completely locally on your computer.

Example Usage

todo borrow book
deadline submit assignment /by 2026-03-10
event team meeting /from Mon 2pm /to 4pm
list
mark 2
find book
delete 1
bye
