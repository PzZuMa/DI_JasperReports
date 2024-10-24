package org.example.retoconjuntohibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.retoconjuntohibernate.Aplicacion;
import org.example.retoconjuntohibernate.dao.CopiaDAO;
import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.dao.RegisteredSession;
import org.example.retoconjuntohibernate.models.Copia;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoController implements Initializable {
    @javafx.fxml.FXML
    private TextArea tvDescripcion;
    @javafx.fxml.FXML
    private TextField tvYear;
    @javafx.fxml.FXML
    private TextField tvGenre;
    @javafx.fxml.FXML
    private Label tituloID;
    @javafx.fxml.FXML
    private TextField tvTitle;
    @javafx.fxml.FXML
    private TextField tvDirector;
    @javafx.fxml.FXML
    private ComboBox cbEstado;
    @javafx.fxml.FXML
    private ComboBox cbSoporte;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbEstado.getItems().addAll("bueno","dañado");
        cbSoporte.getItems().addAll("DVD","Blu-ray");

        tituloID.setText(RegisteredSession.copiaSeleccionada.getIdPelicula().getTitulo());
        tvTitle.setText(RegisteredSession.copiaSeleccionada.getIdPelicula().getTitulo());
        tvGenre.setText(RegisteredSession.copiaSeleccionada.getIdPelicula().getGenero());
        tvYear.setText(RegisteredSession.copiaSeleccionada.getIdPelicula().getAño().toString());
        tvDescripcion.setText(RegisteredSession.copiaSeleccionada.getIdPelicula().getDescripcion());
        tvDirector.setText(RegisteredSession.copiaSeleccionada.getIdPelicula().getDirector());
        cbEstado.setValue(RegisteredSession.copiaSeleccionada.getEstado());
        cbSoporte.setValue(RegisteredSession.copiaSeleccionada.getSoporte());
    }

    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        Aplicacion.loadFXML("views/main-view.fxml", "[User: " + RegisteredSession.user.getNombre() + "]",800,600);
    }

    @javafx.fxml.FXML
    public void save(ActionEvent actionEvent) {
        CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());

        Copia copia = new Copia();
        copia.setId(RegisteredSession.copiaSeleccionada.getId());
        copia.setIdUsuario(RegisteredSession.user);
        copia.setIdPelicula(RegisteredSession.copiaSeleccionada.getIdPelicula());
        copia.setEstado(String.valueOf(cbEstado.getValue()));
        copia.setSoporte(String.valueOf(cbSoporte.getValue()));
        copiaDAO.update(copia);

        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Copia actualizada correctamente");
        alert.showAndWait();
        Aplicacion.loadFXML("views/main-view.fxml", "[User: " + RegisteredSession.user.getNombre() + "]",800,600);
    }
}
