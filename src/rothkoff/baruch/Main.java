package rothkoff.baruch;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static MemoryTree memory = new MemoryTree(16);
    private static LinkedList<Process> processes = new LinkedList<>();

    public static void main(String[] args) {
        var menuSelect = 0;
        do {
            menuSelect = printMenu();
            switch (menuSelect) {
                case 1:
                    enterProcess();
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        } while (menuSelect != 4);
    }

    private static void enterProcess() {
        System.out.print("Enter process ID: ");
        var id = scanner.nextLine();
        System.out.print("Enter process size in bytes: ");
        var size = scanner.nextInt();
        var process = new Process(id, size);
        if (memory.allocate(process)) {
            processes.add(process);
            System.out.println(process.toString());
        }
    }

    private static int printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Enter process");
        System.out.println("2. Exit process");
        System.out.println("3. Print status");
        System.out.println("4. Exit");
        System.out.print("Your choice: ");
        return scanner.nextInt();
    }
}
