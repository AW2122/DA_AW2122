import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Program {
    protected static Scanner sc = new Scanner(System.in);
    protected static ContactList contactList;
    protected static final String fileName = "contacts.obj";

    protected static int Menu() {
        String input;
        System.out.println(">>> MENU <<<");
        System.out.println();
        System.out.println("[1] Add a contact");
        System.out.println("[2] Show contact list");
        System.out.println("[3] Search contact list");
        System.out.println("[0] Exit");

         do {
            System.out.println("Choose an option");
            input = sc.nextLine();
        } while (!input.matches("[0-3]"));
        return Integer.parseInt(input);
    }

    public static Contacts newContact() {
        Contacts contact = new Contacts();
        System.out.println("Enter the contact's name: ");
        contact.setName(sc.nextLine());
        System.out.println("Enter the contact's surname: ");
        contact.setSurname(sc.nextLine());
        System.out.println("Enter the contact's e-mail: ");
        contact.setEmail(sc.nextLine());
        System.out.println("Enter the contact's phone number: ");
        contact.setPhoneNumber(sc.nextLine());
        System.out.println("Enter the contact's description: ");
        contact.setDescription(sc.nextLine());

        return contact;
    }

    public static void showContact(Contacts contact) {
        System.out.println("Name: ");
        System.out.println(contact.getName());
        System.out.println("Surname: ");
        System.out.println(contact.getSurname());
        System.out.println("e-mail: ");
        System.out.println(contact.getEmail());
        System.out.println("Phone: ");
        System.out.println(contact.getPhoneNumber());
        System.out.println("Description: ");
        System.out.println(contact.getDescription());
    }
    public static Contacts searchContact() {
        String searchType;
        do {
            System.out.println("Do you wish to search by [N]ame or by [P]hone number?");
            searchType = sc.nextLine().toLowerCase();
        } while (!searchType.matches("[np]"));

        System.out.println("Please type in the search criteria: ");
        String searchWord = sc.nextLine();

        for (Contacts c : contactList) {
            if (searchType.equalsIgnoreCase("n")) {
                if (searchWord.equalsIgnoreCase(c.fullName())) {
                    System.out.println(c.fullName());

                    return c;
                }
            }
            if (searchType.equalsIgnoreCase("p")) {
                if (searchWord == c.getPhoneNumber()) {
                    return c;
                }
            }
        }
        return null;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        contactList = new ContactList();

        try {
            contactList.ReadContactFile(fileName);

            int option = Menu();
            while (option != 0) {
                switch (option) {
                    case 1:
                        contactList.add(newContact());
                        break;
                    case 2:
                        for (Contacts c : contactList) {
                            showContact(c);
                        }
                        break;
                    case 3:
                        Contacts c = searchContact();
                        if (c != null)
                            showContact(c);
                        else
                            System.out.println("The contact doesn't exist.");
                        break;
                    case 0:
                        break;
                }
                option = Menu();
            }
            contactList.WriteContactFile(fileName);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Something went wrong.");
        }

    }
}
