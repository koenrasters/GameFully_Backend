package gamefully.service.repositories;

import gamefully.service.models.Wishlist;
import gamefully.service.repositories.interfaces.IWishlist;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

public class WishlistRepository implements IWishlist
{
    public List<Wishlist> getWishlists() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Wishlist> result = session.createQuery("from Wishlist ", Wishlist.class).list();
            for (Wishlist wishlist : result)
            {
                Hibernate.initialize(wishlist.getUser());
                Hibernate.initialize(wishlist.getProducts());
            }

            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read wishlist from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public Wishlist getWishlist(int id) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Wishlist wishlist= session.get(Wishlist.class, id);
            Hibernate.initialize(wishlist.getUser());
            Hibernate.initialize(wishlist.getProducts());
            session.getTransaction().commit();
            return wishlist;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read wishlist with id " + id, e);
        }
        finally
        {
            session.close();
        }
    }

    public int getLatestWishlistId() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Integer> wishlistId = session.createQuery("select max(id) from Wishlist ").list();
            if(wishlistId.get(0) == null)
            {
                wishlistId.set(0,0);
            }
            int result = wishlistId.get(0) + 1;
            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read wishlist id from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public void createWishlist(Wishlist wishlist) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.save(wishlist);

            session.getTransaction().commit();

        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot create wishlist " + wishlist,e);
        }
        finally
        {
            session.close();
        }
    }

    public void deleteWishlist(int id) throws HibernateException
    {
        Wishlist wishlist = this.getWishlist(id);
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            session.delete(wishlist);

            session.getTransaction().commit();
        }
        catch (Exception e)
        {
            session.close();

            throw new HibernateException("Cannot delete wishlist", e);
        }
        finally
        {
            session.close();
        }
    }

    public boolean updateWishlist(Wishlist wishlist) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.update(wishlist);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot update wishlist", e);
        }
        finally
        {
            session.close();
        }
    }

}
