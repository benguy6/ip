import java.util.Scanner;

public class Orion {
    private static final String LINE = "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    private static final String[] tasks = new String[MAX_TASKS];
    private static int taskCount = 0;

    public static void main(String[] args) {
        greet();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                printBoxed("Byeee! Hope to see you again soon!");
                break;
            }

            if (input.equals("list")) {
                printList();
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
        if (taskCount >= MAX_TASKS) {
            printBoxed("Sorry, I can only store up to " + MAX_TASKS + " tasks.");
            return;
        }

        tasks[taskCount] = taskText;
        taskCount++;

        printBoxed("added: " + taskText);
    }

    private static void printList() {
        System.out.println(LINE);

        if (taskCount == 0) {
            System.out.println(" (no tasks yet)");
        } else {
            for (int i = 0; i < taskCount; i++) {
                System.out.println(" " + (i + 1) + ". " + tasks[i]);
            }
        }

        System.out.println(LINE);
        System.out.println();
    }

    private static void printBoxed(String message) {
        System.out.println(LINE);
        System.out.println(" " + message);
        System.out.println(LINE);
        System.out.println();
    }
}
