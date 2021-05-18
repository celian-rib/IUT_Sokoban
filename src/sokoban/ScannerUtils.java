package sokoban;

import java.util.Scanner;

public class ScannerUtils {

    public static String awaitString(String prefix) {
        System.out.print(prefix);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        if (input.length() == 0)
            return awaitString(prefix);
        return input;
    }

    public static int awaitInt(String prefix) {
        System.out.print(prefix);
        Scanner scanner = new Scanner(System.in);
        String inputString = scanner.nextLine().trim().toUpperCase();

        try {
            int inputInt = Integer.parseInt(inputString);
            return inputInt;
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
            return awaitInt(prefix);
        }
    }

    public static int awaitIntInRange(String prefix, int min, int max) {
        int inputInt = awaitInt(prefix);
        if (inputInt >= min && inputInt <= max)
            return inputInt;
        System.out.println("Please enter a number between " + min + " & " + max);
        return awaitIntInRange(prefix, min, max);
    }

}
