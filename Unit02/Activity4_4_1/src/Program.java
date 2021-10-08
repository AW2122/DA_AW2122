import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PrintWriter printWriter  = null;
        boolean overwrite;
        try {
            System.out.printf("Please enter the name of the file: ");
            String file = sc.nextLine();
            String input;
            long number = 0;

            if (new File(file).exists()) {
                System.out.println("The file exists. Would you like to overwrite it? Y/N ");
                do {
                    input = sc.nextLine();
                } while (!input.matches("(?i)[YN]"));

                overwrite = input.equalsIgnoreCase("N");
                printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, overwrite)));

                if (input.equalsIgnoreCase("n")) {
                    number = Files.lines(Paths.get(file)).count();
                }
            }
            else {
                System.out.println("File does not exist. Creating file...");
                FileOutputStream newFile = new FileOutputStream(file, false);
                printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            }
            System.out.println("Type sentences to add to the file. Type \"exit\" + press Enter to finish. ");
            do {
                input = sc.nextLine();
                if (!input.equalsIgnoreCase("exit")) {
                    printWriter.println(number + " " + input);
                    number++;
                }
            } while (!input.equalsIgnoreCase("exit"));
        }
        catch (Exception e) {

        }
        finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }

    }
}
