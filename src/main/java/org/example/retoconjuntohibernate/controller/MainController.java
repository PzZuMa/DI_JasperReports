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

/**
 * Controlador principal que maneja la interfaz de usuario y las interacciones en la aplicación.
 */
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

    /**
     * Inicializa el controlador y configura las columnas de la tabla y los listeners.
     *
     * @param url la URL utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce la URL.
     * @param resourceBundle el ResourceBundle para localizar objetos raíz, o null si no se ha localizado.
     */
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

        /**
         * Estos dos párrafos de código permiten realizar un listener en la tabla para detectar doble click en una fila y
         * otro para detectar cuando se hace solo un click sobre una fila, para así mostrar los datos de la película con
         * el doble click y habilitar diferentes funciones con un solo click.
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

    /**
     * Refresca los datos de la tabla con las copias del usuario actual.
     */
    public void tableRefresh() {
        tableID.getItems().clear();
        tableID.getItems().addAll(copiaDAO.findCopiasByUserID(RegisteredSession.user.getId()));
    }

    /**
     * Maneja la acción de cerrar sesión.
     *
     * @param actionEvent el evento de acción.
     */
    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        RegisteredSession.user = null;
        RegisteredSession.playButtonSound();
        Aplicacion.loadFXML("views/login-view.fxml", "Login", 250, 350, false);
    }

    /**
     * Maneja la acción de eliminar una copia seleccionada.
     *
     * @param actionEvent el evento de acción.
     */
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

    /**
     * Maneja la acción de insertar una nueva copia.
     *
     * @param actionEvent el evento de acción.
     */
    @javafx.fxml.FXML
    public void insertarCopia(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Aplicacion.loadFXModal("views/newcopy-view.fxml", "Nueva copia", 300, 250, false);
        tableRefresh();
        btnDelete.setDisable(true);
    }

    /**
     * Maneja la acción de añadir una nueva película.
     *
     * @param actionEvent el evento de acción.
     */
    @javafx.fxml.FXML
    public void añadirPelicula(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Aplicacion.loadFXML("views/newfilm-view.fxml", "Nueva película", 700, 700, false);
    }

    /**
     * Maneja la acción de mostrar la información del usuario.
     *
     * @param actionEvent el evento de acción.
     */
    @javafx.fxml.FXML
    public void userInfo(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Aplicacion.loadFXModal("views/user-view.fxml", "Información de usuario", 200, 350, false);
    }
}
