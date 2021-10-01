import java.io.FileInputStream;

public class Program {
    public static void main(String[] args) {
        byte[] fullBitmap = new byte[54];
        StringBuilder output = new StringBuilder();
        try {
            FileInputStream image = new FileInputStream("D:\\Downloads\\sample_640×426.bmp");
            image.read(fullBitmap, 18, 4);
            System.out.println("BMP width: ");
            for (byte b:fullBitmap) {
                output.append(String.format("%02X", b));
            }
            System.out.println(output.toString());
        }
        catch (Exception e) {

        }
    }
}
