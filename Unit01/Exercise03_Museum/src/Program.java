import java.util.ArrayList;

public class Program {
    public static void PrintArtwork(ArrayList<Artwork> artworks) {
        for (int i = 0; i < artworks.size(); i++)
        {
            System.out.println(artworks.get(i).getTitle());
            if (artworks.get(i) instanceof Painting) {
                System.out.println(((Painting) artworks.get(i)).getFormat());
                System.out.println(((Painting) artworks.get(i)).getType());
            }
            if (artworks.get(i) instanceof Sculpture) {
                System.out.println(((Sculpture) artworks.get(i)).getMaterial());
                System.out.println(((Sculpture) artworks.get(i)).getStyle());
            }
        }
    }

    public static void main(String[] args) {
        List<Room> roomList =

    }
}
