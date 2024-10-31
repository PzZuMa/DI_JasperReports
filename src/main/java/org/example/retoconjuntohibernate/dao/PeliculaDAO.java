package org.example.retoconjuntohibernate.dao;

import org.example.retoconjuntohibernate.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la interfaz DAO y se encarga de manejar las películas en la base de datos
 */
public class PeliculaDAO implements DAO<Pelicula> {
    private SessionFactory sF;

    /**
     * Constructor de la clase
     * @param sessionFactory sesiones Hibernate
     */
    public PeliculaDAO(SessionFactory sessionFactory) {
        this.sF = sessionFactory;
    }

    /**
     * Método que busca todas las películas de la base de datos
     * @return lista de películas
     */
    @Override
    public List<Pelicula> findAll() {
        try (Session session = sF.openSession()) {
            return session.createQuery("SELECT p FROM Pelicula p", Pelicula.class).list();
        } catch (Exception e) {
            return new ArrayList<Pelicula>(0);
        }
    }

    /**
     * Método que busca una película por su id
     * @param id id de la película
     * @return película
     */
    @Override
    public Pelicula findByID(Integer id) {
        try (Session session = sF.openSession()) {
            return session.get(Pelicula.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método que inserta una película en la base de datos
     * @param peli película a insertar
     */
    @Override
    public void insert(Pelicula peli) {
        sF.inTransaction( session -> session.persist(peli));
    }

    /**
     * Método que elimina una película de la base de datos
     * @param peli película a eliminar
     */
    @Override
    public void delete(Pelicula peli) {
        sF.inTransaction( session -> session.remove(peli));
    }

    /**
     * Método que actualiza una película en la base de datos
     * @param peli película a actualizar
     */
    @Override
    public void update(Pelicula peli) {
        sF.inTransaction( session -> session.merge(peli));
    }

    /**
     * Método que busca una película por su nombre
     * @param name nombre de la película
     * @return película
     */
    public Pelicula findPeliByName(String name) {
        try (Session session = sF.openSession()) {
            return session.createQuery("SELECT p FROM Pelicula p WHERE p.titulo = :name", Pelicula.class)
                    .setParameter("name", name)
                    .list().getFirst();
        } catch (Exception e) {
            System.out.println("No se ha encontrado la pelicula");
        }
        return null;
    }
}
