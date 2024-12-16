package org.example.retoconjuntohibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.retoconjuntohibernate.Aplicacion;
import org.example.retoconjuntohibernate.dao.Hibernate_Util;
import org.example.retoconjuntohibernate.dao.RegisteredSession;
import org.example.retoconjuntohibernate.dao.UsuarioDAO;
import org.example.retoconjuntohibernate.models.Usuario;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para manejar las acciones de inicio de sesión.
 */
public class LoginController implements Initializable {
    @javafx.fxml.FXML
    private PasswordField tvPwd;
    @javafx.fxml.FXML
    private TextField tvUser;

    private UsuarioDAO ud = new UsuarioDAO(Hibernate_Util.getSessionFactory());

    /**
     * Inicializa el controlador.
     *
     * @param url la URL utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce la URL.
     * @param resourceBundle el ResourceBundle para localizar el objeto raíz, o null si no se ha localizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Maneja la acción de inicio de sesión.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Usuario introducido = ud.validateUser(tvUser.getText(), tvPwd.getText());
        if (introducido == null) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Usuario o contraseña incorrectos");
            alert.showAndWait();
        } else {
            RegisteredSession.user = introducido;
            Aplicacion.loadFXML("views/main-view.fxml", "MOVIE-UP [User: " + introducido.getNombre() + "]",900,800, false);
        }
    }

    /**
     * Maneja la acción de registro.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void registrar(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Aplicacion.loadFXML("views/register-view.fxml","Registro", 250, 350, false);
    }

    /**
     * Maneja la acción de contraseña olvidada.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void olvidada(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Contacte con el administrador para recuperar su contraseña.");
        alert.showAndWait();
    }
}