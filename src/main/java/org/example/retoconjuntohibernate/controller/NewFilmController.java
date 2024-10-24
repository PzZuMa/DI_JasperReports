package org.example.retoconjuntohibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.retoconjuntohibernate.Aplicacion;
import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.dao.PeliculaDAO;
import org.example.retoconjuntohibernate.dao.RegisteredSession;
import org.example.retoconjuntohibernate.models.Pelicula;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class NewFilmController implements Initializable {
    @javafx.fxml.FXML
    public Spinner spinnerYear;
    @javafx.fxml.FXML
    public TextArea tvDescription;
    @javafx.fxml.FXML
    public TextField tvTitle;
    @javafx.fxml.FXML
    public TextField tvDirector;
    @javafx.fxml.FXML
    public ComboBox cbGenero;


    private final PeliculaDAO peliDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Set<String> generos = new HashSet<>();
        peliDAO.findAll().forEach(peli -> {
            generos.add(peli.getGenero());
        });
        cbGenero.getItems().addAll(generos);

        spinnerYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2025, 2000));
    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        Aplicacion.loadFXML("views/main-view.fxml", "[User: " + RegisteredSession.user.getNombre() + "]",800,600);
    }

    @javafx.fxml.FXML
    public void añadir(ActionEvent actionEvent) {
        Pelicula peli = new Pelicula();
        peli.setTitulo(tvTitle.getText());
        peli.setGenero((String) cbGenero.getValue());
        peli.setAño((Integer) spinnerYear.getValue());
        peli.setDescripcion(tvDescription.getText());
        peli.setDirector(tvDirector.getText());

        peliDAO.insert(peli);

        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Película añadida correctamente");
        alert.showAndWait();
        Aplicacion.loadFXML("views/main-view.fxml", "[User: " + RegisteredSession.user.getNombre() + "]",800,600);
    }
}
