package org.example.retoconjuntohibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.retoconjuntohibernate.Aplicacion;
import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.dao.UsuarioDAO;
import org.example.retoconjuntohibernate.models.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @javafx.fxml.FXML
    public TextField tvUser;
    @javafx.fxml.FXML
    public PasswordField tvPWD;
    @javafx.fxml.FXML
    public PasswordField tvConfirm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        Aplicacion.loadFXML("views/login-view.fxml","Login", 800, 600);
    }

    @javafx.fxml.FXML
    public void registrar(ActionEvent actionEvent) {
        if(tvPWD.getText().equals(tvConfirm.getText())){
            Usuario user = new Usuario();
            user.setNombre(tvUser.getText());
            user.setContraseña(tvPWD.getText());

            UsuarioDAO ud = new UsuarioDAO(HibernateUtil.getSessionFactory());
            ud.insert(user);
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro");
            alert.setHeaderText("Usuario registrado correctamente");
            alert.showAndWait();
            Aplicacion.loadFXML("views/main-view.fxml","[User: " + user.getNombre() + "]", 800, 600);
        } else {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Las contraseñas no coinciden");
            alert.showAndWait();
        }
    }
}
