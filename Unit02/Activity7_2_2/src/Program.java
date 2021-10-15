import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class Program {
    protected static String xmlFileName = "contacts.xml";
    protected static String objFileName = "contacts.obj";

    public static void main(String[] args) {
        ContactListXML list = new ContactListXML();
        File xmlFile = new File(xmlFileName);
        File objFile = new File(objFileName);
        try {
            list.ReadContactFile(objFileName);
            list.ConvertObjectToXML(xmlFileName);

        } catch (IOException | ClassNotFoundException e) {

        }


    }
}
