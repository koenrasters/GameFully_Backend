package gamefully.service.repositories;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateManager
{
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();

        }
        catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private HibernateManager(){}

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
