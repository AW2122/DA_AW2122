import java.io.Serializable;
import java.util.ArrayList;

public class Contacts implements Serializable {
    protected String name;
    protected String surname;
    protected String email;
    protected String phoneNumber;
    protected String description;

    public Contacts(String name, String surname, String email, String phoneNumber, String description) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "Name: " + name + "\n" + "Surname: " + surname + "\n" + "E-mail: " + email + "\n" + "Phone: " +
                phoneNumber + "\n" + "Description: " + description;
    }
}
