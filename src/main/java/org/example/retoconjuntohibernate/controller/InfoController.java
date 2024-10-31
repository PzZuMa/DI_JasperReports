package org.example.retoconjuntohibernate.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.retoconjuntohibernate.Aplicacion;
import org.example.retoconjuntohibernate.dao.CopiaDAO;
import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.dao.RegisteredSession;
import org.example.retoconjuntohibernate.models.Copia;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador de la vista de información de la película
 */
public class InfoController implements Initializable {
    @javafx.fxml.FXML
    private TextArea tvDescripcion;
    @javafx.fxml.FXML
    private TextField tvYear;
    @javafx.fxml.FXML
    private TextField tvGenre;
    @javafx.fxml.FXML
    private TextField tvTitle;
    @javafx.fxml.FXML
    private TextField tvDirector;
    @javafx.fxml.FXML
    private ComboBox cbEstado;
    @javafx.fxml.FXML
    private ComboBox cbSoporte;
    @javafx.fxml.FXML
    private ImageView ivPoster;
    @javafx.fxml.FXML
    private Button btnStop;
    @javafx.fxml.FXML
    private Slider volume;
    @javafx.fxml.FXML
    private Button btnPlay;
    @javafx.fxml.FXML
    private Button btnPause;

    private MediaPlayer mpBSO;

    /**
     * Inicializa el controlador y configura los elementos de la vista con los datos de la película seleccionada.
     *
     * @param url la URL utilizada para resolver rutas relativas para el objeto raíz, o null si no se conoce la URL.
     * @param resourceBundle el ResourceBundle para localizar objetos raíz, o null si no se ha localizado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbEstado.getItems().addAll("bueno","dañado");
        cbSoporte.getItems().addAll("DVD","Blu-ray");

        tvTitle.setText(RegisteredSession.copiaSeleccionada.getIdPelicula().getTitulo());
        tvGenre.setText(RegisteredSession.copiaSeleccionada.getIdPelicula().getGenero());
        tvYear.setText(RegisteredSession.copiaSeleccionada.getIdPelicula().getAño().toString());
        tvDescripcion.setText(RegisteredSession.copiaSeleccionada.getIdPelicula().getDescripcion());
        tvDirector.setText(RegisteredSession.copiaSeleccionada.getIdPelicula().getDirector());
        cbEstado.setValue(RegisteredSession.copiaSeleccionada.getEstado());
        cbSoporte.setValue(RegisteredSession.copiaSeleccionada.getSoporte());

        /**
         * Carga la BSO de la película seleccionada en el MediaPlayer.
         */
        String bsoPath = "src/main/resources/org/example/retoconjuntohibernate/media/bso/" + RegisteredSession.copiaSeleccionada.getIdPelicula().getBso();
        File bsoFile = new File(bsoPath);
        if (bsoFile.exists()) {
            Media bso = new Media(bsoFile.toURI().toString());
            mpBSO = new MediaPlayer(bso);

            volume.setValue(RegisteredSession.volumen);
            mpBSO.setVolume(RegisteredSession.volumen);
            volume.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    mpBSO.setVolume(newValue.doubleValue());
                    RegisteredSession.volumen = newValue.doubleValue();
                }
            });
        } else {
            System.err.println("BSO not found: " + bsoPath);
        }

        /**
         * Carga el poster de la película seleccionada.
         */
        String posterPath = "src/main/resources/org/example/retoconjuntohibernate/media/posters/" + RegisteredSession.copiaSeleccionada.getIdPelicula().getPoster();
        File posterFile = new File(posterPath);
        if (posterFile.exists()) {
            ivPoster.setImage(new Image(posterFile.toURI().toString()));
        } else {
            System.err.println("Poster not found: " + posterPath);
        }
    }

    /**
     * Maneja la acción de volver al menú principal.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        if (mpBSO != null) {
            mpBSO.stop();
        }
        RegisteredSession.playButtonSound();
        Aplicacion.loadFXML("views/main-view.fxml", "MOVIE-UP [User: " + RegisteredSession.user.getNombre() + "]",600,600, false);
    }

    /**
     * Maneja la acción de guardar los cambios en la copia.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void save(ActionEvent actionEvent) {
        CopiaDAO copiaDAO = new CopiaDAO(HibernateUtil.getSessionFactory());

        Copia copia = new Copia();
        copia.setId(RegisteredSession.copiaSeleccionada.getId());
        copia.setIdUsuario(RegisteredSession.user);
        copia.setIdPelicula(RegisteredSession.copiaSeleccionada.getIdPelicula());
        copia.setEstado(String.valueOf(cbEstado.getValue()));
        copia.setSoporte(String.valueOf(cbSoporte.getValue()));
        copiaDAO.update(copia);

        RegisteredSession.playButtonSound();
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Copia actualizada correctamente");
        alert.showAndWait();
        if (mpBSO != null) {
            mpBSO.stop();
        }
        Aplicacion.loadFXML("views/main-view.fxml", "MOVIE-UP [User: " + RegisteredSession.user.getNombre() + "]",600,600, false);
    }

    /**
     * Maneja la acción de reproducir la BSO.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void Play(ActionEvent actionEvent) {
        if (mpBSO != null) {
            mpBSO.play();
            btnPlay.setDisable(true);
            btnStop.setDisable(false);
            btnPause.setDisable(false);
        } else {
            System.err.println("No BSO found");
        }
    }

    /**
     * Maneja la acción de detener la BSO.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void Stop(ActionEvent actionEvent) {
        mpBSO.stop();
        btnStop.setDisable(true);
        btnPlay.setDisable(false);
        btnPause.setDisable(true);
        RegisteredSession.playButtonSound();
    }

    /**
     * Maneja la acción de pausar la BSO.
     *
     * @param actionEvent el evento de acción que desencadena este método.
     */
    @javafx.fxml.FXML
    public void Pause(ActionEvent actionEvent) {
        mpBSO.pause();
        btnPlay.setDisable(false);
        btnPause.setDisable(true);
        RegisteredSession.playButtonSound();
    }
}
