import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Scanner scannerFile1 = null;
        Scanner scannerFile2 = null;
        String file1Line = null;
        String file2Line = null;
        System.out.println("Please enter the name of the first file: ");
        File file1 = new File(sc.nextLine());
        System.out.println("Please enter the name of the second file: ");
        File file2 = new File(sc.nextLine());
        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("sorted.txt")))) {
            if (file1.exists() && file2.exists()) {
                file1Line = scannerFile1.nextLine();
                file2Line = scannerFile2.nextLine();
                while (scannerFile1.hasNext() && scannerFile2.hasNext()) {
                    if (file1Line.compareTo(file2Line) > 0) {
                        printWriter.println(file2Line);
                        file2Line = scannerFile2.nextLine();
                    } else {
                        printWriter.println(file1Line);
                        file1Line = scannerFile1.nextLine();
                    }
                }
                if (scannerFile1.hasNext()){
                    printWriter.println(file1Line);
                    while (scannerFile1.hasNext()) {
                        printWriter.println(scannerFile1.nextLine());
                    }
                }
                if (scannerFile2.hasNext()){
                    printWriter.println(file2Line);
                    while (scannerFile2.hasNext()) {
                        printWriter.println(scannerFile2.nextLine());
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
