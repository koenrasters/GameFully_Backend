package gamefully.service.repositories;

import gamefully.service.models.Accessory;
import gamefully.service.repositories.interfaces.IAccessory;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AccessoryRepository implements IAccessory
{
    public List<String> getColumns(String columm) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            String accessoryQuery = " from accessory Inner join product on accessory.id = product.id";
            session.beginTransaction();

            Query<String> query = session.createSQLQuery("select distinct " + columm + accessoryQuery);
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

    public Accessory getAccessory(int id) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Accessory accessory = session.get(Accessory.class, id);
            Hibernate.initialize(accessory.getWishlists());
            Hibernate.initialize(accessory.getCartItems());
            Hibernate.initialize(accessory.getTransactionItems());
            Hibernate.initialize(accessory.getStock());
            session.getTransaction().commit();
            return accessory;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read accessory with id " + id, e);
        }
        finally
        {
            session.close();
        }
    }

    public List<Accessory> getAccessories(String searchTerm) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Query<Accessory> query = session.createQuery("from Accessory " + searchTerm, Accessory.class);
            List<Accessory> result = query.list();
            for(Accessory accessory : result)
            {
                Hibernate.initialize(accessory.getWishlists());
                Hibernate.initialize(accessory.getCartItems());
                Hibernate.initialize(accessory.getTransactionItems());
                Hibernate.initialize(accessory.getStock());
            }

            session.getTransaction().commit();
            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read accessory from database with search " + searchTerm, e);
        }
        finally
        {
            session.close();
        }
    }

    public int getLatestAccessoryId() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Integer> accessoryId = session.createQuery("select max(id) from Accessory ").list();
            if(accessoryId.get(0) == null)
            {
                accessoryId.set(0,0);
            }
            int result = accessoryId.get(0) + 1;
            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read accessory id from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public void createAccessory(Accessory accessory) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.save(accessory);

            session.getTransaction().commit();

        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot create accessory " + accessory,e);
        }
        finally
        {
            session.close();
        }
    }

    public boolean updateAccessory(Accessory accessory) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.update(accessory);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot update accessory", e);
        }
        finally
        {
            session.close();
        }
    }
}
