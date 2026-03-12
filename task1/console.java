package task1;

import java.util.Scanner;

public class console {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the full class name (e.g., java.util.ArrayList): ");
        String className = scanner.nextLine();

        try {
            String result = engine.analyze(className);
            System.out.println("\nResult of analysis:\n");
            System.out.println(result);
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + e.getMessage());
        }
    }
}
