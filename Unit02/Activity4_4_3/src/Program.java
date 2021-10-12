import java.io.File;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner scanFile = null;
        int counter = 1;

        System.out.println("Please type in the name of the file you would like to read: ");
        File file = new File(sc.nextLine());

        try {
            if (file.exists()) {
                scanFile = new Scanner(file);
                while (scanFile.hasNext()) {
                    System.out.println(counter + " " + scanFile.nextLine());
                    counter++;
                    if (counter == 24) {
                        System.out.println("");
                        System.out.println("Press 'Enter' to keep reading.");
                        sc.nextLine();
                        counter = 1;
                    }
                }
                System.out.println("Press 'Enter' to finish.");
                sc.nextLine();
            }
        } catch (Exception e) {

        } finally {
            if (scanFile != null)
            {
                scanFile.close();
            }
        }
    }
}
