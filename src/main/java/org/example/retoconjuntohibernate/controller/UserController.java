package org.example.retoconjuntohibernate.controller;

import javafx.event.*;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.retoconjuntohibernate.dao.CopiaDAO;
import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.dao.RegisteredSession;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @javafx.fxml.FXML
    private TextField ivCopias;
    @javafx.fxml.FXML
    private TextField ivNombre;
    @javafx.fxml.FXML
    private PasswordField ivPWD;
    @javafx.fxml.FXML
    private Button btnVolver;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CopiaDAO cd = new CopiaDAO(HibernateUtil.getSessionFactory());

        ivNombre.setText(RegisteredSession.user.getNombre());
        ivPWD.setText(RegisteredSession.user.getContraseña());
        ivCopias.setText(String.valueOf(cd.countCopyByUser(RegisteredSession.user)));
    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }
}
