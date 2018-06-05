package rothkoff.baruch;

import jdk.jshell.spi.ExecutionControl;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static MemoryTree memory;
    private static LinkedList<Process> processes = new LinkedList<>();

    public static void main(String[] args) {
        int memorySize;
        do {
            System.out.print("Enter memory size, must to be power of 2: ");
            memorySize = Integer.parseInt(scanner.nextLine());
        } while ((Math.log(memorySize) / Math.log(2)) % 1 != 0);

        memory = new MemoryTree(memorySize);

        int menuSelect;
        do {
            menuSelect = printMenu();
            switch (menuSelect) {
                case 1:
                    enterProcess();
                    break;
                case 2:
                    freeProcess();
                    break;
                case 3:
                    printDetails();
                    break;
            }
        } while (menuSelect != 4);
    }

    private static void printDetails() {
        System.out.println("Processes:");
        processes.forEach(System.out::println);
        System.out.println("Free Blocks: ");
        memory.printFreeBlocks();
        System.out.print("Sum of Internal Fragmentation: ");
        System.out.println(processes.stream().mapToInt(Process::getInternalFragmentation).sum());
    }

    private static void freeProcess() {
        System.out.print("Enter process id to release: ");
        String id = scanner.nextLine();
       var process = processes.stream().filter(p -> p.getId().equals(id)).findFirst();
       if(process.isPresent()){
           process.get().release();
           if(processes.remove(process.get())){
               System.out.println("The process released");
           }
       }else {
           System.err.println("The process not exist");
       }
    }

    private static void enterProcess() {
        System.out.print("Enter process ID: ");
        var id = scanner.nextLine();
        int size;
        do {
            System.out.print("Enter process size in bytes: ");
            size = Integer.parseInt(scanner.nextLine());
        } while (size >= memory.getSize());
        var process = new Process(id, size);
        if (memory.allocate(process)) {
            processes.add(process);
            System.out.println(process.toString());
        } else {
            System.err.println("External Fragmentation is " + memory.externalFragmentation());
        }
    }

    private static int printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Enter process");
        System.out.println("2. Exit process");
        System.out.println("3. Print status");
        System.out.println("4. Exit");
        System.out.print("Your choice: ");
        return Integer.parseInt(scanner.nextLine());
    }
}
