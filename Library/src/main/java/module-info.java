module com.aw2122.unit05.library {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires javax.persistence;
    requires java.sql;
    requires org.hibernate.commons.annotations;
    requires org.hibernate.orm.core;


    opens com.aw2122.unit05 to javafx.fxml;
    exports com.aw2122.unit05;

}