package org.example.retoconjuntohibernate.dao;

import org.example.retoconjuntohibernate.models.Copia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase que implementa la interfaz DAO y se encarga de manejar las copias en la base de datos
 */
public class CopiaDAO implements DAO<Copia> {
    private static Connection connection = null;

    private static final String INSERT_INTO_COPIA = "INSERT INTO Copia (id_pelicula, id_usuario, estado, soporte) values (?,?,?,?);";
    private static final String UPDATE_COPIA = "UPDATE Copia set id_pelicula = ?, id_usuario = ?, estado = ?, soporte = ? where id = ?;";
    private static final String DELETE_COPIA = "DELETE FROM Copia where id = ?;";

    public CopiaDAO(Connection conn) {
        connection = conn;
    }

    @Override
    public ArrayList<Copia> findAll() {
        ArrayList<Copia> resultado = new ArrayList<>();

        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Copia");

            while (rs.next()){
                Copia copia = new Copia();
                copia.setId(rs.getInt("id"));
                copia.setId_pelicula(rs.getInt("id_pelicula"));
                copia.setId_usuario(rs.getInt("id_usuario"));
                copia.setEstado(rs.getString("estado"));
                copia.setSoporte(rs.getString("soporte"));

                resultado.add(copia);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }

    @Override
    public Copia findByID(Integer id) {
        Copia copia = new Copia();

        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Copia WHERE id = " + id);

            while (rs.next()){
                copia.setId(rs.getInt("id"));
                copia.setId_pelicula(rs.getInt("id_pelicula"));
                copia.setId_usuario(rs.getInt("id_usuario"));
                copia.setEstado(rs.getString("estado"));
                copia.setSoporte(rs.getString("soporte"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return copia;
    }

    @Override
    public void insert(Copia copy) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_INTO_COPIA)) {
            ps.setInt(1, copy.getId_pelicula());
            ps.setInt(2, copy.getId_usuario());
            ps.setString(3, copy.getEstado());
            ps.setString(4, copy.getSoporte());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_COPIA)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Copia copia) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_COPIA)) {
            ps.setInt(1, copia.getId_pelicula());
            ps.setInt(2, copia.getId_usuario());
            ps.setString(3, copia.getEstado());
            ps.setString(4, copia.getSoporte());
            ps.setInt(5, copia.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que busca la lista de copias de peliculas que tiene un usuario
     * @param id id del usuario
     * @return lista de copias que tiene el usuario
     */
    public ArrayList<Copia> findPelisByUser(Integer id) {
        ArrayList<Copia> resultado = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Copia WHERE id_usuario = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Copia copia = new Copia();
                copia.setId(rs.getInt("id"));
                copia.setId_pelicula(rs.getInt("id_pelicula"));
                copia.setId_usuario(rs.getInt("id_usuario"));
                copia.setEstado(rs.getString("estado"));
                copia.setSoporte(rs.getString("soporte"));

                resultado.add(copia);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

}
