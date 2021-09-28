package Activity2;
import java.util.*;

public class Program2 {
    public static void main(String[] args) {
        String cont = "y";
        Scanner sc = new Scanner(System.in);
        System.out.println("Type in a day: ");
        int day = sc.nextInt();
        System.out.println("Type in a month: ");
        int month = sc.nextInt();
        System.out.println("Type in a year: ");
        int year = sc.nextInt();

        CheckDate d = new CheckDate();
        if (d.setDay(day)) {
            System.out.printf("The day entered is  %s ", day);
        }
        else {
            System.out.println("The day is out of range. ");
        }

        try {
            d.setMonth(month);
            System.out.printf("The month entered is %s ", month);
        }
        catch (IllegalArgumentException i) {
            System.out.println("Argument out of range.");
        }

        System.out.print(year);
        if (d.isLeapYear(year)) {
            System.out.println(" is a leap year.");
        }
        else {
            System.out.println(" is not a leap year.");
        }

    }
}
