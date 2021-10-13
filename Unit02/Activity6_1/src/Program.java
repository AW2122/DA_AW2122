import java.io.*;


public class Program {
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream objFile = null;
        File file = new File("contacts.obj");

        try {
            objFile = new ObjectOutputStream(new FileOutputStream("contacts.obj"));

            if (file.exists()) {

                objFile.writeInt(0);
                Contacts cont1 = new Contacts("Alex", "W", "aw@g.c", "90909",
                        "Hello");
                objFile.writeObject(cont1);
                objFile.flush();
                objFile.close();
                ObjectInputStream objectFile = new ObjectInputStream(
                        new FileInputStream( new File("contacts.obj") ));
                int numObjects = objectFile.readInt();
                for ( ; numObjects > 0 ; numObjects-- ) {
                    cont1 = (Contacts) objectFile.readObject();
                    cont1.toString();
                }
            }
        }
        catch (Exception e) {

        }

    }
}
