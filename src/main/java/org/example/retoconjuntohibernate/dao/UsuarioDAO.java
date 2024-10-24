package org.example.retoconjuntohibernate.dao;

import org.example.retoconjuntohibernate.models.Pelicula;
import org.example.retoconjuntohibernate.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la interfaz DAO y se encarga de manejar los usuarios en la base de datos
 */
public class UsuarioDAO implements DAO<Usuario> {
    private SessionFactory sF;

    public UsuarioDAO(SessionFactory sessionFactory) {
        this.sF = sessionFactory;
    }

    @Override
    public List<Usuario> findAll() {
        try (Session session = sF.openSession()) {
            return session.createQuery("SELECT u FROM Usuario u", Usuario.class).list();
        } catch (Exception e) {
            return new ArrayList<Usuario>(0);
        }
    }

    @Override
    public Usuario findByID(Integer id) {
        try (Session session = sF.openSession()) {
            return session.get(Usuario.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void insert(Usuario user) {
        sF.inTransaction( session -> session.persist(user));
    }

    @Override
    public void delete(Usuario user) {
        sF.inTransaction( session -> session.remove(user));
    }

    @Override
    public void update(Usuario user) {
        sF.inTransaction( session -> session.merge(user));
    }

    /**
     * Método que valida si un usuario y contraseña son correctos
     *
     * @param nombre    Email del usuario
     * @param contrasena Contraseña del usuario
     * @return Usuario si es correcto, null si no lo es
     */
    public Usuario validateUser(String nombre, String contrasena) {
        Usuario output;
        try (Session session = sF.openSession()) {
            output = session.createQuery("FROM Usuario WHERE nombre = :nombre AND contraseña = :contrasena", Usuario.class)
                    .setParameter("nombre", nombre)
                    .setParameter("contrasena", contrasena)
                    .uniqueResult();
        } catch (Exception e) {
            System.out.println("Error al validar el usuario: " + e.getMessage());
            return null;
        }
        return output;
    }

}
