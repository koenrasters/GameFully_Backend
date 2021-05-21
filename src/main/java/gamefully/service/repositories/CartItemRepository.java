package gamefully.service.repositories;

import gamefully.service.models.CartItem;
import gamefully.service.repositories.interfaces.ICartItem;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CartItemRepository implements ICartItem {

    public void deleteCartItemsByProductId(int id) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Query<CartItem> query = session.createQuery("delete from CartItem where product.id= :id").setParameter("id", id);
            query.executeUpdate();
            session.getTransaction().commit();
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot delete cartitem from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public CartItem getCartItem(int id) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            CartItem cart= session.get(CartItem.class, id);
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

    public void addCartItem(CartItem cartItem) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.save(cartItem);

            session.getTransaction().commit();

        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot create cart item" + cartItem,e);
        }
        finally
        {
            session.close();
        }
    }

    public void deleteCartItem(int id) throws HibernateException
    {

        CartItem cartItem = this.getCartItem(id);
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            session.delete(cartItem);

            session.getTransaction().commit();
        }
        catch (Exception e)
        {
            session.close();

            throw new HibernateException("Cannot delete cart item", e);
        }
        finally
        {
            session.close();
        }
    }

    public boolean updateCartItem(CartItem cartItem) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.update(cartItem);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot update cartItem", e);
        }
        finally
        {
            session.close();
        }
    }

    public int getLatestCartItemId() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Integer> id = session.createQuery("select max(id) from CartItem").list();
            if(id.get(0) == null)
            {
                id.set(0,0);
            }
            int result = id.get(0) + 1;
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
}
