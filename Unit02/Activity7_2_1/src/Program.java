import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;


class myXMLContactsHandler extends DefaultHandler {
    protected String currentTag;
    protected StringBuilder tagContent = new StringBuilder();
    protected boolean inPhone = false;
    protected String name;
    protected String homePhone;

    // Tag opening found
    //
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentTag = qName;
        tagContent.setLength(0);
        if (currentTag.equals("contact")) {
            System.out.println("ID: " + attributes.getValue("id"));
        }
        if (currentTag.equals("phones")) {
            inPhone = true;
        }
    }

    // Tag content, usually CDATA
    //
    public void characters(char[] ch, int start, int length) throws SAXException {
        tagContent.append(ch, start, length);
    }

    // Tag ending
    //
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!currentTag.isBlank()) {
            if (qName.equals("name"))
                name = tagContent.toString();
            if (qName.equals("surname"))
                System.out.println(" " + "Full name: " + name + " " + tagContent.toString());
            if (inPhone) {
                if (qName.equals("cell"))
                    System.out.println(" " + currentTag + " " + tagContent.toString());
                if (qName.equals("home"))
                    System.out.println(" " + currentTag + " " + tagContent.toString());
                if (qName.equals("work"))
                    System.out.println(" " + currentTag + " " + tagContent.toString());
            }
            if (qName.equals("phones"))
                inPhone = false;
        }
    }
}

public class Program {
    public static void main(String[] args) {
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse("contacts.xml", new myXMLContactsHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
