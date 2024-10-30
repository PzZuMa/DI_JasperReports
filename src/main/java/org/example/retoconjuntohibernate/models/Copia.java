package org.example.retoconjuntohibernate.models;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Copia")
public class Copia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula idPelicula;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    private String estado;
    private String soporte;

    @Override
    public String toString() {
        return id + " - " + idPelicula.getTitulo() + " - " + estado + " - " + soporte;
    }

}