package WishList.repository;
import WishList.model.Wish;
import WishList.model.Wishlist;
import WishList.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class WishlistJDBC {
    private final String db_url = "jdbc:mysql://localhost:3306/WishlistDB"; //ik hardcode det her hvis vi kan det få det til at fungere uden
    private String username = "root";
    private String pw = "-mads18B";

    //public List<Wishlist> getWishlists(){} //TODO til forside

    /*public Wishlist getWishlist(int wishlistId){
        try (Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL =
                    "SELECT w.id AS wishlist_id " +
                    "w.name AS wishlist_name, " +
                            "w.description AS wishlist_description," +
                            " GROUP_CONCAT(CONCAT(wi.id, ':::'," +
                            " wi.name, ':::', wi.description, ':::'," +
                            " wi.url, ':::', wi.price, ':::') SEPARATOR ';;;') AS wishes " +
                            "FROM Wishlists w " +
                            "LEFT JOIN Wish wi " +
                            "ON w.id = wi.wishlist_id " +
                            "WHERE w.id = ? " +
                            "GROUP BY w.id;";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, wishlistId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String name = resultSet.getString("wishlist_name");
                String description = resultSet.getString("wishlist_description");
                String concatWishes = resultSet.getString("wishes");
                String[] NonFormattedwishes = concatWishes.split(";;;"); //forvirrende men skal være sådan her

                List<Wish> wishes = new ArrayList<>(); //MÅSKE SKA DET VÆRE EN METODE FOR SIG SELV - jo prøv at splitte den op
                for (String notFormattedWish : NonFormattedwishes){
                    String[] splittedValues = notFormattedWish.split(":::"); //også helt væk men skal være sådan
                    String wishName = splittedValues[1];
                    String wishDesc = splittedValues[2];
                    String wishUrl = splittedValues[3];
                    int wishPrice = Integer.parseInt(splittedValues[4]);
                    wishes.add(new Wish(wishName, wishDesc, wishPrice, wishUrl));
                }
                return new Wishlist(name, description, wishes);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }*/

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
                    String name = rsWishlist.getString("name");
                    String description = rsWishlist.getString("description");
                    wishlist = new Wishlist(name, description, wishes);
                }
            }

            // Tjek at ønskelisten blev fundet før vi forsøger at hente ønsker
            if (wishlist != null) {
                // Trin 2: Hent Wishes for den givne Wishliste
                String sqlWishes = "SELECT id, name, description, url, price FROM Wish WHERE wishlist_id = ?;";
                try (PreparedStatement psWishes = con.prepareStatement(sqlWishes)) {
                    psWishes.setInt(1, wishlistId);
                    ResultSet rsWishes = psWishes.executeQuery();
                    while (rsWishes.next()) {
                        //int id = rsWishes.getInt("id");
                        String name = rsWishes.getString("name");
                        String description = rsWishes.getString("description");
                        String url = rsWishes.getString("url");
                        double price = rsWishes.getDouble("price");
                        wishes.add(new Wish(name, description, price , url));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return wishlist;
    }

    public Wish getWishById(int wishId) { //relevant for update og edit af wish
        try (Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL =
                    "SELECT * FROM Wish " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, wishId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                String url = resultSet.getString("url");
                return new Wish(name, description, price, url);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public User getUsernameFromID(int ID){
        try(Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL =
                    "SELECT name, password " +
                    "FROM Users " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, ID);
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

    public void insertWish(String name, String description, double price, String url, int wishlistId){
        try (Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL =
                    "INSERT INTO Wish (name, description, price, url, wishlist_id) " +
                    "VALUES (?, ?, ?, ?, ?);";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setDouble(3, price);
            preparedStatement.setString(4, url);
            preparedStatement.setInt(5, wishlistId);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void updateWish(int id, Wish updatedwish) {
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
            preparedStatement.setInt(5, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWish (int ID) {
            try(Connection con = DriverManager.getConnection(db_url, username, pw)){
                String SQL =
                        "DELETE FROM Wish " +
                        "WHERE id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(SQL);
                preparedStatement.setInt(1, ID);
                preparedStatement.executeUpdate();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    public void deleteWishlist (int ID) {
        try(Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL =
                    "DELETE FROM Wishlists " +
                            "WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, ID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}
