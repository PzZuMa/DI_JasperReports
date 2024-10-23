package org.example.retoconjuntohibernate.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "Pelicula")
public class Pelicula {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "titulo", length = 50)
    private String titulo;

    @Column(name = "genero", length = 50)
    private String genero;

    @Column(name = "`año`")
    private Integer año;

    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @Column(name = "director", length = 50)
    private String director;

}