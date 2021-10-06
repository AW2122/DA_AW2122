import java.io.*;
import java.util.Scanner;


public class Program {
    public static void LinePrinter() {

    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PrintWriter printWriter  = null;
        boolean overwrite = false;
        try {
            System.out.printf("Please enter the name of the file: ");
            String file = sc.nextLine();
            String input;

            if (new File(file).exists()) {
                printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, overwrite)));
                System.out.println("The file exists. Would you like to overwrite it? Y/N ");
                do {
                    input = sc.nextLine();
                } while (!input.matches("(?i)[YN]"));

                if (input.equalsIgnoreCase("y")) {
                    overwrite = true;
                    System.out.println("Type sentences to add to the file. Type \"exit\" + press Enter to finish. ");
                    do {
                        input = sc.nextLine();
                        if (!input.equalsIgnoreCase("exit")) {
                            printWriter.println(input);
                        }
                    } while (!input.equalsIgnoreCase("exit"));

                } else {
                    overwrite = false;

                }
            }
            else {
                System.out.println("File does not exist. Creating file...");
                FileOutputStream newFile = new FileOutputStream(file, false);
                printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
                System.out.println("Type sentences to add to the file. Type \"exit\" + press Enter to finish. ");
                do {
                    input = sc.nextLine();
                    if (!input.equalsIgnoreCase("exit")) {
                        printWriter.println(input);
                    }
                } while (!input.equalsIgnoreCase("exit"));
            }
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
