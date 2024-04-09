package WishList.service;

import WishList.model.User;
import WishList.model.Wishlist;
import WishList.repository.WishlistJDBC;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private WishlistJDBC repository;

    public Wishlist getWishlist(int wishlistID) {
        return repository.getWishlist(wishlistID);
    }

    public User getUsernameFromID(int ID) {
        return repository.getUsernameFromID(ID);
    }





}
