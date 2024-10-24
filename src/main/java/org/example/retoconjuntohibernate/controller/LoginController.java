package org.example.retoconjuntohibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.retoconjuntohibernate.Aplicacion;
import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.dao.RegisteredSession;
import org.example.retoconjuntohibernate.dao.UsuarioDAO;
import org.example.retoconjuntohibernate.models.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @javafx.fxml.FXML
    private PasswordField tvPwd;
    @javafx.fxml.FXML
    private TextField tvUser;


    private UsuarioDAO ud = new UsuarioDAO(HibernateUtil.getSessionFactory());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) {
        Usuario introducido = ud.validateUser(tvUser.getText(), tvPwd.getText());
        if (introducido == null) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Usuario o contraseña incorrectos");
            alert.showAndWait();
        } else {
            RegisteredSession.user = introducido;
            Aplicacion.loadFXML("views/main-view.fxml", "[User: " + introducido.getNombre() + "]",800,600);
        }
    }

    @javafx.fxml.FXML
    public void registrar(ActionEvent actionEvent) {
        Aplicacion.loadFXML("views/register-view.fxml","Registro", 400, 300);
    }
}