//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

package sokoban.utils;

import java.util.Scanner;

public class ScannerUtils {

    /**
     * Wait for the user to enter any string in the terminal
     * 
     * @param prefix text prefix to display on the same line as the user input 
     *              (Eg : "Enter your name : ")
     * @return the string from the user
     */
    public static String awaitString(String prefix) {
        System.out.print(prefix);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        if (input.length() == 0)
            return awaitString(prefix);
        return input;
    }

    /**
     * Wait for the user to enter any integer in the terminal
     * 
     * @param prefix text prefix to display on the same line as the user input 
     *              (Eg : "Enter your age : ")
     * @return the int from the user
     */
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

    /**
     * Wait for the user to enter any integer in the terminal
     * If the int is not contained in a specific range, the user is reasked to
     * enter an input
     * 
     * @param prefix text prefix to display on the same line as the user input 
     *              (Eg : "Enter your choice (1 to 10) : ")
     * @param min minimum value accepted (Inclusive)
     * @param max maximum value accepted (Inclusive)
     * @return the int from the user
     */
    public static int awaitIntInRange(String prefix, int min, int max) {
        int inputInt = awaitInt(prefix);
        if (inputInt >= min && inputInt <= max)
            return inputInt;
        System.out.println("Please enter a number between " + min + " & " + max);
        return awaitIntInRange(prefix, min, max);
    }

}
