package gamefully.service.repositories;

import gamefully.service.models.Wishlist;
import gamefully.service.repositories.interfaces.IWishlist;

import java.util.List;

public class WishlistRepositoryMem implements IWishlist
{
    private final FakeDataStore fakeDataStore = new FakeDataStore();

    public List<Wishlist> getWishlists()
    {
        return fakeDataStore.getWishlists();
    }

    public Wishlist getWishlist(int id)
    {
        return getWishlist(id);
    }

    public void createWishlist(Wishlist wishlist)
    {
        fakeDataStore.addWishlist(wishlist);
    }

    public void deleteWishlist(int id)
    {
        fakeDataStore.deleteWishlist(id);
    }

    public boolean updateWishlist(Wishlist wishlist)
    {
        return fakeDataStore.updateWishlist(wishlist);
    }
}
