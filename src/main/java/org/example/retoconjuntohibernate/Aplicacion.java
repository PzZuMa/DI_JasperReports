package org.example.retoconjuntohibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Aplicacion extends Application {

    private static Stage ventana;

    @Override
    public void start(Stage stage) throws IOException {
        ventana = stage;
        loadFXML("views/login-view.fxml", "Login", 800, 600);
        stage.show();
    }

    public static void loadFXML(String view, String title, Integer anchura, Integer altura) {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource(view));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(),anchura,altura);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ventana.setTitle(title);
        ventana.setScene(scene);
//        ventana.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}