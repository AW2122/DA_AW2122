import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.printf("Enter file: ");
            String file = sc.nextLine();
            String bytesInFile = new String(new FileInputStream(file).readAllBytes());

            System.out.printf("Enter search value: ");
            String input = sc.nextLine();

            int position = 0;
            int count = 0;

            for(int i = 0; i < bytesInFile.length(); i++){
                if (position == -1) {
                   break;
                }
                else {
                    position = bytesInFile.indexOf(input, (i + position));
                    if (position > -1) {
                        System.out.println("Index of occurrence " + (i + 1) + ": " + position);
                        count++;
                    }
                }
            }
            System.out.println("Number of occurrences: " + count);
        }
        catch (IOException e) {

        }

    } //D:\2_DAM\DA\ejercicio3_5.txt
}
