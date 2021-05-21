package gamefully.service.managers;

import gamefully.service.Category;
import gamefully.service.models.*;
import gamefully.service.repositories.CartItemRepository;
import gamefully.service.repositories.HibernateException;
import gamefully.service.repositories.interfaces.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductsManager
{
    private IProduct productRepository;
    private IGame gameRepository;
    private IAccessory accessoryRepository;
    private IConsole consoleRepository;
    private IStock stockRepository;
    private ICartItem cartItemRepository = new CartItemRepository();

    private static final Logger logger = Logger.getLogger(ProductsManager.class.getName());

    public ProductsManager(DataAccess dataAccess)
    {
        this.productRepository = dataAccess.getProductRepository();
        this.gameRepository = dataAccess.getGameRepository();
        this.accessoryRepository = dataAccess.getAccessoryRepository();
        this.consoleRepository = dataAccess.getConsoleRepository();
        this.stockRepository = dataAccess.getStockRepository();

    }

    public String createQuery(String query)
    {
        if(query == null)
        {
            query = "UpdatedAt-asc";
        }
        String[] queries = query.split(";");
        if(!queries[0].contains("-"))
        {
            queries[0] = "UpdatedAt-asc";
        }
        List<String> columns = new ArrayList<>();
        List<String> searchTerms = new ArrayList<>();
        String orderDirection = "";
        try
        {
            for (int i = 0; i < queries.length; i++)
            {
                if(queries[i].contains("="))
                {
                    String[] parts = queries[i].split("=");
                    columns.add(parts[0]);
                    searchTerms.add(parts[1].replace("+", " "));
                }
                else if(queries[i].contains("-"))
                {
                    String[] parts = queries[i].split("-");
                    orderDirection = " order by " + parts[0] + " " + parts[1];
                }
            }
        }
        catch (Exception e)
        {
            return null;
        }
        String and = " and ";
        String like = " like ";
        StringBuilder str = new StringBuilder();
        if(queries.length > 1)
        {
            if(!queries[1].contains("=") && queries.length == 2)
            {
                str.append("where (Title like '%" + queries[1] + "%' or Description like '%" + queries[1] + "%') ");
            }
            else if(!queries[1].contains("="))
            {
                str.append("where (Title like '%" + queries[1] + "%' or Description like '%" + queries[1] + "%') and ");
            }
            else
            {
                str.append("where ");
            }
        }
        if(!columns.isEmpty())
        {
            if(columns.get(0).equals("SellingPrice"))
            {
                String[] price = searchTerms.get(0).split("/");
                int begin = Integer.parseInt(price[0]);
                int end = Integer.parseInt(price[1]);
                str.append(columns.get(0) + " between " + begin + and + end);
            }
            else
            {
                if(columns.size()>1)
                {
                    if(columns.get(0).equals(columns.get(1)))
                    {
                        str.append("(" + columns.get(0) + like + "'%" + searchTerms.get(0) + "%'");
                    }
                    else
                    {
                        str.append(columns.get(0) + like + "'%" + searchTerms.get(0) + "%'");
                    }
                }
                else
                {
                    str.append(columns.get(0) + like + "'%" + searchTerms.get(0) + "%'");
                }
            }

            for (int i = 1; i < columns.size(); i++)
            {
                if(columns.get(i).equals("SellingPrice"))
                {
                    String[] price = searchTerms.get(i).split("/");
                    int begin = Integer.parseInt(price[0]);
                    int end = Integer.parseInt(price[1]);
                    str.append(and + columns.get(i) + " between " + begin + and + end);
                }
                else
                {
                    if(columns.get(i).equals(columns.get(i-1)))
                    {
                        if(i+1<columns.size())
                        {
                            if(columns.get(i).equals(columns.get(i+1)))
                            {
                                str.append(" or " + columns.get(i) + like + "'%" + searchTerms.get(i) + "%'");
                            }
                            else
                            {
                                str.append(" or " + columns.get(i) + like + "'%" + searchTerms.get(i) + "%')");
                            }
                        }
                        else
                        {
                            str.append(" or " + columns.get(i) + like + "'%" + searchTerms.get(i) + "%')");
                        }
                    }
                    else if(i+1<columns.size())
                    {
                        if(columns.get(i).equals(columns.get(i+1)))
                        {
                            str.append(" and (" + columns.get(i) + like + "'%" + searchTerms.get(i) + "%'");
                        }
                        else
                        {
                            str.append(and + columns.get(i) + like + "'%" + searchTerms.get(i) + "%'");
                        }
                    }
                    else
                    {
                        str.append(and + columns.get(i) + like + "'%" + searchTerms.get(i) + "%'");
                    }
                }
            }
        }
        str.append(orderDirection);
        return str.toString();
    }

    public List<DistinctColumn> getColumns(String product)
    {
        List<DistinctColumn> distinctColumns = new ArrayList<>();
        String platform = "Platform";
        try
        {
            switch (product)
            {
                case "product":
                    List<String> columns = new ArrayList<>(Arrays.asList(platform, "Category"));
                    for (String column: columns)
                    {
                        DistinctColumn distinctColumn = new DistinctColumn();
                        distinctColumn.setColumn(column);
                        distinctColumn.setValues(productRepository.getColumns(column));
                        distinctColumns.add(distinctColumn);
                    }
                    break;
                case "game":
                    List<String> gameColumns = new ArrayList<>(Arrays.asList("Pegi", platform));
                    for (String column: gameColumns)
                    {
                        DistinctColumn distinctColumn = new DistinctColumn();
                        distinctColumn.setColumn(column);
                        distinctColumn.setValues(gameRepository.getColumns(column));
                        distinctColumns.add(distinctColumn);
                    }
                    break;
                case "console":
                    List<String> consoleColumns = new ArrayList<>(Arrays.asList("Color","Brand","Storage", platform));
                    for (String column: consoleColumns)
                    {
                        DistinctColumn distinctColumn = new DistinctColumn();
                        distinctColumn.setColumn(column);
                        distinctColumn.setValues(consoleRepository.getColumns(column));
                        distinctColumns.add(distinctColumn);
                    }
                    break;
                case "accessory":
                    List<String> accessoryColumns = new ArrayList<>(Arrays.asList("Color", "Brand", platform));
                    for (String column: accessoryColumns)
                    {
                        DistinctColumn distinctColumn = new DistinctColumn();
                        distinctColumn.setColumn(column);
                        distinctColumn.setValues(accessoryRepository.getColumns(column));
                        distinctColumns.add(distinctColumn);
                    }
                    break;
                default:
                    break;
            }
            return distinctColumns;
        }
        catch(HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return new ArrayList<>();
        }

    }

    public int getLatestProductId()
    {
        try
        {
            return productRepository.getLatestProductId();
        }
        catch(HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return 0;
        }
    }

    public List<Product> getProducts(String query)
    {
        String str = createQuery(query);
        if(str == null)
        {
            return new ArrayList<>();
        }
        try
        {
            return productRepository.getProducts(str);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return new ArrayList<>();
        }
    }

    public Product getProduct(int id)
    {
        try
        {
            return productRepository.getProduct(id);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return null;
        }
    }

    public Game getGame(int id)
    {
        try
        {
            return gameRepository.getGame(id);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return null;
        }
    }

    public Console getConsole(int id)
    {
        try
        {
            return consoleRepository.getConsole(id);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return null;
        }
    }

    public Accessory getAccessory(int id)
    {
        try
        {
            return accessoryRepository.getAccessory(id);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return null;
        }
    }

    public void deleteProduct(int id)
    {
        try
        {
            cartItemRepository.deleteCartItemsByProductId(id);
            if(productRepository.getProduct(id) != null)
            {
                stockRepository.deleteStock(id);
                productRepository.deleteProduct(id);
            }
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
        }
    }

    public List<Game> getGames(String query)
    {
        String str = createQuery(query);
        if(str == null)
        {
            return new ArrayList<>();
        }
        try
        {
            return gameRepository.getGames(str);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return new ArrayList<>();
        }
    }

    public List<GenreModel> getGenres()
    {
        try
        {
            return gameRepository.getGenres();
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return new ArrayList<>();
        }
    }

    public boolean addGame(Game game)
    {
        game.setUpdatedAt(LocalDateTime.now());
        game.setCategory(Category.GAMES);
        try
        {
            int id = getLatestProductId();
            if(id!=0)
            {
                game.setId(getLatestProductId());
            }
            Stock stock = new Stock(game.getId(), 1, LocalDateTime.now());
            game.setStock(stock);
            stock.setProduct(game);
            gameRepository.createGame(game);
            stockRepository.createStock(stock);
            return true;

        } catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return false;
        }
    }

    public boolean updateGame(Game game)
    {
        try
        {
            game.setUpdatedAt(LocalDateTime.now());
            return gameRepository.updateGame(game);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return false;
        }
    }

    public List<Console> getConsoles(String query)
    {
        String str = createQuery(query);
        if(str == null)
        {
            return new ArrayList<>();
        }
        try
        {
            return consoleRepository.getConsoles(str);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return new ArrayList<>();
        }
    }

    public boolean addConsole(Console console)
    {
        console.setCategory(Category.CONSOLES);
        console.setUpdatedAt(LocalDateTime.now());
        try
        {
            int id = getLatestProductId();
            if(id!=0)
            {
                console.setId(getLatestProductId());
            }
            Stock stock = new Stock(console.getId(), 1, LocalDateTime.now());
            console.setStock(stock);
            stock.setProduct(console);
            consoleRepository.createConsole(console);
            stockRepository.createStock(stock);
            return true;

        } catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return false;
        }
    }

    public boolean updateConsole(Console console)
    {
        try
        {
            console.setUpdatedAt(LocalDateTime.now());
            return consoleRepository.updateConsole(console);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return false;
        }
    }

    public List<Accessory> getAccessories(String query)
    {
        String str = createQuery(query);
        if(str == null)
        {
            return new ArrayList<>();
        }
        try
        {
            return accessoryRepository.getAccessories(str);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return new ArrayList<>();
        }
    }

    public boolean addAccessory(Accessory accessory)
    {
        accessory.setUpdatedAt(LocalDateTime.now());
        try
        {
            int id = getLatestProductId();
            if(id!=0)
            {
                accessory.setId(getLatestProductId());
            }
            Stock stock = new Stock(accessory.getId(), 1, LocalDateTime.now());
            accessory.setStock(stock);
            stock.setProduct(accessory);
            accessoryRepository.createAccessory(accessory);
            stockRepository.createStock(stock);
            return true;

        } catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return false;
        }
    }

    public boolean updateAccessory(Accessory accessory)
    {
        try
        {
            accessory.setUpdatedAt(LocalDateTime.now());
            return accessoryRepository.updateAccessory(accessory);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return false;
        }
    }

    public Stock getStock(int id)
    {
        try
        {
            return stockRepository.getStock(id);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return null;
        }
    }

    public int getStockQuantity(int id)
    {
        Stock stock = new Stock();
        try
        {
            stock = stockRepository.getStock(id);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return -1;
        }
        return stock.getQuantity();
    }

    public int updateStockQuantity(int id, int quantity)
    {
        Stock stock = new Stock();
        try
        {
            stock = stockRepository.getStock(id);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return -1;
        }
        int currentQuantity = stock.getQuantity();
        int newQuantity = currentQuantity + quantity;
        stock.setUpdatedAt(LocalDateTime.now());
        stock.setQuantity(newQuantity);
        try
        {
            stockRepository.updateStock(stock);
        }
        catch (HibernateException e)
        {
            logger.log(Level.INFO, null ,e);
            return -1;
        }
        return newQuantity;
    }

}
