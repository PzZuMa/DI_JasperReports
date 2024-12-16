package org.example.retoconjuntohibernate.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.retoconjuntohibernate.dao.CopiaDAO;
import org.example.retoconjuntohibernate.dao.Hibernate_Util;
import org.example.retoconjuntohibernate.dao.PeliculaDAO;
import org.example.retoconjuntohibernate.dao.RegisteredSession;
import org.example.retoconjuntohibernate.models.Copia;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de la vista de añadir copia
 */
public class NewCopyController implements Initializable {
    @javafx.fxml.FXML
    public ComboBox cbPeliculas;
    @javafx.fxml.FXML
    public ComboBox cbEstado;
    @javafx.fxml.FXML
    public ComboBox cbSoporte;
    @javafx.fxml.FXML
    public Button btnIntroducir;

    private final PeliculaDAO peliDAO = new PeliculaDAO(Hibernate_Util.getSessionFactory());
    private final CopiaDAO copiaDAO = new CopiaDAO(Hibernate_Util.getSessionFactory());

    /**
     * Inicializa el controlador y configura los elementos de la vista.
     *
     * @param url la URL utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce la URL.
     * @param resourceBundle el ResourceBundle para localizar objetos raíz, o null si no se ha localizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        peliDAO.findAll().forEach(peli -> {
            cbPeliculas.getItems().add(peli.getTitulo());
        });

        cbEstado.getItems().addAll("bueno","dañado");
        cbSoporte.getItems().addAll("DVD","Blu-ray");

        cbPeliculas.setOnAction(event -> checkCB());
        cbEstado.setOnAction(event -> checkCB());
        cbSoporte.setOnAction(event -> checkCB());
    }

    /**
     * Maneja la acción de añadir una copia.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void addCopy(ActionEvent actionEvent) {
        var copia = new Copia();
        copia.setIdPelicula(peliDAO.findPeliByName((String) cbPeliculas.getValue()));
        copia.setEstado((String) cbEstado.getValue());
        copia.setSoporte((String) cbSoporte.getValue());
        copia.setIdUsuario(RegisteredSession.user);
        copiaDAO.insert(copia);

        RegisteredSession.playButtonSound();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Copia añadida correctamente");
        alert.showAndWait();

        Stage stage = (Stage) btnIntroducir.getScene().getWindow();
        stage.close();
    }

    /**
     * Maneja la acción de cancelar.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        RegisteredSession.playButtonSound();
        Stage stage = (Stage) btnIntroducir.getScene().getWindow();
        stage.close();
    }

    /**
     * Comprueba si los ComboBox están seleccionados para habilitar el botón de añadir.
     */
    private void checkCB() {
        boolean allSelected = (cbPeliculas.getValue() != null && cbEstado.getValue() != null && cbSoporte.getValue() != null);
        if (allSelected) {
            btnIntroducir.setDisable(false);
        } else {
            btnIntroducir.setDisable(true);
        }
    }
}
