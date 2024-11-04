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

/**
 * Controlador de la vista de usuario
 */
public class UserController implements Initializable {
    @javafx.fxml.FXML
    private TextField ivCopias;
    @javafx.fxml.FXML
    private TextField ivNombre;
    @javafx.fxml.FXML
    private TextField ivPWD;
    @javafx.fxml.FXML
    private Button btnVolver;
    @javafx.fxml.FXML
    private TextField ivEstado;

    /**
     * Inicializa el controlador y configura los elementos de la vista.
     *
     * @param url la URL utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce la URL.
     * @param resourceBundle el ResourceBundle para localizar objetos raíz, o null si no se ha localizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CopiaDAO cd = new CopiaDAO(HibernateUtil.getSessionFactory());

        ivNombre.setText(RegisteredSession.user.getNombre());
        ivPWD.setText(RegisteredSession.user.getContraseña());
        ivCopias.setText(String.valueOf(cd.countCopyByUser(RegisteredSession.user)));

        if (RegisteredSession.user.getAdmin()) {
            ivEstado.setText("Administrador");
        } else {
            ivEstado.setText("Usuario");
        }
    }

    /**
     * Maneja la acción de volver a la vista anterior.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        stage.close();
    }
}
