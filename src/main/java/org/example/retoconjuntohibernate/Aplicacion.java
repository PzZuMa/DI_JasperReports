package org.example.retoconjuntohibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación
 */
public class Aplicacion extends Application {

    private static Stage ventana;

    /**
     * Inicia la aplicación cargando la vista de login
     *
     * @param stage la ventana principal de la aplicación
     */
    @Override
    public void start(Stage stage){
        ventana = stage;
        loadFXML("views/login-view.fxml", "Login", 250, 350, false);
        stage.show();
    }

    /**
     * Carga una vista FXML en la ventana principal
     *
     * @param view la vista a cargar
     * @param title el título de la ventana
     * @param anchura la anchura de la ventana
     * @param altura la altura de la ventana
     * @param resizable si la ventana es redimensionable
     */
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

        //Hace que la ventana se centre en la pantalla
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        ventana.setX((screenBounds.getWidth() - anchura) / 2);
        ventana.setY((screenBounds.getHeight() - altura) / 2);
    }

    /**
     * Carga una vista FXML en una ventana modal
     *
     * @param view la vista a cargar
     * @param title el título de la ventana
     * @param anchura la anchura de la ventana
     * @param altura la altura de la ventana
     * @param resizable si la ventana es redimensionable
     */
    public static void loadFXModal(String view, String title, Integer anchura, Integer altura, Boolean resizable) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource(view));
            Scene scene = new Scene(fxmlLoader.load(), anchura, altura);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.setResizable(resizable);
            stage.initModality(Modality.APPLICATION_MODAL);

            //Hace que la ventana se centre en la pantalla
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((screenBounds.getWidth() - anchura) / 2);
            stage.setY((screenBounds.getHeight() - altura) / 2);

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método principal de la aplicación
     *
     * @param args los argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        launch();
    }
}