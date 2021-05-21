package gamefully.service.repositories;

import gamefully.service.models.Stock;
import gamefully.service.repositories.interfaces.IStock;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class StockRepository implements IStock
{
    public List<Stock> getStocks() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Stock> result = session.createQuery("from Stock ", Stock.class).list();
            for (Stock stock : result)
            {
                Hibernate.initialize(stock.getProduct());
            }

            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read stock from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public Stock getStock(int id) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Stock stock= session.get(Stock.class, id);
            Hibernate.initialize(stock.getProduct());
            session.getTransaction().commit();
            return stock;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read stock with id " + id, e);
        }
        finally
        {
            session.close();
        }
    }

    public List<Stock> getStocks(String searchTerm, String column) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Query<Stock> query = session.createQuery("from Stock where " + column + " like :searchterm", Stock.class)
                    .setParameter("searchterm", "%" + searchTerm + "%");
            List<Stock> result = query.list();
            for (Stock stock : result)
            {
                Hibernate.initialize(stock.getProduct());
            }
            session.getTransaction().commit();
            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read Stock from database with search " + searchTerm + " in column " + column, e);
        }
        finally
        {
            session.close();
        }
    }

    public int getLatestStockId() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Integer> stockId = session.createQuery("select max(id) from Stock ").list();
            if(stockId.get(0) == null)
            {
                stockId.set(0,0);
            }
            int result = stockId.get(0) + 1;
            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read stock id from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public void createStock(Stock stock) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.save(stock);

            session.getTransaction().commit();

        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot create stock " + stock,e);
        }
        finally
        {
            session.close();
        }
    }

    public void deleteStock(int id) throws HibernateException
    {
        Stock stock = this.getStock(id);
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            session.delete(stock);

            session.getTransaction().commit();
        }
        catch (Exception e)
        {
            session.close();

            throw new HibernateException("Cannot delete stock", e);
        }
        finally
        {
            session.close();
        }
    }

    public boolean updateStock(Stock stock) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.update(stock);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot update stock", e);
        }
        finally
        {
            session.close();
        }
    }
}
