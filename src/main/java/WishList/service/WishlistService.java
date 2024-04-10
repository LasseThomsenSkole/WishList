package WishList.service;

import WishList.model.User;
import WishList.model.Wish;
import WishList.model.Wishlist;
import WishList.repository.WishlistJDBC;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistJDBC repository;

    public WishlistService(WishlistJDBC repository) {
        this.repository = repository;
    }

    public Wishlist getWishlist(int wishlistID) {
        return repository.getWishlist(wishlistID);
    }

    public User getUsernameFromID(int ID) {
        return repository.getUsernameFromID(ID);
    }

    public void updateWish(int ID, Wish updatedWish) { //dunno todo: test det;
        repository.updateWish(ID, updatedWish);
    }

    public void deleteWish(int ID) {
        repository.deleteWish(ID);
    }

}
