package gamefully.service.repositories.interfaces;

import gamefully.service.models.CartItem;
import gamefully.service.repositories.HibernateException;

public interface ICartItem {
    CartItem getCartItem(int id) throws HibernateException;
    void addCartItem(CartItem cartItem) throws HibernateException;
    void deleteCartItem(int id) throws HibernateException;
    boolean updateCartItem(CartItem cartItem) throws HibernateException;
    int getLatestCartItemId() throws HibernateException;
    void deleteCartItemsByProductId(int id) throws HibernateException;
}
