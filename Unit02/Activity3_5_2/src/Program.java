import java.io.FileInputStream;

public class Program {
    public static int calculateValue(byte[] bmp, int offset) {
        // Calculate the size, width and height of bitmap: byte[x] + byte[x + 1] * 2^8 + byte[x + 2] * 2^16 + byte[x + 3] * 2^24
        int size = Byte.toUnsignedInt(bmp[offset]) + (Byte.toUnsignedInt(bmp[offset + 1]) * 256) + (Byte.toUnsignedInt(bmp[offset + 2]) * 65536) + (Byte.toUnsignedInt(bmp[offset + 3]) * 16777216);
        return size;
    }

    public static void main(String[] args) {
        byte[] fullBitmap = new byte[54];

        try {
            FileInputStream image = new FileInputStream("D:\\Downloads\\sample_640×426.bmp");
            // Reads the 26 bytes of the bitmap and stores it to the fullBitmap array
            image.read(fullBitmap);

            // If the header indicates that it is a BMP, calculate the size, width and length
            if ((fullBitmap[0] & 0xFF) == 0x42 && (fullBitmap[1] & 0xFF) == 0x4D) {
                System.out.println("The image is a .BMP");
                System.out.println("The size of the bitmap is " + calculateValue(fullBitmap, 2) + " bytes.");
                System.out.println("The width of the bitmap is " + calculateValue(fullBitmap, 18) + " bytes.");
                System.out.println("The height of the bitmap is " + calculateValue(fullBitmap, 22) + " bytes.");
                switch (calculateValue(fullBitmap, 30)) {
                    case 0:
                        System.out.println("The bitmap is not compressed.");
                        break;
                    case 1:
                        System.out.println("The bitmap has an RLE-8 compression.");
                        break;
                    case 2:
                        System.out.println("The bitmap has an RLE-4 compression.");
                        break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("The image file could not be read.");
        }
    }
}
