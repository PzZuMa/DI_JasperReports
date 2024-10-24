package org.example.retoconjuntohibernate.dao;

import org.example.retoconjuntohibernate.models.Pelicula;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Referenceable;

/**
 * Clase que implementa la interfaz DAO y se encarga de manejar las películas en la base de datos
 */
public class PeliculaDAO implements DAO<Pelicula> {
    private SessionFactory sF;

    public PeliculaDAO(SessionFactory sessionFactory) {
        this.sF = sessionFactory;
    }

    @Override
    public List<Pelicula> findAll() {
        try (Session session = sF.openSession()) {
            return session.createQuery("SELECT p FROM Pelicula p", Pelicula.class).list();
        } catch (Exception e) {
            return new ArrayList<Pelicula>(0);
        }
    }

    @Override
    public Pelicula findByID(Integer id) {
        try (Session session = sF.openSession()) {
            return session.get(Pelicula.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void insert(Pelicula peli) {
        sF.inTransaction( session -> session.persist(peli));
    }

    @Override
    public void delete(Pelicula peli) {
        sF.inTransaction( session -> session.remove(peli));
    }

    @Override
    public void update(Pelicula peli) {
        sF.inTransaction( session -> session.merge(peli));
    }

}
