package org.example.retoconjuntohibernate.models;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una película
 */
@Data
@Entity
@Table(name = "Pelicula")
public class Pelicula implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String genero;
    private Integer año;
    private String descripcion;
    private String director;
    private String bso;
    private String poster;

    @OneToMany
    private List<Copia> copiasPelis = new ArrayList<>();

    public void addCopy(Copia copia) {
        copia.setIdPelicula(this);
        this.copiasPelis.add(copia);
    }

    @Override
    public String toString(){
        return id + " - " + titulo + " - " + genero + " - " + año + " - " + descripcion + " - " + director + " - " + bso + " - " + poster;
    }
}