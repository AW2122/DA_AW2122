import java.util.ArrayList;

public class Room {
    private String name;
    private Museum museum;
    protected ArrayList<Artwork> artworks;

    public Room() {
        artworks = new ArrayList<Artwork>();
    }

    public void add(Artwork artwork) {
        artwork.setRoom(this);
        artworks.add(artwork);
    }

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
