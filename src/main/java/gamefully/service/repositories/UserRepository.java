package gamefully.service.repositories;

import gamefully.service.models.User;
import gamefully.service.repositories.interfaces.IUser;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepository implements IUser
{
    public List<User> getUsers() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<User> result = session.createQuery("from User ", User.class).list();
            for (User user : result)
            {
                Hibernate.initialize(user.getCart());
                Hibernate.initialize(user.getWishlist());
                Hibernate.initialize(user.getTransactions());
            }

            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read user from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public User getUser(int id) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            User result= session.get(User.class, id);
            Hibernate.initialize(result.getCart());
            Hibernate.initialize(result.getWishlist());
            Hibernate.initialize(result.getTransactions());
            session.getTransaction().commit();
            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read user with id " + id, e);
        }
        finally
        {
            session.close();
        }
    }

    public User getUser(String mail) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where email = :searchterm", User.class)
                    .setParameter("searchterm", mail);
            User result = query.list().get(0);
            session.getTransaction().commit();
            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read user with email " + mail, e);
        }
        finally
        {
            session.close();
        }
    }

    public List<User> getUsers(String searchTerm, String column) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User where " + column + " like :searchterm", User.class)
                    .setParameter("searchterm", "%" + searchTerm + "%");
            List<User> result = query.list();
            for (User user : result)
            {
                Hibernate.initialize(user.getCart());
                Hibernate.initialize(user.getWishlist());
                Hibernate.initialize(user.getTransactions());
            }
            session.getTransaction().commit();
            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read user from database with search " + searchTerm + " in column " + column, e);
        }
        finally
        {
            session.close();
        }
    }

    public int getLatestUserId() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Integer> userId = session.createQuery("select max(id) from User ").list();
            if(userId.get(0) == null)
            {
                userId.set(0,0);
            }
            int result = userId.get(0) + 1;
            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read user id from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public void createUser(User user) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();

        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot create user " + user,e);
        }
        finally
        {
            session.close();
        }
    }

    public void deleteUser(int id) throws HibernateException
    {
        User user = this.getUser(id);
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            session.delete(user);

            session.getTransaction().commit();
        }
        catch (Exception e)
        {
            session.close();

            throw new HibernateException("Cannot delete user", e);
        }
        finally
        {
            session.close();
        }
    }

    public boolean updateUser(User user) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot update user", e);
        }
        finally
        {
            session.close();
        }
    }

    public String getRole(String mail) throws HibernateException
    {
        User user = getUser(mail);
        if(user.getAdmin()==1)
        {
            return "ADMIN";
        }
        else
        {
            return "USER";
        }
    }


}
