package gamefully.service.managers;

import gamefully.service.TokenSecurity;
import gamefully.service.models.*;
import gamefully.service.repositories.CartItemRepository;
import gamefully.service.repositories.HibernateException;
import gamefully.service.repositories.interfaces.*;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserManager {
    private IUser userRepository;
    private IWishlist wishlistRepository;
    private ICart cartRepository;
    private ICartItem cartItemRepository = new CartItemRepository();
    private IStock stockRepository;

    private TokenSecurity tokenSecurity = new TokenSecurity();

    private static final Logger logger = Logger.getLogger(UserManager.class.getName());

    public UserManager(DataAccess dataAccess) {
        this.userRepository = dataAccess.getUserRepository();
        this.wishlistRepository = dataAccess.getWishlistRepository();
        this.cartRepository = dataAccess.getCartRepository();
        this.stockRepository = dataAccess.getStockRepository();
    }

    public List<User> getUsers() {
        try {
            return userRepository.getUsers();
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
            return new ArrayList<>();
        }
    }

    public User getUser(int id) {
        try {
            return userRepository.getUser(id);
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
            return null;
        }
    }

    public User getUser(String email) {
        try {
            return userRepository.getUser(email);
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
            return null;
        }
    }

    public boolean addUser(User user) {
        int id = getLatestID();
        if (id == 0) {
            return false;
        }
        user.setId(id);
        user.setAdmin(0);
        user.setRegisteredAt(LocalDateTime.now());
        String password = user.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
        user.setPassword(hashedPassword);
        Wishlist wishlist = new Wishlist(user.getId());
        wishlist.setUser(user);
        Cart cart = new Cart(user.getId(), 0);
        cart.setUser(user);
        user.setWishlist(wishlist);
        user.setCart(cart);
        try {
            userRepository.createUser(user);
            cartRepository.createCart(cart);
            wishlistRepository.createWishlist(wishlist);
            return true;
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
            return false;
        }
    }

    public int getLatestID() {
        try {
            return userRepository.getLatestUserId();
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
            return -1;
        }
    }

    public boolean isValidUser(Credentials credentials) {
        try {
            User user = this.getUser(credentials.getEmail());
            return BCrypt.checkpw(credentials.getPassword(), user.getPassword());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isUserAllowed(String jwt, Set<String> roleset) {
        String username = tokenSecurity.checkJWT(jwt);
        String role = this.getRole(username);
        return roleset.contains(role);
    }

    public String getRole(String mail) {
        try {
            return userRepository.getRole(mail);
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
            return null;
        }
    }

    public Cart getCart(int id) {
        try {
            return cartRepository.getCart(id);
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
            return null;
        }
    }

    public int getCartQuantity(int id) {
        int quantity = 0;
        Cart cart = null;
        try {
            cart = cartRepository.getCart(id);
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
            return -1;
        }
        for (CartItem cartItem : cart.getCartItems()) {
            quantity = quantity + cartItem.getQuantity();
        }
        return quantity;
    }

    public int updateCartItemQuantity(int id, int quantity) {
        CartItem cartItem = null;
        Stock stock = null;
        try {
            cartItem = cartItemRepository.getCartItem(id);
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
            return -1;
        }
        try {
            stock = stockRepository.getStock(cartItem.getProduct().getId());
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
            return -1;
        }
        int maxQuantity = stock.getQuantity();
        if(quantity > maxQuantity)
        {
            quantity = maxQuantity;
        }
        cartItem.setQuantity(quantity);
        int newTotal = quantity * cartItem.getProduct().getSellingPrice();
        cartItem.setTotal(newTotal);
        try {
            cartItemRepository.updateCartItem(cartItem);
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
            return -1;
        }
        updateCartTotal(cartItem.getCart().getId());
        return quantity;

    }

    public void updateCartTotal(int id)
    {
        Cart cart = new Cart();
        try
        {
            cart = cartRepository.getCart(id);
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
        }
        int total = 0;
        for (CartItem item: cart.getCartItems())
        {
            total = total + item.getTotal();
        }
        cart.setTotal(total);
        try
        {
            cartRepository.updateCart(cart);
        } catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
        }
    }

    public void deleteCartItem(int id)
    {
        CartItem item = new CartItem();
        try
        {
            item = cartItemRepository.getCartItem(id);
        }
        catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
        }
        int cartId = item.getCart().getId();
        try
        {
            cartItemRepository.deleteCartItem(id);
        }
        catch (HibernateException e) {
        logger.log(Level.INFO, null, e);
        }
        updateCartTotal(cartId);

    }

    public CartItem addCartItem(CartItemUpdate update)
    {
        int userId = update.getUserId();
        int productId = update.getProductId();
        ProductsManager manager = new ProductsManager(new DataAccess("DB"));
        CartItem cartItem = null;
        try
        {
            Cart cart = cartRepository.getCart(userId);
            List<CartItem> cartItems = cart.getCartItems();
            for (CartItem item: cartItems)
            {
                if(item.getProduct().getId() == productId)
                {
                    cartItem = item;
                    break;
                }
            }
            if(cartItem != null)
            {
                cartItem.setCart(null);
                return cartItem;
            }
            cartItem = new CartItem();
            cartItem.setId(cartItemRepository.getLatestCartItemId());
            cartItem.setCart(cart);
            Product product = manager.getProduct(productId);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setTotal(product.getSellingPrice());
            cartItemRepository.addCartItem(cartItem);
            updateCartTotal(userId);
        }
        catch (HibernateException e) {
            logger.log(Level.INFO, null, e);
            return cartItem;
        }
        return cartItem;
    }


}
