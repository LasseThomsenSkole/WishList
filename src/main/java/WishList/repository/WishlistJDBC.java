package WishList.repository;
import WishList.model.Wish;
import WishList.model.Wishlist;
import WishList.model.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishlistJDBC {
    private final String db_url = "jdbc:mysql://localhost:3306/WishlistDB"; //ik hardcode det her hvis vi kan det få det til at fungere uden
    private final String username = "root";
    private final String pw = "Andrea1999!";

    //TODO til forside
    public List<Wishlist> getWishlistsByUserId(int userId) {
        List<Wishlist> wishlists = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(db_url, username, pw)) {
            String sqlWishlists = "SELECT w.id AS wishlist_id, w.name AS wishlist_name, w.description AS wishlist_description " +
                    "FROM Wishlists w " +
                    "JOIN Users u ON w.user_id = u.id " +
                    "WHERE u.id = ?;";
            try (PreparedStatement psWishlists = con.prepareStatement(sqlWishlists)) {
                psWishlists.setInt(1, userId);
                ResultSet rsWishlists = psWishlists.executeQuery();
                while (rsWishlists.next()) {
                    int wishlistId = rsWishlists.getInt("wishlist_id");
                    String name = rsWishlists.getString("wishlist_name");
                    String description = rsWishlists.getString("wishlist_description");

                    List<Wish> wishes = new ArrayList<>();
                    String sqlWishes = "SELECT id, name, description, url, price " +
                            "FROM Wish " +
                            "WHERE wishlist_id = ?;";
                    try (PreparedStatement psWishes = con.prepareStatement(sqlWishes)) {
                        psWishes.setInt(1, wishlistId);
                        ResultSet rsWishes = psWishes.executeQuery();
                        while (rsWishes.next()) {
                            String wishName = rsWishes.getString("name");
                            String wishDescription = rsWishes.getString("description");
                            String url = rsWishes.getString("url");
                            double price = rsWishes.getDouble("price");
                            wishes.add(new Wish(wishName, wishDescription, price , url));
                        }
                    }
                    // Add Wishlist to the List
                    wishlists.add(new Wishlist(wishlistId,name, description, wishes));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wishlists;
    }

    public Wishlist getWishlist(int wishlistId) {
        Wishlist wishlist = null;
        List<Wish> wishes = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(db_url, username, pw)) {
            // Trin 1: Hent Wishesliste detaljer
            String sqlWishlist = "SELECT id, name, description FROM Wishlists WHERE id = ?;";
            try (PreparedStatement psWishlist = con.prepareStatement(sqlWishlist)) {
                psWishlist.setInt(1, wishlistId);
                ResultSet rsWishlist = psWishlist.executeQuery();
                if (rsWishlist.next()) {
                    int id = rsWishlist.getInt("id");
                    String name = rsWishlist.getString("name");
                    String description = rsWishlist.getString("description");
                    wishlist = new Wishlist(id, name, description, wishes);
                }
            }

            if (wishlist != null) {
                // Trin 2: Hent Wishes for den givne Wishliste
                String sqlWishes = "SELECT id, name, description, url, price FROM Wish WHERE wishlist_id = ?;";
                try (PreparedStatement psWishes = con.prepareStatement(sqlWishes)) {
                    psWishes.setInt(1, wishlistId);
                    ResultSet rsWishes = psWishes.executeQuery();
                    while (rsWishes.next()) {
                        int id = rsWishes.getInt("id");
                        String name = rsWishes.getString("name");
                        String description = rsWishes.getString("description");
                        String url = rsWishes.getString("url");
                        double price = rsWishes.getDouble("price");
                        wishes.add(new Wish(id, name, description, price , url));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wishlist;
    }

    public Wish getWishById(int wishId) {
        try (Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL =
                    "SELECT * FROM Wish " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, wishId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                String url = resultSet.getString("url");
                return new Wish(id, name, description, price, url);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public User getUsernameFromID(int userID){
        try(Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL =
                    "SELECT name, password " +
                    "FROM Users " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                return new User(name, password);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    /** LOGIN **/


    public Integer authenticateUserAndGetId(String username, String providedPassword) {
        String sql = "SELECT id, password FROM Users WHERE name = ?";
        try (Connection con = DriverManager.getConnection(db_url, this.username, this.pw);
             PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && providedPassword.equals(resultSet.getString("password"))) {
                return resultSet.getInt("id"); // Returnerer brugerens ID
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

  /**Opret Profil**/

    public User createProfile(User user) {
        try(Connection con = DriverManager.getConnection(db_url,username,pw)) {
            String SQL =
                    "INSERT INTO Users (name, password)" +
                    " VALUES ( ?, ?)";
            PreparedStatement ps = con.prepareStatement(SQL);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void createWishlist(Wishlist wishlist, int userId){
        try(Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL =
                    "INSERT INTO Wishlists (name, description, user_id) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, wishlist.getName());
            preparedStatement.setString(2, wishlist.getDescription());
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    /**Opret Ønske**/

    public void insertWish(Wish wish,int wishlistId){
        try (Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL =
                    "INSERT INTO Wish (name, description, price, url, wishlist_id) " +
                    "VALUES (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, wish.getName());
            preparedStatement.setString(2, wish.getDescription());
            preparedStatement.setDouble(3, wish.getPrice());
            preparedStatement.setString(4, wish.getUrl());
            preparedStatement.setInt(5, wishlistId);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }





    public void editWish(int wishID, Wish updatedwish) {
        try(Connection con = DriverManager.getConnection(db_url, username, pw)) {
            String SQL =
                    "UPDATE Wish " +
                    "SET name = ?, description = ?, price = ?, url = ? " +
                    "WHERE id = ?;";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, updatedwish.getName());
            preparedStatement.setString(2, updatedwish.getDescription());
            preparedStatement.setDouble(3, updatedwish.getPrice());
            preparedStatement.setString(4, updatedwish.getUrl());
            preparedStatement.setInt(5, wishID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWish (int wishID) {
            try(Connection con = DriverManager.getConnection(db_url, username, pw)){
                String SQL =
                        "DELETE FROM Wish " +
                        "WHERE id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(SQL);
                preparedStatement.setInt(1, wishID);
                preparedStatement.executeUpdate();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    public void deleteWishlist (int wishlistID) {
        try(Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL =
                    "DELETE FROM Wishlists " +
                            "WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, wishlistID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getUserIDFromWishlistID(int wishlistID){
        try(Connection con = DriverManager.getConnection(db_url, username, pw)) {
            String SQL = "SELECT w.id AS wishlist_id, u.id AS user_id " +
                    "FROM Wishlists w " +
                    "JOIN Users u ON w.user_id = u.id " +
                    "WHERE w.id = ?;";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, wishlistID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("user_id");
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }





}
