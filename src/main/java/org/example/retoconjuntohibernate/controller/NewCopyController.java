package org.example.retoconjuntohibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.retoconjuntohibernate.Aplicacion;
import org.example.retoconjuntohibernate.dao.CopiaDAO;
import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.dao.PeliculaDAO;
import org.example.retoconjuntohibernate.dao.RegisteredSession;
import org.example.retoconjuntohibernate.models.Copia;

import java.net.URL;
import java.util.ResourceBundle;

public class NewCopyController implements Initializable {
    @javafx.fxml.FXML
    public ComboBox cbPeliculas;
    @javafx.fxml.FXML
    public ComboBox cbEstado;
    @javafx.fxml.FXML
    public ComboBox cbSoporte;
    @javafx.fxml.FXML
    public Button btnIntroducir;


    private final PeliculaDAO peliDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());
    private final CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        peliDAO.findAll().forEach(peli -> {
            cbPeliculas.getItems().add(peli.getTitulo());
        });

        cbEstado.getItems().addAll("bueno","dañado");
        cbSoporte.getItems().addAll("DVD","Blu-ray");

        cbPeliculas.setOnAction(event -> checkCB());
        cbEstado.setOnAction(event -> checkCB());
        cbSoporte.setOnAction(event -> checkCB());
    }

    @javafx.fxml.FXML
    public void addCopy(ActionEvent actionEvent) {
        var copia = new Copia();
        copia.setIdPelicula(peliDAO.findPeliByName((String) cbPeliculas.getValue()));
        copia.setEstado((String) cbEstado.getValue());
        copia.setSoporte((String) cbSoporte.getValue());
        copia.setIdUsuario(RegisteredSession.user);
        copiaDAO.insert(copia);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Copia añadida correctamente");
        alert.showAndWait();

        Aplicacion.loadFXML("views/main-view.fxml", "[User: " + RegisteredSession.user.getNombre() + "]",800,600);
    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        Aplicacion.loadFXML("views/main-view.fxml", "[User: " + RegisteredSession.user.getNombre() + "]",800,600);
    }

    private void checkCB() {
        boolean allSelected = (cbPeliculas.getValue() != null && cbEstado.getValue() != null && cbSoporte.getValue() != null);
        if (allSelected) {
            btnIntroducir.setDisable(false);
        } else {
            btnIntroducir.setDisable(true);
        }
    }
}
