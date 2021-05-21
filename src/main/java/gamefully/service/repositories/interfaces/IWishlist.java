package gamefully.service.repositories.interfaces;

import gamefully.service.models.Wishlist;
import gamefully.service.repositories.HibernateException;

import java.util.List;

public interface IWishlist
{
    List<Wishlist> getWishlists() throws HibernateException;
    Wishlist getWishlist(int id) throws HibernateException;
    void createWishlist(Wishlist wishlist) throws HibernateException;
    void deleteWishlist(int id) throws HibernateException;
    boolean updateWishlist(Wishlist wishlist) throws HibernateException;

}
