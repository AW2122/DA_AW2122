package Activity1;

import java.util.*;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please, type in your name: ");
        String name = sc.nextLine();
        System.out.print("Now type in your surname: ");
        String surname = sc.nextLine();
        System.out.printf("Hello, %s %s.", name, surname);
    }
}
