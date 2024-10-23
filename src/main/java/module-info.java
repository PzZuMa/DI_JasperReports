module org.example.retoconjuntohibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires java.desktop;
    requires jakarta.persistence;

    opens org.example.retoconjuntohibernate to javafx.fxml;
    exports org.example.retoconjuntohibernate;
}