package gamefully.service.repositories;

import gamefully.service.models.Transaction;
import gamefully.service.repositories.interfaces.ITransaction;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TransactionRepository implements ITransaction
{
    public List<Transaction> getTransactions() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Transaction> result = session.createQuery("from Transaction ", Transaction.class).list();
            for (Transaction transaction : result)
            {
                Hibernate.initialize(transaction.getUser());
                Hibernate.initialize(transaction.getTransactionItems());
            }

            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read transaction from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public Transaction getTransaction(int id) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Transaction transaction= session.get(Transaction.class, id);
            Hibernate.initialize(transaction.getUser());
            Hibernate.initialize(transaction.getTransactionItems());
            session.getTransaction().commit();
            return transaction;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read transaction with id " + id, e);
        }
        finally
        {
            session.close();
        }
    }

    public List<Transaction> getTransactions(String searchTerm, String column) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            Query<Transaction> query = session.createQuery("from Transaction where " + column + " = :searchterm", Transaction.class)
                    .setParameter("searchterm", searchTerm);
            List<Transaction> result = query.list();
            for (Transaction transaction : result)
            {
                Hibernate.initialize(transaction.getUser());
                Hibernate.initialize(transaction.getTransactionItems());
            }
            session.getTransaction().commit();
            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read Transaction from database with search " + searchTerm + " in column " + column, e);
        }
        finally
        {
            session.close();
        }
    }

    public int getLatestTransactionId() throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            List<Integer> transactionId = session.createQuery("select max(id) from Transaction ").list();
            if(transactionId.get(0) == null)
            {
                transactionId.set(0,0);
            }
            int result = transactionId.get(0) + 1;
            session.getTransaction().commit();

            return result;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot read transaction id from database",e);
        }
        finally
        {
            session.close();
        }
    }

    public void createTransaction(Transaction transaction) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try {
            session.beginTransaction();

            session.save(transaction);

            session.getTransaction().commit();

        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot create transaction " + transaction,e);
        }
        finally
        {
            session.close();
        }
    }

    public void deleteTransaction(int id) throws HibernateException
    {
        Transaction transaction = this.getTransaction(id);
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();

            session.delete(transaction);

            session.getTransaction().commit();
        }
        catch (Exception e)
        {
            session.close();

            throw new HibernateException("Cannot delete transaction", e);
        }
        finally
        {
            session.close();
        }
    }

    public boolean updateTransaction(Transaction transaction) throws HibernateException
    {
        Session session = HibernateManager.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.update(transaction);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e)
        {
            session.close();
            throw new HibernateException("Cannot update transaction", e);
        }
        finally
        {
            session.close();
        }
    }
}
