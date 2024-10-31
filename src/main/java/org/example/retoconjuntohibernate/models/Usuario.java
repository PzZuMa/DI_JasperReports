package org.example.retoconjuntohibernate.models;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un usuario
 */
@Data
@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String contraseña;

    @OneToMany
    List<Copia> copiasUser = new ArrayList<>();

    public void addCopy(Copia copia) {
        copia.setIdUsuario(this);
        this.copiasUser.add(copia);
    }

    @Override
    public String toString() {
        return id + " - " + nombre + " - " + contraseña;
    }
}