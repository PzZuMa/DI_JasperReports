package org.example.retoconjuntohibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.retoconjuntohibernate.Aplicacion;
import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.dao.RegisteredSession;
import org.example.retoconjuntohibernate.dao.UsuarioDAO;
import org.example.retoconjuntohibernate.models.Usuario;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador para manejar las acciones de registro de usuario.
 */
public class RegisterController implements Initializable {

    @javafx.fxml.FXML
    public TextField tvUser;
    @javafx.fxml.FXML
    public PasswordField tvPWD;
    @javafx.fxml.FXML
    public PasswordField tvConfirm;
    @javafx.fxml.FXML
    private CheckBox cbAdmin;

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
     * Maneja la acción de cancelar el registro.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Aplicacion.loadFXML("views/login-view.fxml","Login", 250, 350, false);
    }

    /**
     * Maneja la acción de registrar un usuario.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void registrar(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        if (tvUser.getText().isEmpty() || tvPWD.getText().isEmpty() || tvConfirm.getText().isEmpty()) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Por favor, rellene todos los campos");
            alert.showAndWait();
        } else {
            if(tvPWD.getText().equals(tvConfirm.getText())) {
                Usuario user = new Usuario();
                user.setNombre(tvUser.getText());
                user.setContraseña(tvPWD.getText());
                user.setAdmin(cbAdmin.isSelected());
                UsuarioDAO ud = new UsuarioDAO(HibernateUtil.getSessionFactory());
                ud.insert(user);
                var alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro");
                alert.setHeaderText("Usuario registrado correctamente");
                alert.showAndWait();
                RegisteredSession.user = user;
                Aplicacion.loadFXML("views/main-view.fxml","MOVIE-UP [User: " + user.getNombre() + "]", 900,800, false);
            } else {
                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Las contraseñas no coinciden");
                alert.showAndWait();
            }
        }
    }
}
