module com.aw2122.unit05.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javax.persistence;
    requires java.sql;


    opens com.aw2122.unit05.library to javafx.fxml;
    exports com.aw2122.unit05.library;
}