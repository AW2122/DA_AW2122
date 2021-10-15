import java.io.*;
import java.util.ArrayList;

public class ContactList extends ArrayList<Contacts> {
    public void ReadContactFile(String fileName) throws IOException, ClassNotFoundException {
        File file = new File(fileName);

        if (file.exists()) {
            ObjectInputStream objFile = new ObjectInputStream(new FileInputStream(file));
            int numObjects = objFile.readInt();
            for (; numObjects > 0; numObjects--) {
                add((Contacts) objFile.readObject());
            }
        }
    }
    public void WriteContactFile(String fileName) throws IOException {
        File file = new File(fileName);

        if (file.exists()) {
            ObjectOutputStream objFile = new ObjectOutputStream(new FileOutputStream(file));
            objFile.writeInt(size());
            for (Contacts c : this) {
                objFile.writeObject(c);
            }
        }
    }
}
