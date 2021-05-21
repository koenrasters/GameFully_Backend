package gamefully.service.repositories;

import gamefully.service.models.Product;
import gamefully.service.repositories.interfaces.IProduct;

import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryMem implements IProduct
{
    private final FakeDataStore fakeDataStore = new FakeDataStore();

    public List<Product> getProducts()
    {
        return fakeDataStore.getProducts();
    }

    public List<Product> getProducts(String searchTerm) throws HibernateException
    {
        return fakeDataStore.getProducts();
    }

    public int getLatestProductId() throws HibernateException
    {
        return 0;
    }

    public Product getProduct(int id)
    {
        return fakeDataStore.getProduct(id);
    }

    public void deleteProduct(int id)
    {
        fakeDataStore.deleteProduct(id);
    }

    public List<String> getColumns(String columm) throws HibernateException
    {
        return new ArrayList<>();
    }
}
