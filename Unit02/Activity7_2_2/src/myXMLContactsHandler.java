import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.ArrayList;

public class myXMLContactsHandler extends DefaultHandler {
    protected String currentTag;
    protected Contacts contact;
    protected boolean isName = false;
    protected boolean isSurname = false;
    protected boolean isEmail = false;
    protected boolean isPhone = false;
    protected boolean isDescription = false;
    protected int id;
    protected ContactList cList = new ContactList();

    public void startElement(String uri, String localName, String qName, Attributes attribute) throws SAXException {
        currentTag = qName;
        if (qName.equals("contact")) {
            contact = new Contacts();
            id = Integer.parseInt(attribute.getValue("id"));
        }

        switch (qName) {
            case "name":
                isName = true;
                break;
            case "surname":
                isSurname = true;
                break;
            case "email":
                isEmail = true;
                break;
            case "phone":
                isPhone = true;
                break;
            case "description":
                isDescription = true;
                break;
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (isName) {
            contact.setName(new String(ch, start, length));
            isName = false;
        }
        if (isSurname) {
            contact.setSurname(new String(ch, start, length));
            isSurname = false;
        }
        if (isEmail) {
            contact.setEmail(new String(ch, start, length));
            isEmail = false;
        }
        if (isPhone) {
            contact.setPhoneNumber(new String(ch, start, length));
            isPhone = false;
        }
        if (isDescription) {
            contact.setDescription(new String(ch, start, length));
            isDescription = false;
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("contact")) {
            cList.add(id, contact);
        }
    }

    public ContactList getContacts() {
        return cList;
    }
}
