package gamefully.service.repositories.interfaces;

import gamefully.service.models.Product;
import gamefully.service.repositories.HibernateException;

import java.util.List;

public interface IProduct
{
    List<Product> getProducts(String searchTerm) throws HibernateException;
    Product getProduct(int id) throws HibernateException;
    void deleteProduct(int id) throws HibernateException;
    int getLatestProductId() throws HibernateException;
    List<String> getColumns(String columm) throws HibernateException;
}
