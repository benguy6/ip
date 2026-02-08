public class Ui {
    private static final String LINE = "____________________________________________________________";

    public void showWelcome(String name) {
        System.out.println(LINE);
        System.out.println(" Hello! I'm " + name);
        System.out.println(" What can I do for you?");
        System.out.println(LINE);
        System.out.println();
    }

    public void showGoodbye() {
        printBoxed("Bye. Hope to see you again soon!");
    }

    public void printBoxed(String message) {
        System.out.println(LINE);
        System.out.println(" " + message);
        System.out.println(LINE);
        System.out.println();
    }

    public void showAdded(Task task, int count) {
        System.out.println(LINE);
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + count + " tasks in the list.");
        System.out.println(LINE);
        System.out.println();
    }

    public void showMarkResult(boolean isMark, Task task) {
        String header = isMark
                ? "Nice! I've marked this task as done:"
                : "OK, I've marked this task as not done yet:";

        System.out.println(LINE);
        System.out.println(" " + header);
        System.out.println("   " + task);
        System.out.println(LINE);
        System.out.println();
    }
}
