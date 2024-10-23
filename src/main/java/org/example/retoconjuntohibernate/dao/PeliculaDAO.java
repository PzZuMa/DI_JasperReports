package org.example.retoconjuntohibernate.dao;

import org.example.retoconjuntohibernate.models.Pelicula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase que implementa la interfaz DAO y se encarga de manejar las películas en la base de datos
 */
public class PeliculaDAO implements DAO<Pelicula> {
    private static Connection connection = null;

    private static final String INSERT_INTO_FILM = "INSERT INTO Pelicula (titulo, genero, año, descripcion, director) values (?,?,?,?,?);";
    private static final String UPDATE_FILM = "UPDATE Pelicula set titulo = ?, genero = ?, año = ?, descripcion = ?, director = ? where id = ?;";
    private static final String DELETE_FILM = "DELETE FROM Pelicula where id = ?;";

    public PeliculaDAO(Connection conn) {
        connection = conn;
    }

    @Override
    public ArrayList<Pelicula> findAll() {
        ArrayList<Pelicula> resultado = new ArrayList<>();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Pelicula");

            while (rs.next()){
                Pelicula peli = new Pelicula();
                peli.setId(rs.getInt("id"));
                peli.setTitulo(rs.getString("titulo"));
                peli.setGenero(rs.getString("genero"));
                peli.setAño(rs.getInt("año"));
                peli.setDescripcion(rs.getString("descripcion"));
                peli.setDirector(rs.getString("director"));

                resultado.add(peli);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    @Override
    public Pelicula findByID(Integer id) {
        Pelicula peli = new Pelicula();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Pelicula WHERE id = " + id);

            while (rs.next()){
                peli.setId(rs.getInt("id"));
                peli.setTitulo(rs.getString("titulo"));
                peli.setGenero(rs.getString("genero"));
                peli.setAño(rs.getInt("año"));
                peli.setDescripcion(rs.getString("descripcion"));
                peli.setDirector(rs.getString("director"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return peli;
    }

    @Override
    public void insert(Pelicula peli) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_INTO_FILM)) {
            ps.setString(1, peli.getTitulo());
            ps.setString(2, peli.getGenero());
            ps.setInt(3, peli.getAño());
            ps.setString(4, peli.getDescripcion());
            ps.setString(5, peli.getDirector());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_FILM)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Pelicula pelicula) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_FILM)) {
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getGenero());
            ps.setInt(3, pelicula.getAño());
            ps.setString(4, pelicula.getDescripcion());
            ps.setString(5, pelicula.getDirector());
            ps.setInt(6, pelicula.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
