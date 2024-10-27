package org.example.retoconjuntohibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Aplicacion extends Application {

    private static Stage ventana;

    @Override
    public void start(Stage stage) throws IOException {
        ventana = stage;
        loadFXML("views/login-view.fxml", "Login", 250, 350, false);
        stage.show();
    }

    public static void loadFXML(String view, String title, Integer anchura, Integer altura, Boolean resizable) {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource(view));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(),anchura,altura);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ventana.setTitle(title);
        ventana.setScene(scene);
        ventana.setResizable(resizable);
    }

    public static void main(String[] args) {
        launch();
    }
}