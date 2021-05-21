package gamefully.service.repositories;

import gamefully.service.models.Stock;
import gamefully.service.repositories.interfaces.IStock;

import java.util.ArrayList;
import java.util.List;

public class StockRepositoryMem implements IStock
{
    private final FakeDataStore fakeDataStore = new FakeDataStore();

    public List<Stock> getStocks()
    {
        return fakeDataStore.getStockList();
    }

    public Stock getStock(int id)
    {
        return fakeDataStore.getStock(id);
    }

    public List<Stock> getStocks(String searchTerm, String column)
    {
        List<Stock> stocks = new ArrayList<>();
        Stock stock = fakeDataStore.getStock(1);
        stocks.add(stock);
        return stocks;
    }

    public void createStock(Stock stock)
    {
        fakeDataStore.addStock(stock);
    }

    public void deleteStock(int id)
    {
        fakeDataStore.deleteStock(id);
    }

    public boolean updateStock(Stock stock)
    {
        return fakeDataStore.updateStock(stock);
    }
}
