package gamefully.service.repositories;

import gamefully.service.models.Cart;
import gamefully.service.repositories.interfaces.ICart;

import java.util.List;

public class CartRepositoryMem implements ICart
{
    private final FakeDataStore fakeDataStore = new FakeDataStore();
    public List<Cart> getCarts()
    {
        return fakeDataStore.getCartList();
    }

    public Cart getCart(int id)
    {
        return fakeDataStore.getCart(id);
    }

    public void createCart(Cart cart)
    {
        fakeDataStore.addCart(cart);
    }

    public void deleteCart(int id)
    {
        fakeDataStore.deleteCart(id);
    }

    public boolean updateCart(Cart cart)
    {
        return fakeDataStore.updateCart(cart);
    }
}
