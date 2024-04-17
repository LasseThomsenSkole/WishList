package WishList.service;

import WishList.model.User;
import WishList.model.Wish;
import WishList.model.Wishlist;
import WishList.repository.WishlistJDBC;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean authenticateUser(String username, String password) {
        return repository.authenticateUser(username, password);
    }

    public Integer authenticateUserAndGetId(String username, String password) {
        return repository.authenticateUserAndGetId(username, password);
    }


    public Wish getWishById(int id) {
        return repository.getWishById(id);
    }

    public void insertWish(Wish wish, int wishlistID){
        repository.insertWish(wish, wishlistID);
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

    public List<Wishlist> getWishlistsFromUserID(int userID) {
        return repository.getWishlistsByUserId(userID);
    }
    public int getUserIDFromWishlistID(int wishlistID) {
        return repository.getUserIDFromWishlistID(wishlistID);
    }
    public User createProfile(User user) {
        return repository.createProfile(user);
    }
}
