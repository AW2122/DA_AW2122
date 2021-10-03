import java.util.Scanner;

public class program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CheckDate d = new CheckDate();
        int day;
        int month;
        int year;
        try {
                System.out.println("Please enter a day (1-31): ");
                day = sc.nextInt();
                System.out.println("Please enter a month (1-12): ");
                month = sc.nextInt();
                System.out.println("Please enter a year: ");
                year = sc.nextInt();

                System.out.printf("The date entered is %s / %s / %s", day, month, year);
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.toString());
        }
    }
}
