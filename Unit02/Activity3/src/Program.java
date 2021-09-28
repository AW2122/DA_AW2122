import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("Please enter a day (1-31): ");
            int day = sc.nextInt();
            System.out.println("Please enter a month (1-12): ");
            int month = sc.nextInt();
            System.out.println("Please enter a year: ");
            int year = sc.nextInt();

            CheckDate d = new CheckDate(day, month, year);
            System.out.printf("The date entered is %s / %s / %s", day, month, year);
        }
        catch (IllegalArgumentException i) {
            System.out.println("Out of range");
        }
    }
}
