package gamefully.service.repositories.interfaces;

import gamefully.service.models.Transaction;
import gamefully.service.repositories.HibernateException;

import java.util.List;

public interface ITransaction
{
    List<Transaction> getTransactions() throws HibernateException;
    Transaction getTransaction(int id) throws HibernateException;
    List<Transaction> getTransactions(String searchTerm, String column) throws HibernateException;
    void createTransaction(Transaction transaction) throws HibernateException;
    void deleteTransaction(int id) throws HibernateException;
    boolean updateTransaction(Transaction transaction) throws HibernateException;
}
