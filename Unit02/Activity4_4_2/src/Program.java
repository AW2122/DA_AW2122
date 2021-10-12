import java.io.*;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the first filename: ");
        File file1 = new File(sc.nextLine());
        System.out.println("Please enter the second filename: ");
        File file2 = new File(sc.nextLine());

        BufferedReader reader1 = null;
        BufferedReader reader2 = null;

        try (PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter("sorted.txt")))) {

            reader1 = new BufferedReader(new FileReader(file1));
            reader2 = new BufferedReader(new FileReader(file2));

            String textFile1;
            String textFile2;

            if (file1.exists() && file2.exists()) {
                textFile1 = reader1.readLine();
                textFile2 = reader2.readLine();

                if (textFile1 != null && textFile2 != null) {
                    while (textFile1 != null && textFile2 != null) {
                        if (textFile1.compareTo(textFile2) > 0) {
                            printWriter.println(textFile1);
                            textFile1 = reader1.readLine();
                        } else if (textFile1.compareTo(textFile2) < 0) {
                            printWriter.println(textFile2);
                            textFile2 = reader2.readLine();
                        } else if (textFile1.compareTo(textFile2) == 0) {
                            printWriter.println(textFile1);
                            textFile1 = reader1.readLine();

                            printWriter.println(textFile2);
                            textFile2 = reader2.readLine();
                        }
                    }
                }
                while (textFile1 != null || textFile2 != null) {
                    if (textFile1 == null) {
                        printWriter.println(textFile2);
                        textFile2 = reader2.readLine();
                    } else {
                        printWriter.println(textFile1);
                        textFile1 = reader1.readLine();
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (reader1 != null) {
                    reader1.close();
                }
                if (reader2 != null) {
                    reader2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
