package org.example.retoconjuntohibernate.dao;

import org.example.retoconjuntohibernate.models.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase que implementa la interfaz DAO y se encarga de manejar los usuarios en la base de datos
 */
public class UsuarioDAO implements DAO<Usuario> {
    private static Connection connection = null;

    private static final String INSERT_INTO_USER = "INSERT INTO User (email, password, is_admin) values (?,?,?);";
    private static final String UPDATE_USER = "UPDATE User set email = ?, password = ?, is_admin = ? where id = ?;";
    private static final String DELETE_USER = "DELETE FROM User where id = ?;";

    public UsuarioDAO(Connection conn) {
        connection = conn;
    }

    @Override
    public ArrayList<Usuario> findAll() {
        ArrayList<Usuario> resultado = new ArrayList<>();

        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Usuario");

            while (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setContraseña(rs.getString("contraseña"));

                resultado.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }

    @Override
    public Usuario findByID(Integer id) {
        Usuario user = new Usuario();
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Usuario WHERE id = " + id);
            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setContraseña(rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void insert(Usuario user) {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_INTO_USER)) {
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getContraseña());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_USER)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Usuario usuario) {
        try (PreparedStatement ps = connection.prepareStatement(UPDATE_USER)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getContraseña());
            ps.setInt(3, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método que valida si un usuario y contraseña son correctos
     *
     * @param email    Email del usuario
     * @param password Contraseña del usuario
     * @return Usuario si es correcto, null si no lo es
     */
    public Usuario validateUser(String email, String password) {
        Usuario output = null;

        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM Usuario WHERE nombre=? AND contraseña=?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                output = new Usuario();
                output.setId(rs.getInt("id"));
                output.setNombre(rs.getString("nombre"));
                output.setContraseña(rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

}
