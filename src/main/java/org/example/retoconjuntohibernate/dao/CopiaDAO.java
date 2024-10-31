package org.example.retoconjuntohibernate.dao;

import org.example.retoconjuntohibernate.models.Copia;
import org.example.retoconjuntohibernate.models.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa la interfaz DAO y se encarga de manejar las copias en la base de datos
 */
public class CopiaDAO implements DAO<Copia> {
    private SessionFactory sF = null;

    /**
     * Constructor de la clase
     * @param sessionFactory sesiones Hibernate
     */
    public CopiaDAO(SessionFactory sessionFactory) {
        this.sF = sessionFactory;
    }

    /**
     * Método que busca todas las copias de la base de datos
     * @return lista de copias
     */
    @Override
    public List<Copia> findAll() {
        try (Session session = sF.openSession()) {
            return session.createQuery("SELECT c FROM Copia c",Copia.class).list();
        } catch (Exception e) {
            return new ArrayList<Copia>(0);
        }
    }

    /**
     * Método que busca una copia por su id
     * @param id id de la copia
     * @return copia
     */
    @Override
    public Copia findByID(Integer id) {
        try (Session session = sF.openSession()) {
            return session.get(Copia.class, id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método que inserta una copia en la base de datos
     * @param copia copia a insertar
     */
    @Override
    public void insert(Copia copia) {
        sF.inTransaction( session -> session.persist(copia));
    }

    /**
     * Método que elimina una copia de la base de datos
     * @param copia copia a eliminar
     */
    @Override
    public void delete(Copia copia) {
        sF.inTransaction( session -> session.remove(copia));
    }

    /**
     * Método que actualiza una copia en la base de datos
     * @param copia copia a actualizar
     */
    @Override
    public void update(Copia copia) {
        sF.inTransaction( session -> session.merge(copia));
    }

    /**
     * Método que busca la lista de copias de peliculas que tiene un usuario
     * @param id id del usuario
     * @return lista de copias que tiene el usuario
     */
    public ArrayList<Copia> findCopiasByUserID(Integer id) {
        ArrayList<Copia> resultado = new ArrayList<>();
         try (Session session = sF.openSession()) {
             List<Copia> lista = session.createQuery("FROM Copia WHERE idUsuario.id = :id", Copia.class)
                     .setParameter("id", id)
                     .list();
             resultado.addAll(lista);
         } catch (Exception e) {
             System.out.println(e.getMessage());
             return new ArrayList<Copia>(0);
         }
        return resultado;
    }

    /**
     * Método que cuenta las copias de peliculas que tiene un usuario
     * @param user usuario
     * @return número de copias que tiene el usuario
     */
    public Long countCopyByUser(Usuario user) {
        try (Session session = sF.openSession()) {
            return session.createQuery("SELECT COUNT(c) FROM Copia c WHERE c.idUsuario = :user", Long.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return 0L;
    }
}
