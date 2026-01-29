import java.util.Scanner;

public class Orion {
    private static final String LINE = "____________________________________________________________";

    public static void main(String[] args) {
        greet();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                printBoxed("Bye. Hope to see you again soon!");
                break;
            }

            printBoxed(input);
        }
    }

    private static void greet() {
        System.out.println(LINE);
        System.out.println(" Hello! I'm Orion");
        System.out.println(" What can I do for you?");
        System.out.println(LINE);
    }

    private static void printBoxed(String message) {
        System.out.println(LINE);
        System.out.println(" " + message);
        System.out.println(LINE);
        System.out.println();
    }
}
