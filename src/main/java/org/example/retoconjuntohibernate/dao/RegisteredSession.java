package org.example.retoconjuntohibernate.dao;

import lombok.Data;
import org.example.retoconjuntohibernate.models.Copia;
import org.example.retoconjuntohibernate.models.Usuario;

import java.time.Duration;

public class RegisteredSession {
    public static Usuario user;
    public static Copia copiaSeleccionada;

    public static Double volumen = 0.5;
}
