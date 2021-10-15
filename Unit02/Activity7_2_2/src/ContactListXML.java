import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ContactListXML extends ContactList {
    public void ConvertObjectToXML(String file) throws IOException {
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));

        printWriter.printf("<contactList>\n");

        for (int i = 0; i < this.size(); i++) {
            Contacts c = get(i);
            printWriter.printf("\t<contact id=\"%s\">\n", i);
            printWriter.printf("\t\t<name> %s </name> \n", c.getName());
            printWriter.printf("\t\t<surname> %s </surname> \n", c.getSurname());
            printWriter.printf("\t\t<email> %s </email> \n", c.getEmail());
            printWriter.printf("\t\t<phone> %s </phone> \n", c.getPhoneNumber());
            printWriter.printf("\t\t<description> %s </description> \n", c.getDescription());
            printWriter.printf("\t</contact>\n");
        }

        printWriter.printf("</contactList>");

        printWriter.flush();
        printWriter.close();
    }
}
