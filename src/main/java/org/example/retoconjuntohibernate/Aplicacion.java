package org.example.retoconjuntohibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.models.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;

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

    public static void loadFXModal(String view, String title, Integer anchura, Integer altura, Boolean resizable) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource(view));
            Scene scene = new Scene(fxmlLoader.load(), anchura, altura);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(resizable);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}