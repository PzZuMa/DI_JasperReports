package org.example.retoconjuntohibernate.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.retoconjuntohibernate.Aplicacion;
import org.example.retoconjuntohibernate.dao.CopiaDAO;
import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.dao.*;
import org.example.retoconjuntohibernate.models.Copia;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {

    @javafx.fxml.FXML
    public TableView<Copia> tableID;
    @javafx.fxml.FXML
    public TableColumn<Copia,String> cTitle;
    @javafx.fxml.FXML
    public TableColumn<Copia,String> cEstado;
    @javafx.fxml.FXML
    public TableColumn<Copia,String> cSoporte;
    @javafx.fxml.FXML
    public Button btnDelete;
    @javafx.fxml.FXML
    private Label welcomeUser;


    private final CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cTitle.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getIdPelicula().getTitulo());
        });
        cEstado.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getEstado());
        });
        cSoporte.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getSoporte());
        });

        tableRefresh();

        welcomeUser.setText(RegisteredSession.user.getNombre());

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
                    RegisteredSession.playButtonSound();
                    Aplicacion.loadFXML("views/info-view.fxml", "Información", 700, 700, false);
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
        RegisteredSession.playButtonSound();
        Aplicacion.loadFXML("views/login-view.fxml", "Login", 250, 350, false);
    }

    @javafx.fxml.FXML
    public void deleteCopy(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        if (RegisteredSession.copiaSeleccionada == null) {
            return;
        }
        copiaDAO.delete(RegisteredSession.copiaSeleccionada);
        tableRefresh();
        btnDelete.setDisable(true);
    }

    @javafx.fxml.FXML
    public void insertarCopia(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Aplicacion.loadFXModal("views/newcopy-view.fxml", "Nueva copia", 300, 250, false);
        tableRefresh();
        btnDelete.setDisable(true);
    }

    @javafx.fxml.FXML
    public void añadirPelicula(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Aplicacion.loadFXML("views/newfilm-view.fxml", "Nueva pelicula", 700, 700, false);
    }

    @javafx.fxml.FXML
    public void userInfo(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Aplicacion.loadFXModal("views/user-view.fxml", "Información de usuario", 200, 350, false);
    }
}
