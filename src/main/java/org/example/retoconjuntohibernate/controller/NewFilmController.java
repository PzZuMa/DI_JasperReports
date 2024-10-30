package org.example.retoconjuntohibernate.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import org.example.retoconjuntohibernate.Aplicacion;
import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.dao.PeliculaDAO;
import org.example.retoconjuntohibernate.dao.RegisteredSession;
import org.example.retoconjuntohibernate.models.Pelicula;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class NewFilmController implements Initializable {
    @javafx.fxml.FXML
    public Spinner spinnerYear;
    @javafx.fxml.FXML
    public TextArea tvDescription;
    @javafx.fxml.FXML
    public TextField tvTitle;
    @javafx.fxml.FXML
    public TextField tvDirector;
    @javafx.fxml.FXML
    public ComboBox cbGenero;
    @javafx.fxml.FXML
    private Button btnStop;
    @javafx.fxml.FXML
    private Button btnPlay;
    @javafx.fxml.FXML
    private ImageView ivPoster;


    private MediaPlayer mp;
    private final PeliculaDAO peliDAO = new PeliculaDAO(HibernateUtil.getSessionFactory());
    private Pelicula peli = new Pelicula();
    @javafx.fxml.FXML
    private Slider volume;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Set<String> generos = new HashSet<>();
        peliDAO.findAll().forEach(peli -> {
            generos.add(peli.getGenero());
        });
        cbGenero.getItems().addAll(generos);

        spinnerYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2025, 2000));
    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        if (mp != null) {
            mp.stop();
        }
        Aplicacion.loadFXML("views/main-view.fxml", "MOVIE-UP [User: " + RegisteredSession.user.getNombre() + "]",600,600, false);
    }

    @javafx.fxml.FXML
    public void añadir(ActionEvent actionEvent) {
        peli.setTitulo(tvTitle.getText());
        peli.setGenero((String) cbGenero.getValue());
        peli.setAño((Integer) spinnerYear.getValue());
        peli.setDescripcion(tvDescription.getText());
        peli.setDirector(tvDirector.getText());

        peliDAO.insert(peli);

        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Película añadida correctamente");
        alert.showAndWait();
        if (mp != null) {
            mp.stop();
        }
        Aplicacion.loadFXML("views/main-view.fxml", "MOVIE-UP [User: " + RegisteredSession.user.getNombre() + "]",600,600, false);
    }

    @javafx.fxml.FXML
    public void añadirBSO(ActionEvent actionEvent) {
        var fc = new FileChooser();
        fc.setTitle("Selecciona una banda sonora");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3", "*.mp3")
        );

        var file = fc.showOpenDialog(null);
        if (file != null) {
            try {
                File targetDirect = new File("src/main/resources/org/example/retoconjuntohibernate/media/bso/");

                File targetFile = new File(targetDirect, file.getName());
                Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                peli.setBso(file.getName());
                mp = new MediaPlayer(new Media(targetFile.toURI().toString()));
                btnPlay.setDisable(false);
                volume.setDisable(false);

                mp.setVolume(volume.getValue());
                volume.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        mp.setVolume(newValue.doubleValue());
                    }
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @javafx.fxml.FXML
    public void añadirPoster(ActionEvent actionEvent) {
        var fc = new FileChooser();
        fc.setTitle("Selecciona un poster");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );

        var file = fc.showOpenDialog(null);
        if (file != null) {
            try {
                File targetDirect = new File("src/main/resources/org/example/retoconjuntohibernate/media/posters/");

                File targetFile = new File(targetDirect, file.getName());
                Files.copy(file.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                peli.setPoster(file.getName());
                ivPoster.setImage(new Image("file:"+file.getAbsolutePath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @javafx.fxml.FXML
    public void Play(ActionEvent actionEvent) {
        if (mp != null) {
            mp.play();
        }
        btnStop.setDisable(false);
    }

    @javafx.fxml.FXML
    public void Stop(ActionEvent actionEvent) {
        mp.stop();
        btnStop.setDisable(true);
    }
}
