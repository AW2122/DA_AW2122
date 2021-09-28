import java.util.ArrayList;
import java.util.List;

public class Museum {
    protected String name;
    protected String address;
    protected String city;
    protected String country;
    protected ArrayList<Room> rooms;

    public Museum() {
        rooms = new ArrayList<Room>();
    }

    public void add(Room room) {
        room.setMuseum(this);
        rooms.add(room);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
