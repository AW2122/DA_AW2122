module com.aw2122.finalactivity.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires android.json;


    opens com.aw2122.finalactivity.library to javafx.fxml;
    exports com.aw2122.finalactivity.library;
}