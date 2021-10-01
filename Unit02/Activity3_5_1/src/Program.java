import java.io.FileInputStream;

public class Program {
    public static void main(String[] args) {
        byte[] buffer = new byte[6];
        try {
            FileInputStream image = new FileInputStream("D:\\Downloads\\sample_640×426.bmp");
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
            if ((buffer[0] & 0xFF) == 0x00 && (buffer[1] & 0xFF) == 0x00 && (buffer[2] & 0xFF) == 0x01 && (buffer[3] & 0xFF) == 0x00) {
                System.out.println("The image is a .ICO");
            }
            if ((buffer[0] & 0xFF) == 0x89 && (buffer[1] & 0xFF) == 0x50 && (buffer[2] & 0xFF) == 0x4E && (buffer[3] & 0xFF) == 0x47) {
                System.out.println("The image is a .PNG");
            }
        }
        catch (Exception e){

        }
    }
}
