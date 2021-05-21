package gamefully.service.repositories;

import gamefully.service.models.Cart;
import gamefully.service.repositories.interfaces.ICart;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

public class CartRepository implements ICart
{
    public List<Cart> getCarts() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Cart> result = session.createQuery("from Cart ", Cart.class).list();
            for (Cart cart : result)
            {
                Hibernate.initialize(cart.getUser());
                Hibernate.initialize(cart.getCartItems());
            }

            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read cart from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public Cart getCart(int id) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Cart cart= session.get(Cart.class, id);
            Hibernate.initialize(cart.getUser());
            Hibernate.initialize(cart.getCartItems());
            session.getTransaction().commit();
            return cart;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read cart with id " + id, e);
        }
        finally
        {
            session.close();
        }
    }

    public int getLatestCartId() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Integer> cartId = session.createQuery("select max(id) from Cart ").list();
            if(cartId.get(0) == null)
            {
                cartId.set(0,0);
            }
            int result = cartId.get(0) + 1;
            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read cart id from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public void createCart(Cart cart) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.save(cart);

            session.getTransaction().commit();

        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot create cart " + cart,e);
        }
        finally
        {
            session.close();
        }
    }

    public void deleteCart(int id) throws HibernateException
    {

        Cart cart = this.getCart(id);
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            session.delete(cart);

            session.getTransaction().commit();
        }
        catch (Exception e)
        {
            session.close();

            throw new HibernateException("Cannot delete accessory", e);
        }
        finally
        {
            session.close();
        }
    }

    public boolean updateCart(Cart cart) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.update(cart);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot update cart", e);
        }
        finally
        {
            session.close();
        }
    }
}
