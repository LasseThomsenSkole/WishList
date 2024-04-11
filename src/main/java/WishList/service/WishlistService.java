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
    public void createWishlist(Wishlist wishlist, int userId){
        repository.createWishlist(wishlist, userId);
    }

    public User getUsernameFromID(int ID) {
        return repository.getUsernameFromID(ID);
    }

    public Wish getWishById(int id) {
        return repository.getWishById(id);
    }

    public void updateWish(int ID, Wish updatedWish) {
        repository.updateWish(ID, updatedWish);
    }

    public void deleteWish(int ID) {
        repository.deleteWish(ID);
    }

    public void deleteWishlist(int ID) {
        repository.deleteWishlist(ID);
    }


}
