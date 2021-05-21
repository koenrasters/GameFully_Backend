import gamefully.service.Category;
import gamefully.service.Platform;
import gamefully.service.managers.DataAccess;
import gamefully.service.managers.ProductsManager;
import gamefully.service.models.Accessory;
import gamefully.service.models.Console;
import gamefully.service.models.Game;
import gamefully.service.models.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductControllerTests
{

    public List<Product> getProductList()
    {
        List<Product> productList = new ArrayList<>();

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
        return productList;
    }


    @Test
    void getAllProducts()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        List<Product> products = getProductList();
        //Act

        List<Product> actualList = productsManager.getProducts("");

        //Assert

        assertArrayEquals(products.toArray(), actualList.toArray());
    }

    @Test
    void getOneProductByIdTrue()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Console switc = new Console(3, "0045496452629", Category.CONSOLES, Platform.SWITCH, "32GB","Nintendo", "Nintendo Switch", "Nice Switch", "Red/Blue", 100, 350);
        //Act

        Product product = productsManager.getProduct(3);

        //Assert
        assertEquals(switc, product);
    }

    @Test
    void getOneProductByIdFalse()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        //Act
        Product product = productsManager.getProduct(300);

        //Assert
        assertEquals(null, product);
    }

    @Test
    void getOneProductByIdConsoleTrue()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Console switc = new Console(3, "0045496452629", Category.CONSOLES, Platform.SWITCH, "32GB","Nintendo", "Nintendo Switch", "Nice Switch", "Red/Blue", 100, 350);
        //Act

        Console product = productsManager.getConsole(3);

        //Assert
        assertEquals(switc, product);
    }

    @Test
    void getOneProductByIdConsoleFalse()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        //Act

        Console product = productsManager.getConsole(200);

        //Assert
        assertEquals(null, product);
    }

    @Test
    void getOneProductByIdAccessory()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Accessory ps4controller = new Accessory(8, "711719870050", Category.CONTROLLERS, Platform.PS4, "Sony", "Sony DualShock 4 Controller", "Very nice controller", "Black", 20, 60);
        //Act

        Accessory product = productsManager.getAccessory(8);

        //Assert
        assertEquals(ps4controller, product);
    }

    @Test
    void getOneProductByIdAccessoryFalse()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        //Act

        Accessory product = productsManager.getAccessory(200);

        //Assert
        assertEquals(null, product);
    }

    @Test
    void getOneProductByIdGame()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Game cod = new Game(4, "5030917239199",Category.GAMES, Platform.PS4, 5, "Call of Duty: Black Ops 4", "Very mooi game",5, 60);
        //Act
        Game product = productsManager.getGame(4);
        //Assert
        assertEquals(cod, product);
    }

    @Test
    void getOneProductByIdGameFalse()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        //Act
        Game product = productsManager.getGame(200);
        //Assert
        assertEquals(null, product);
    }

    @Test
    void getAllGames()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Game cod = new Game(4, "5030917239199",Category.GAMES, Platform.PS4, 5, "Call of Duty: Black Ops 4", "Very mooi game",5, 60);
        Game ac = new Game(5, "0045496425456", Category.GAMES, Platform.SWITCH, 3, "Animal Crossing: New Horizons", "Animal Crossing on Switch", 5, 50);
        Game sm = new Game(6, "711719835721",Category.GAMES, Platform.PS5, 18, "Marvel's Spider-Man: Miles Morales", "Very mooi game",5, 60);
        Game cp = new Game(7, "3391892006155", Category.GAMES, Platform.XBOX_SERIES_X, 18, "Cyberpunk 2077 - Day One Edition", "Cyberpunk 2077 - Day One Edition nice game", 5, 50);
        List<Game> productList = new ArrayList<>();
        productList.add(cod);
        productList.add(ac);
        productList.add(sm);
        productList.add(cp);
        //Act

        List<Game> games = productsManager.getGames("");

        //Assert
        assertArrayEquals(productList.toArray(), games.toArray());
    }

    @Test
    void getAllAccessories()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Accessory ps4controller = new Accessory(8, "711719870050", Category.CONTROLLERS, Platform.PS4, "Sony", "Sony DualShock 4 Controller", "Very nice controller", "Black", 20, 60);
        Accessory ps5controller = new Accessory(9, "0711719399506", Category.CONTROLLERS, Platform.PS5, "Sony", "Sony PS5 DualSense Controller", "Very nice controller", "White", 25, 70);
        Accessory joycon = new Accessory(10, "45496430566", Category.CONTROLLERS, Platform.SWITCH, "Nintendo", "Joy-Con Controllerset", "Very nice controller", "Red/Blue", 25, 70);
        List<Accessory> productList = new ArrayList<>();
        productList.add(ps4controller);
        productList.add(ps5controller);
        productList.add(joycon);
        //Act

        List<Accessory> accessories = productsManager.getAccessories("");

        //Assert
        assertArrayEquals(productList.toArray(), accessories.toArray());
    }

    @Test
    void getAllConsoles()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Console playstation4 = new Console(1, "0711719753216", Category.CONSOLES, Platform.PS4, "1TB","Sony","Sony PlayStation 4 Pro console 1TB", "Very mooi console","Black", 100, 300);
        Console playstation5 = new Console(2, "0711719395300", Category.CONSOLES, Platform.PS5, "800GB","Sony","Sony Playstation 5", "Very mooi PS5","White", 200, 600);
        Console switc = new Console(3, "0045496452629", Category.CONSOLES, Platform.SWITCH, "32GB","Nintendo", "Nintendo Switch", "Nice Switch", "Red/Blue", 100, 350);
        List<Console> productList = new ArrayList<>();
        productList.add(playstation4);
        productList.add(playstation5);
        productList.add(switc);
        //Act

        List<Console> consoles = productsManager.getConsoles("");

        //Assert
        assertArrayEquals(productList.toArray(), consoles.toArray());
    }

    @Test
    void getProductBySearchterm()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        List<Product> products = getProductList();
        //Act
        String query = "";
        List<Product> productList = productsManager.getProducts(query);
        //Assert
        assertArrayEquals(products.toArray(), productList.toArray());
    }

    @Test
    void getGameBySearchterm()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        List<Game> games = new ArrayList<>();
        Game cod = new Game(4, "5030917239199",Category.GAMES, Platform.PS4, 5, "Call of Duty: Black Ops 4", "Very mooi game",5, 60);
        games.add(cod);
        //Act
        List<Game> gameList = productsManager.getGames("UpdatedAt-asc;PS4");
        //Assert
        assertArrayEquals(games.toArray(), gameList.toArray());
    }

    @Test
    void getConsoleBySearchterm()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        List<Console> consoles = new ArrayList<>();
        Console playstation4 = new Console(1, "0711719753216", Category.CONSOLES, Platform.PS4, "1TB","Sony","Sony PlayStation 4 Pro console 1TB", "Very mooi console","Black", 100, 300);
        consoles.add(playstation4);
        //Act
        List<Console> consoleList = productsManager.getConsoles("UpdatedAt-asc;Black");
        //Assert
        assertArrayEquals(consoles.toArray(), consoleList.toArray());
    }

    @Test
    void getAccessoryBySearchterm()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        List<Accessory> accessories = new ArrayList<>();
        Accessory ps4controller = new Accessory(8, "711719870050", Category.CONTROLLERS, Platform.PS4, "Sony", "Sony DualShock 4 Controller", "Very nice controller", "Black", 20, 60);
        accessories.add(ps4controller);
        //Act
        List<Accessory> accessoryList = productsManager.getAccessories("UpdatedAt-asc;Black");
        //Assert
        assertArrayEquals(accessories.toArray(), accessoryList.toArray());
    }

    @Test
    void deleteProduct()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        int listSize = 10 - 1;
        //Act
        productsManager.deleteProduct(1);
        int actualSize = productsManager.getProducts("").size();
        //Assert
        assertEquals(listSize, actualSize);
    }

    @Test
    void addGame()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Game newGame = new Game();
        int listSize = 4 + 1;
        //Act
        productsManager.addGame(newGame);
        int actualListSize = productsManager.getGames("").size();
        //Assert
        assertEquals(listSize, actualListSize);
    }

    @Test
    void addConsole()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Console newConsole = new Console();
        int listSize = 3 + 1;
        //Act
        productsManager.addConsole(newConsole);
        int actualListSize = productsManager.getGames("").size();
        //Assert
        assertEquals(listSize, actualListSize);
    }

    @Test
    void addAccessory()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Accessory newAccessory = new Accessory();
        int listSize = 3 + 1;
        //Act
        productsManager.addAccessory(newAccessory);
        int actualListSize = productsManager.getGames("").size();
        //Assert
        assertEquals(listSize, actualListSize);
    }

    @Test
    void updateGame()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Game game = new Game(4, "5030917239199",Category.GAMES, Platform.PS4, 6, "Call of Duty: Black Ops 4", "Very mooi game",5, 40000);
        //Act
        productsManager.updateGame(game);
        Game updatedGame = productsManager.getGame(game.getId());
        //Assert
        assertEquals(game, updatedGame);
    }

    @Test
    void updateConsole()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Console console = new Console(1, "0711719753216", Category.CONSOLES, Platform.PS4, "1TB","Sony","Sony PlayStation 4 Pro console 1TB", "Very mooi console","Black", 100, 3032550);
        //Act
        productsManager.updateConsole(console);
        Console updatedConsole = productsManager.getConsole(console.getId());
        //Assert
        assertEquals(console, updatedConsole);
    }

    @Test
    void updateAccessory()
    {
        //Arrange
        ProductsManager productsManager = new ProductsManager(new DataAccess("MEM"));
        Accessory accessory = new Accessory(8, "711719870050", Category.CONTROLLERS, Platform.PS4, "Sony", "Sony DualShock 4 Controller", "Very nice controller", "Black", 20, 60);
        //Act
        productsManager.updateAccessory(accessory);
        Accessory updatedAccessory = productsManager.getAccessory(accessory.getId());
        //Assert
        assertEquals(accessory, updatedAccessory);
    }





}
