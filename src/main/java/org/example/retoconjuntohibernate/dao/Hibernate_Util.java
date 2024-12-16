package org.example.retoconjuntohibernate.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase que se encarga de crear la factoría de sesiones de Hibernate
 */
public class Hibernate_Util {
    private static final SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration()
                .configure()
                .setProperty("hibernate.connection.password", System.getenv("MYSQL_ROOT_PASSWORD"))
                .setProperty("hibernate.connection.username", System.getenv("MYSQL_USER"))
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
