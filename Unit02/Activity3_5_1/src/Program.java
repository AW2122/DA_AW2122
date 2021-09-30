import java.io.FileInputStream;

public class Program {
    public static void main(String[] args) {
        byte[] buffer = new byte[4];
        try {
            FileInputStream image = new FileInputStream("D:\\\\Wallpapers\\4PdYNA3.jpeg");
            image.read(buffer, 0, 4);
            int i = 0;
            for (byte b:buffer) {
                if (( & 0xFF))
            }
        }
        catch (Exception e){

        }
    }
}
