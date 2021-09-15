import java.util.Scanner;

public class Exercise01 {
    public static void main(String[] args) {
        /* This program reads keyboard entered characters,
        saves them in a string, selects the highest and lowest numbers according to the ASCII table
        * It also stores the amount of characters that are upper case
        */

        Scanner sc = new Scanner(System.in);

        StringBuilder letters = new StringBuilder();
        int numLetters = 0;
        char minLetter = Character.MIN_VALUE;
        char maxLetter = Character.MAX_VALUE;
        int numUpper = 0;
        boolean exit = false;

        while (numLetters < 10 && !exit) {
            // Reads character.
            System.out.println("Enter a letter. Press 0 if you wish to exit: ");
            char auxLetter = sc.next().charAt(0);
            System.out.println();
            System.out.println("-------");
            letters.append(auxLetter);

            if (letters.toString().toCharArray()[numLetters] == '0') {
                exit = true;
            }

            // Saves the largest and smallest numbers.
            if (minLetter > auxLetter) {
                minLetter = auxLetter;
            }
            if (maxLetter < auxLetter) {
                maxLetter = auxLetter;
            }

            // Increments the counter.
            numLetters++;
        }
        if (numLetters > 50) {
            System.out.println("The string is full.");
        }

        // For every char in the string.
        for (int i = 0; i < numLetters && letters.toString().toCharArray()[i] != '0'; i++) {
            // If the letter is uppercase.
            if ((letters.toString().toCharArray()[i] >= 'A' && (letters.toString().toCharArray()[i] <= 'Z'))) {
                // Counts the uppercase letters.
                numUpper++;
            }
        }
        // Writes the results.
        System.out.println("The smallest Char is: " + (int)minLetter);
        System.out.println("The largest Char is: " + (int)maxLetter);
        System.out.println("There are " + numUpper + " uppercase letters.");
    }
}
