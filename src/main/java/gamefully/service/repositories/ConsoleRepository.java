package gamefully.service.repositories;

import gamefully.service.models.Console;
import gamefully.service.repositories.interfaces.IConsole;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ConsoleRepository implements IConsole
{
    public Console getConsole(int id) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Console console = session.get(Console.class, id);
            Hibernate.initialize(console.getWishlists());
            Hibernate.initialize(console.getCartItems());
            Hibernate.initialize(console.getTransactionItems());
            Hibernate.initialize(console.getStock());

            session.getTransaction().commit();
            return console;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read console with id " + id, e);
        }
        finally
        {
            session.close();
        }
    }

    public List<Console> getConsoles(String searchTerm) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Query<Console> query = session.createQuery("from Console " + searchTerm, Console.class);
            List<Console> result = query.list();
            for(Console console : result)
            {
                Hibernate.initialize(console.getWishlists());
                Hibernate.initialize(console.getCartItems());
                Hibernate.initialize(console.getTransactionItems());
                Hibernate.initialize(console.getStock());
            }
            session.getTransaction().commit();
            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read console from database with search " + searchTerm, e);
        }
        finally
        {
            session.close();
        }
    }

    public int getLatestConsoleId() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Integer> consoleId = session.createQuery("select max(id) from Console").list();
            if(consoleId.get(0) == null)
            {
                consoleId.set(0,0);
            }
            int result = consoleId.get(0) + 1;
            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read console id from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public List<String> getColumns(String columm) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            String consoleQuery = " from console Inner join product on console.id = product.id";
            session.beginTransaction();

            Query<String> query = session.createSQLQuery("select distinct " + columm + consoleQuery);
            List<String> distinctList = query.list();
            session.getTransaction().commit();
            return distinctList;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cant get columns", e);
        }
        finally
        {
            session.close();
        }
    }

    public void createConsole(Console console) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.save(console);

            session.getTransaction().commit();

        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot create console " + console,e);
        }
        finally
        {
            session.close();
        }
    }

    public boolean updateConsole(Console console) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.update(console);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot update console", e);
        }
        finally
        {
            session.close();
        }
    }
}
