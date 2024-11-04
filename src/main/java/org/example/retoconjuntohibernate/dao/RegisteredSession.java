package org.example.retoconjuntohibernate.dao;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.example.retoconjuntohibernate.models.Copia;
import org.example.retoconjuntohibernate.models.Usuario;

import java.io.File;

/**
 * Clase que guarda la sesión actual del usuario
 */
public class RegisteredSession {
    public static Usuario user;
    public static Copia copiaSeleccionada;


    public static Double volumen = 0.5;

    private static final String soundPath = "src/main/resources/org/example/retoconjuntohibernate/media/boton.mp3";
    public static final MediaPlayer mpBtn = createMediaPlayer(soundPath);

    /**
     * Crea un reproductor de sonido a partir de un archivo
     *
     * @param filePath ruta del archivo
     * @return reproductor de sonido
     */
    private static MediaPlayer createMediaPlayer(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Media media = new Media(file.toURI().toString());
            return new MediaPlayer(media);
        } else {
            System.err.println("Sonido no encontrado: " + filePath);
            return null;
        }
    }

    public static void playButtonSound() {
        if (mpBtn != null) {
            mpBtn.seek(mpBtn.getStartTime()); // Reset to start
            mpBtn.play();
        }
    }
}
