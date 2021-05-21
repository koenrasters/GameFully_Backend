package gamefully.service.repositories;

import gamefully.service.Category;
import gamefully.service.Platform;
import gamefully.service.models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakeDataStore
{

    private final List<Product> productList;
    private final List<Cart> cartList;
    private final List<Stock> stockList;
    private final List<Transaction> transactionList;
    private final List<User> userList;
    private final List<Wishlist> wishlists;

    public FakeDataStore()
    {
        productList = new ArrayList<>();
        cartList = new ArrayList<>();
        stockList = new ArrayList<>();
        transactionList = new ArrayList<>();
        userList = new ArrayList<>();
        wishlists = new ArrayList<>();
        Console playstation4 = new Console(1, "0711719753216", Category.CONSOLES, Platform.PS4, "1TB","Sony","Sony PlayStation 4 Pro console 1TB", "Very mooi console","Black", 100, 300);
        Console playstation5 = new Console(2, "0711719395300", Category.CONSOLES, Platform.PS5,"1TB","Sony","Sony Playstation 5", "Very mooi PS5","White", 200, 600);
        Console switc = new Console(3, "0045496452629", Category.CONSOLES, Platform.SWITCH, "1TB","Nintendo", "Nintendo Switch", "Nice Switch", "Red/Blue", 100, 350);


        Game cod = new Game(4, "5030917239199",Category.GAMES, Platform.PS4, 6, "Call of Duty: Black Ops 4", "Very mooi game",5, 60);
        Game ac = new Game(5, "0045496425456", Category.GAMES, Platform.SWITCH, 7, "Animal Crossing: New Horizons", "Animal Crossing on Switch", 5, 50);
        Game sm = new Game(6, "711719835721",Category.GAMES, Platform.PS5, 7, "Marvel's Spider-Man: Miles Morales", "Very mooi game",5, 60);
        Game cp = new Game(7, "3391892006155", Category.GAMES, Platform.XBOX_SERIES_X, 9, "Cyberpunk 2077 - Day One Edition", "Cyberpunk 2077 - Day One Edition nice game", 5, 50);


        Accessory ps4controller = new Accessory(8, "711719870050", Category.CONTROLLERS, Platform.PS4, "Sony", "Sony DualShock 4 Controller", "Very nice controller", "Black", 20, 60);
        Accessory ps5controller = new Accessory(9, "0711719399506", Category.CONTROLLERS, Platform.PS5, "Sony", "Sony PS5 DualSense Controller", "nice controller", "White", 25, 70);
        Accessory joycon = new Accessory(10, "45496430566", Category.CONTROLLERS, Platform.SWITCH, "Nintendo", "Joy-Con Controllerset", "Very mooi controller", "Red/Blue", 25, 70);

        User koen = new User(1, "Koen", "Rasters", "Gravenkasteel 23", "6028RK", "Gastel", "06", "koen.rasters@gmail.com", "Jan", LocalDateTime.now(), 1);

        Cart kCart = new Cart(1, 15);

        CartItem ps4 = new CartItem(1, 12, 100);
        ps4.setProduct(playstation4);
        kCart.setUser(koen);
        kCart.setCartItems(Arrays.asList(ps4));

        Stock ps4Stock = new Stock(1, 14, LocalDateTime.now());
        ps4Stock.setProduct(playstation4);
        Stock ps5Stock = new Stock(2, 14, LocalDateTime.now());
        ps5Stock.setProduct(playstation5);
        Stock swStock = new Stock(3, 14, LocalDateTime.now());
        swStock.setProduct(switc);
        Stock codStock = new Stock(4, 14, LocalDateTime.now());
        codStock.setProduct(cod);
        Stock acStock = new Stock(5, 14, LocalDateTime.now());
        acStock.setProduct(ac);
        Stock smStock = new Stock(6, 14, LocalDateTime.now());
        smStock.setProduct(sm);
        Stock cpStock = new Stock(7, 14, LocalDateTime.now());
        cpStock.setProduct(cp);
        Stock ps4CStock = new Stock(8, 14, LocalDateTime.now());
        ps4CStock.setProduct(ps4controller);
        Stock ps5CStock = new Stock(9, 14, LocalDateTime.now());
        ps5CStock.setProduct(ps5controller);
        Stock joyconStock = new Stock(10, 14, LocalDateTime.now());
        joyconStock.setProduct(joycon);

        Transaction transaction = new Transaction(1, 50, LocalDateTime.now());
        TransactionItem transactionItem = new TransactionItem(1, 14, 10);
        transactionItem.setProduct(ps5controller);
        transaction.setUser(koen);

        Wishlist wishlist = new Wishlist(1);
        wishlist.setProducts(Arrays.asList(playstation4, playstation5));
        wishlist.setUser(koen);

        koen.setCart(kCart);
        koen.setTransactions(Arrays.asList(transaction));
        koen.setWishlist(wishlist);

        productList.add(playstation4);
        productList.add(playstation5);
        productList.add(switc);

        productList.add(cod);
        productList.add(ac);
        productList.add(sm);
        productList.add(cp);

        productList.add(ps4controller);
        productList.add(ps5controller);
        productList.add(joycon);

        cartList.add(kCart);

        userList.add(koen);

        stockList.add(ps4Stock);
        stockList.add(ps5Stock);
        stockList.add(ps4CStock);
        stockList.add(ps5CStock);
        stockList.add(swStock);
        stockList.add(codStock);
        stockList.add(acStock);
        stockList.add(smStock);
        stockList.add(cpStock);
        stockList.add(joyconStock);

        wishlists.add(wishlist);


    }


    public List<Product> getProducts()
    {
        return productList;
    }

    public List<Cart> getCartList()
    {
        return cartList;
    }

    public List<User> getUserList()
    {
        return userList;
    }

    public List<Stock> getStockList()
    {
        return stockList;
    }

    public List<Transaction> getTransactionList()
    {
        return transactionList;
    }

    public List<Wishlist> getWishlists()
    {
        return wishlists;
    }

    public List<Game> getGames()
    {
        List<Game> games = new ArrayList<>();
        for (Product game : productList)
        {
            if(game.getClass().equals(Game.class)){
                games.add((Game) game);
            }
        }
        return games;
    }

    public List<Console> getConsoles() {
        List<Console> consoles = new ArrayList<>();
        for (Product console : productList)
        {
            if(console.getClass().equals(Console.class)){
                consoles.add((Console) console);
            }
        }
        return consoles;
    }

    public List<Accessory> getAccessories()
    {
        List<Accessory> accessories = new ArrayList<>();
        for (Product accessory : productList)
        {
            if(accessory.getClass().equals(Accessory.class)){
                accessories.add((Accessory) accessory);
            }
        }
        return accessories;
    }

    public Product getProduct(int nr)
    {
        for (Product product : productList)
        {
            if(product.getId() == nr)
            {
                return product;
            }
        }
        return null;
    }

    public User getUser(int nr)
    {
        for (User user : userList)
        {
            if(user.getId() == nr)
            {
                return user;
            }
        }
        return null;
    }

    public User getUser(String email)
    {
        for (User user : userList)
        {
            if(user.getEmail().equals(email))
            {
                return user;
            }
        }
        return null;
    }

    public Cart getCart(int nr)
    {
        for (Cart cart : cartList)
        {
            if(cart.getId() == nr)
            {
                return cart;
            }
        }
        return null;
    }

    public Transaction getTransaction(int nr)
    {
        for (Transaction transaction : transactionList)
        {
            if(transaction.getId() == nr)
            {
                return transaction;
            }
        }
        return null;
    }

    public Stock getStock(int nr)
    {
        for (Stock stock : stockList)
        {
            if(stock.getId() == nr)
            {
                return stock;
            }
        }
        return null;
    }

    public Wishlist getWishlist(int nr)
    {
        for (Wishlist wishlist : wishlists)
        {
            if(wishlist.getId() == nr)
            {
                return wishlist;
            }
        }
        return null;
    }

    public void deleteProduct(int nr)
    {
        Product product = getProduct(nr);
        if (product != null)
        {
            productList.remove(product);
        }
    }

    public void deleteUser(int nr)
    {
        User user = getUser(nr);
        if (user != null)
        {
            userList.remove(user);
        }
    }

    public void deleteCart(int nr)
    {
        Cart cart = getCart(nr);
        if (cart != null)
        {
            cartList.remove(cart);
        }
    }

    public void deleteStock(int nr)
    {
        Stock stock = getStock(nr);
        if (stock != null)
        {
            stockList.remove(stock);
        }
    }

    public void deleteTransaction(int nr)
    {
        Transaction transaction = getTransaction(nr);
        if (transaction != null)
        {
            transactionList.remove(transaction);
        }
    }

    public void deleteWishlist(int nr)
    {
        Wishlist wishlist = getWishlist(nr);
        if (wishlist != null)
        {
            wishlists.remove(wishlist);
        }
    }

    public boolean addProduct(Product product)
    {
        if(this.getProduct(product.getId()) != null)
        {
            return false;
        }

        productList.add(product);
        return true;

    }

    public boolean addUser(User user)
    {
        if(this.getUser(user.getId()) != null)
        {
            return false;
        }

        userList.add(user);
        return true;

    }

    public boolean addCart(Cart cart)
    {
        if(this.getCart(cart.getId()) != null)
        {
            return false;
        }

        cartList.add(cart);
        return true;

    }

    public boolean addStock(Stock stock)
    {
        if(this.getStock(stock.getId()) != null)
        {
            return false;
        }

        stockList.add(stock);
        return true;

    }

    public boolean addTransaction(Transaction transaction)
    {
        if(this.getTransaction(transaction.getId()) != null)
        {
            return false;
        }

        transactionList.add(transaction);
        return true;

    }

    public boolean addWishlist(Wishlist wishlist)
    {
        if(this.getWishlist(wishlist.getId()) != null)
        {
            return false;
        }

        wishlists.add(wishlist);
        return true;

    }

    public boolean updateProduct(Product product)
    {
        Product old = this.getProduct(product.getId());
        int index = productList.indexOf(old);
        if (old == null)
        {
            return false;
        }
        productList.set(index, product);
        return true;
    }

    public boolean updateUser(User user)
    {
        User old = this.getUser(user.getId());
        int index = userList.indexOf(old);
        if (old == null)
        {
            return false;
        }
        userList.set(index, user);
        return true;
    }

    public boolean updateCart(Cart cart)
    {
        Cart old = this.getCart(cart.getId());
        int index = cartList.indexOf(old);
        if (old == null)
        {
            return false;
        }
        cartList.set(index, cart);
        return true;
    }

    public boolean updateStock(Stock stock)
    {
        Stock old = this.getStock(stock.getId());
        int index = stockList.indexOf(old);
        if (old == null)
        {
            return false;
        }
        stockList.set(index, stock);
        return true;
    }

    public boolean updateTransaction(Transaction transaction)
    {
        Transaction old = this.getTransaction(transaction.getId());
        int index = transactionList.indexOf(old);
        if (old == null)
        {
            return false;
        }
        transactionList.set(index, transaction);
        return true;
    }

    public boolean updateWishlist(Wishlist wishlist)
    {
        Wishlist old = this.getWishlist(wishlist.getId());
        int index = wishlists.indexOf(old);
        if (old == null)
        {
            return false;
        }
        wishlists.set(index, wishlist);
        return true;
    }


}
