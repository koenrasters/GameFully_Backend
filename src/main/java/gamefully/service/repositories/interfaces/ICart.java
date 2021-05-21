package gamefully.service.repositories.interfaces;

import gamefully.service.models.Cart;
import gamefully.service.repositories.HibernateException;

import java.util.List;

public interface ICart
{
    List<Cart> getCarts() throws HibernateException;
    Cart getCart(int id) throws HibernateException;
    void createCart(Cart cart) throws HibernateException;
    void deleteCart(int id) throws HibernateException;
    boolean updateCart(Cart cart) throws HibernateException;

}
