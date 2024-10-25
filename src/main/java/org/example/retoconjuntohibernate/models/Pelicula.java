package org.example.retoconjuntohibernate.models;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

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
}