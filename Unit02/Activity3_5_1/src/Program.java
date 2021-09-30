import java.io.FileInputStream;

public class Program {
    public static void main(String[] args) {
        byte[] buffer = new byte[6];
        try {
            FileInputStream image = new FileInputStream("D:\\\\Wallpapers\\4PdYNA3.jpeg");
            image.read(buffer);
            if ((buffer[0] & 0xFF) == 0xFF && (buffer[1] & 0xFF) == 0xD8 && (buffer[2] & 0xFF) == 0xFF && (buffer[3] & 0xFF) == 0xE0) {
                System.out.println("The image is a .JPEG");
            }
            if ((buffer[0] & 0xFF) == 0x42 && (buffer[1] & 0xFF) == 0x4D) {
                System.out.println("The image is a .BMP");
            }
            if ((buffer[0] & 0xFF) == 0x47 && (buffer[1] & 0xFF) == 0x49 && (buffer[2] & 0xFF) == 0x46 && (buffer[3] & 0xFF) == 0x46) {
                System.out.println("The image is a .GIF");
            }

        }
        catch (Exception e){

        }
    }
}
