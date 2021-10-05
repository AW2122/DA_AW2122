import java.io.*;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.printf("Please enter the name of the file: ");
            PrintWriter printWriter  = null;
            boolean overwrite = false;
            String file = sc.nextLine();
            String input = sc.nextLine();


            if (new File(file).exists()) {
                System.out.println("The file exists. Would you like to overwrite it? Y/N ");
                printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file, overwrite)));
                input = sc.nextLine();
                if (input.equalsIgnoreCase("y")) {
                    overwrite = true;


                } else {
                    overwrite = false;

                }
            }
            else {
                System.out.println("File does not exist");
            }
        }
        catch (Exception e) {

        }

    }
}
