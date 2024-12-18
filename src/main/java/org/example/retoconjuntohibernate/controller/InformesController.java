package org.example.retoconjuntohibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.retoconjuntohibernate.dao.JDBC_Util;
import org.example.retoconjuntohibernate.dao.RegisteredSession;
import org.example.retoconjuntohibernate.dao.ReportServices;

import java.net.URL;
import java.util.ResourceBundle;

public class InformesController implements Initializable {
    @javafx.fxml.FXML
    private Button btnVolver;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }

    @javafx.fxml.FXML
    public void informePeliculasCopiadas(ActionEvent actionEvent) {
        ReportServices rs = new ReportServices(JDBC_Util.getConnection());
        rs.informePeliculasMasCopias();
    }

    @javafx.fxml.FXML
    public void informePeliculasDanadas(ActionEvent actionEvent) {
        ReportServices rs = new ReportServices(JDBC_Util.getConnection());
        rs.informePeliculasDanadas();
    }

    @javafx.fxml.FXML
    public void informePeliculas(ActionEvent actionEvent) {
        ReportServices rs = new ReportServices(JDBC_Util.getConnection());
        rs.informeListadoPeliculas();
    }
}
