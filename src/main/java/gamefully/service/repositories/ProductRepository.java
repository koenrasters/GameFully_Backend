package gamefully.service.repositories;

import gamefully.service.models.Product;
import gamefully.service.repositories.interfaces.IProduct;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ProductRepository implements IProduct
{
    public List<Product> getProducts(String searchTerm) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            Query<Product> query = session.createQuery("from Product " + searchTerm, Product.class);
            List<Product> searchedList = query.list();
            for(Product product : searchedList)
            {
                Hibernate.initialize(product.getWishlists());
                Hibernate.initialize(product.getCartItems());
                Hibernate.initialize(product.getTransactionItems());
                Hibernate.initialize(product.getStock());
            }

            session.getTransaction().commit();
            return searchedList;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read product from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public Product getProduct(int id) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Product product = session.get(Product.class, id);
            Hibernate.initialize(product.getWishlists());
            Hibernate.initialize(product.getCartItems());
            Hibernate.initialize(product.getTransactionItems());
            Hibernate.initialize(product.getStock());
            session.getTransaction().commit();
            return product;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read product with id " + id, e);
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
            session.beginTransaction();

            Query<String> query = session.createSQLQuery("select distinct " + columm +" from product ");
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

    public void deleteProduct(int id) throws HibernateException
    {
        Product product = this.getProduct(id);
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            session.delete(product);

            session.getTransaction().commit();
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot delete product", e);
        }
        finally
        {
            session.close();
        }
    }

    public int getLatestProductId() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Integer> productId = session.createQuery("select max(id) from Product ").list();
            if(productId.get(0) == null)
            {
                productId.set(0,0);
            }
            int result = productId.get(0) + 1;
            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read product id from database",e);
        }
        finally
        {
            session.close();
        }
    }


}
