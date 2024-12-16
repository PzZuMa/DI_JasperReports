package org.example.retoconjuntohibernate.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.retoconjuntohibernate.Aplicacion;
import org.example.retoconjuntohibernate.dao.CopiaDAO;
import org.example.retoconjuntohibernate.dao.Hibernate_Util;
import org.example.retoconjuntohibernate.dao.*;
import org.example.retoconjuntohibernate.models.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador principal que maneja la interfaz de usuario y las interacciones en la aplicación.
 */
public class MainController implements Initializable {

    @javafx.fxml.FXML
    private TableView<Copia> tableID;
    @javafx.fxml.FXML
    private TableColumn<Copia,String> cTitle;
    @javafx.fxml.FXML
    private TableColumn<Copia,String> cEstado;
    @javafx.fxml.FXML
    private TableColumn<Copia,String> cSoporte;
    @javafx.fxml.FXML
    private TableColumn<Copia,String> cDirector;
    @javafx.fxml.FXML
    private TableColumn<Copia,String> cGenre;
    @javafx.fxml.FXML
    private TableColumn<Copia,String> cYear;
    @javafx.fxml.FXML
    private Button btnDelete;
    @javafx.fxml.FXML
    private Label welcomeUser;
    @javafx.fxml.FXML
    private Button btnAdd;

    private final CopiaDAO copiaDAO = new CopiaDAO(Hibernate_Util.getSessionFactory());

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
        cGenre.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getIdPelicula().getGenero());
        });
        cDirector.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getIdPelicula().getDirector());
        });
        cYear.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getIdPelicula().getAño().toString());
        });
        cEstado.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getEstado());
        });
        cSoporte.setCellValueFactory( (row) -> {
            return new SimpleStringProperty(row.getValue().getSoporte());
        });

        tableRefresh();

        /**
         * Si el usuario es administrador, se habilita el botón de añadir una pelicula a la BD.
         */
        if (RegisteredSession.user.getAdmin()) {
            btnAdd.setDisable(false);
        }

        welcomeUser.setText(RegisteredSession.user.getNombre());

        /**
         * Estos dos párrafos de código permiten realizar un listener en la tabla para detectar doble click en una fila y
         * otro para detectar cuando se hace solo un click sobre una fila, para así mostrar los datos de la película con
         * el doble click y habilitar diferentes funciones con un solo click.
         */
        tableID.setRowFactory( (tableView) -> {
            TableRow<Copia> row = new TableRow<>();
            row.setOnMouseClicked( (event) -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Copia rowData = row.getItem();
                    RegisteredSession.copiaSeleccionada = rowData;
                    RegisteredSession.playButtonSound();
                    Aplicacion.loadFXML("views/info-view.fxml", "Información", 900, 800, false);
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
        Aplicacion.loadFXML("views/newfilm-view.fxml", "Nueva película", 900, 800, false);
    }

    /**
     * Maneja la acción de mostrar la información del usuario.
     *
     * @param actionEvent el evento de acción.
     */
    @javafx.fxml.FXML
    public void userInfo(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Aplicacion.loadFXModal("views/user-view.fxml", "Información de usuario", 200, 400, false);
    }

    @javafx.fxml.FXML
    public void informeMasCopias(ActionEvent actionEvent) {
        ReportServices rs = new ReportServices(JDBC_Util.getConnection());
        rs.informePeliculasMasCopias();
    }

    @javafx.fxml.FXML
    public void informeDanadas(ActionEvent actionEvent) {
        ReportServices rs = new ReportServices(JDBC_Util.getConnection());
        rs.informePeliculasDanadas();
    }

    @javafx.fxml.FXML
    public void informePeliculas(ActionEvent actionEvent) {
        ReportServices rs = new ReportServices(JDBC_Util.getConnection());
        rs.informeListadoPeliculas();
    }
}
