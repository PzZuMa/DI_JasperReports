package org.example.retoconjuntohibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.retoconjuntohibernate.Aplicacion;
import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.dao.Session;
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
        HibernateUtil.getSessionFactory();
    }

    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) {
        Usuario introducido = ud.validateUser(tvUser.getText(), tvPwd.getText());
        if (introducido == null) {
            System.out.println("Usuario o contraseña incorrectos.");
        } else {
            Session.user = introducido;
            Aplicacion.loadFXML("views/main-view.fxml", "[User: " + introducido.getNombre() + "]",800,600);
        }
    }
}