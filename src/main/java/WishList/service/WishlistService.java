package WishList.service;

import WishList.model.Wishlist;
import WishList.repository.WishlistJDBC;

public class WishlistService {

    private WishlistJDBC repository;

    public Wishlist getWishlist(int wishlistID) {
        return repository.getWishlist(wishlistID);
    }

    public Wishlist getUsernameFromID(int ID) {
        return repository.getUsernameFromID(ID);
    }





}
