module org.example.retoconjuntohibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires java.desktop;

    opens org.example.retoconjuntohibernate to javafx.fxml;
    exports org.example.retoconjuntohibernate;
}