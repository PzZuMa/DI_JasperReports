package org.example.retoconjuntohibernate.controller;

import javafx.beans.property.SimpleStringProperty;
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


public class MainController implements Initializable {

    @javafx.fxml.FXML
    public TableView<Copia> tableID;
    @javafx.fxml.FXML
    public TableColumn<Copia,String> cTitle;
    @javafx.fxml.FXML
    public TableColumn<Copia,String> cGenre;
    @javafx.fxml.FXML
    public TableColumn<Copia,String> cYear;
    @javafx.fxml.FXML
    public TableColumn<Copia,String> cDescription;
    @javafx.fxml.FXML
    public TableColumn<Copia,String> cDirector;
    @javafx.fxml.FXML
    public TableColumn<Copia,String> cEstado;
    @javafx.fxml.FXML
    public TableColumn<Copia,String> cSoporte;
    @javafx.fxml.FXML
    public TableColumn<Copia,String> cID;
    @javafx.fxml.FXML
    public Button btnDelete;


    private final CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cID.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getId().toString());
        });
        cTitle.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getIdPelicula().getTitulo());
        });
        cGenre.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getIdPelicula().getGenero());
        });
        cYear.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getIdPelicula().getAño().toString());
        });
        cDescription.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getIdPelicula().getDescripcion());
        });
        cDirector.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getIdPelicula().getDirector());
        });
        cEstado.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getEstado());
        });
        cSoporte.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getSoporte());
        });

        tableRefresh();

        /*
        Estos dos parrafos de codigo permite realizar un listener en la tabla para detectar doble click en una fila y
        otro para detectar cuanndo se hace solo un click sobre una fila, para asi mostrar los datos de la pelicula con
        el doble click y habilitar diferentes funciones con un solo click.
         */
        tableID.setRowFactory(tv -> {
            TableRow<Copia> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Copia rowData = row.getItem();
                    RegisteredSession.copiaSeleccionada = rowData;
                    Aplicacion.loadFXML("views/info-view.fxml", "Información", 1000, 800, true);
                }
            });
            return row;
        });
        tableID.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            RegisteredSession.copiaSeleccionada = newSelection;
            btnDelete.setDisable(false);
        });


    }

    public void tableRefresh() {
        tableID.getItems().clear();
        tableID.getItems().addAll(copiaDAO.findCopiasByUserID(RegisteredSession.user.getId()));
    }

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        RegisteredSession.user = null;
        Aplicacion.loadFXML("views/login-view.fxml", "Login", 250, 350, true);
    }

    @javafx.fxml.FXML
    public void deleteCopy(ActionEvent actionEvent) {
        copiaDAO.delete(RegisteredSession.copiaSeleccionada);
        tableRefresh();
    }

    @javafx.fxml.FXML
    public void insertarCopia(ActionEvent actionEvent) {
        Aplicacion.loadFXML("views/newcopy-view.fxml", "Insertar Copia", 400, 300, true);
    }

    @javafx.fxml.FXML
    public void añadirPelicula(ActionEvent actionEvent) {
        Aplicacion.loadFXML("views/newfilm-view.fxml", "Añadir Pelicula", 800, 600, true);
    }
}
