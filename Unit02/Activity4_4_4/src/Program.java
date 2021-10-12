import java.io.File;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Scanner fileRead = null;
        int lineNum = 1;
        String line;

        System.out.println("Please enter the name of the file: ");
        File file = new File(sc.nextLine());
        System.out.println("Please enter the word or sentence you would like to search: ");
        String searchWord = sc.nextLine();

        try {
            if (file.exists()) {
                fileRead = new Scanner(file);
                while (fileRead.hasNext()) {
                    line = fileRead.nextLine();
                    lineNum++;
                    if (line.toLowerCase().contains(searchWord)) {
                        System.out.println("Line nº" + lineNum + " - " + line);
                    }
                }
            }
        }
        catch (Exception e) {

        }
    }
}
