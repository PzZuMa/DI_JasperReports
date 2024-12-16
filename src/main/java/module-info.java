module org.example.retoconjuntohibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires java.desktop;
    requires jakarta.persistence;
    requires java.naming;
    requires javafx.media;
    requires net.sf.jasperreports.core;

    opens org.example.retoconjuntohibernate.models;


    opens org.example.retoconjuntohibernate to javafx.fxml;
    exports org.example.retoconjuntohibernate;
    exports org.example.retoconjuntohibernate.controller;
    opens org.example.retoconjuntohibernate.controller to javafx.fxml;
    opens org.example.jasperhello to javafx.fxml;
    exports org.example.jasperhello;
}