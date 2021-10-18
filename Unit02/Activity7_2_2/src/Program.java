import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class Program {
    protected static Scanner sc;
    protected static String xmlFileName;
    protected static String objFileName;

    public static int Menu() {
        String input = "";
        System.out.println(">>> MENU <<<");
        System.out.println();
        System.out.println("[1] Convert .obj file to .xml file.");
        System.out.println("[2] Convert .xml file to .obj file.");
        System.out.println("[0] Exit.");
        System.out.println();

        while (!input.matches("[0-2]")) {
            System.out.println("Choose an option [0-2]: ");
            input = sc.nextLine();
        }
        return Integer.parseInt(input);
    }
    public static void main(String[] args) {
        ContactListXML list = new ContactListXML();
        sc = new Scanner(System.in);
        int option;

        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            myXMLContactsHandler myXML = new myXMLContactsHandler();

            do {
                option = Menu();

                switch (option) {
                    case 1:
                        System.out.println("Please insert the .obj file name to read: ");
                        list.ReadContactFile(objFileName = sc.nextLine());
                        System.out.println("Please insert the .xml file name to create: ");
                        list.ReadContactFile(xmlFileName = sc.nextLine());
                        list.ConvertObjectToXML(xmlFileName);
                        break;
                    case 2:
                        System.out.println("Please insert the .xml file name: ");
                        File xmlFile = new File(xmlFileName = sc.nextLine());
                        saxParser.parse(xmlFile, myXML);
                        System.out.println("Please insert the .obj file name: ");
                        myXML.getContacts().WriteContactFile(objFileName = sc.nextLine());
                        break;
                    case 0:
                        break;
                }
            } while (option != 0);

        } catch (Exception e) {

        }


    }
}
