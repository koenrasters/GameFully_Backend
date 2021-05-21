package gamefully.service.managers;

import gamefully.service.repositories.*;
import gamefully.service.repositories.interfaces.*;

public class DataAccess
{
    private String context;

    public DataAccess(String context)
    {
        this.context = context;
    }

    public IProduct getProductRepository()
    {
        if (context.equals("DB"))
        {
            return new ProductRepository();
        }
        else
        {
            return new ProductRepositoryMem();
        }
    }

    public IGame getGameRepository()
    {
        if (context.equals("DB"))
        {
            return new GameRepository();
        }
        else
        {
            return new GameRepositoryMem();
        }
    }

    public IConsole getConsoleRepository()
    {
        if (context.equals("DB"))
        {
            return new ConsoleRepository();
        }
        else
        {
            return new ConsoleRepositoryMem();
        }
    }

    public IAccessory getAccessoryRepository()
    {
        if (context.equals("DB"))
        {
            return new AccessoryRepository();
        }
        else
        {
            return new AccessoryRepositoryMem();
        }
    }

    public IStock getStockRepository()
    {
        if (context.equals("DB"))
        {
            return new StockRepository();
        }
        else
        {
            return new StockRepositoryMem();
        }
    }

    public ICart getCartRepository()
    {
        if (context.equals("DB"))
        {
            return new CartRepository();
        }
        else
        {
            return new CartRepositoryMem();
        }
    }

    public ITransaction getTransactionRepository()
    {
        if (context.equals("DB"))
        {
            return new TransactionRepository();
        }
        else
        {
            return new TransactionRepositoryMem();
        }
    }

    public IUser getUserRepository()
    {
        if (context.equals("DB"))
        {
            return new UserRepository();
        }
        else
        {
            return new UserRepositoryMem();
        }
    }

    public IWishlist getWishlistRepository()
    {
        if (context.equals("DB"))
        {
            return new WishlistRepository();
        }
        else
        {
            return new WishlistRepositoryMem();
        }
    }
}
