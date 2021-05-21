package gamefully.service.repositories.interfaces;

import gamefully.service.models.Stock;
import gamefully.service.repositories.HibernateException;

import java.util.List;

public interface IStock
{
    List<Stock> getStocks() throws HibernateException;
    Stock getStock(int id) throws HibernateException;
    List<Stock> getStocks(String searchTerm, String column) throws HibernateException;
    void createStock(Stock stock) throws HibernateException;
    void deleteStock(int id) throws HibernateException;
    boolean updateStock(Stock stock) throws HibernateException;

}
