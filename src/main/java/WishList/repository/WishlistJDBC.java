package WishList.repository;
import WishList.model.Wish;
import WishList.model.Wishlist;
import WishList.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishlistJDBC {
    private final String db_url = ""; //ik hardcode det her hvis vi kan det få det til at fungere uden
    private String username = "";
    private String pw = "";

    //public List<Wishlist> getWishlists(){} //TODO til forside

    public Wishlist getWishlist(int wishlistId){
        try (Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL = "SELECT w.id AS wishlist_id w.name AS wishlist_name w.description AS wishlist_description, GROUP_CONCAT(CONCAT(wi.id, ':::', wi.name, ':::', wi.description, ':::',IFNULL(wi.url, '')) SEPARATOR '|')AS wishes FROM Wishlists w LEFT JOIN Wish wi ON w.id = wi.wishlist_id WHERE w.id = ? GROUP BY w.id;";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, wishlistId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String concatWishes = resultSet.getString("wishes");
                String[] NonFormattedwishes = concatWishes.split("|"); //forvirrende men skal være sådan her

                List<Wish> wishes = new ArrayList<>(); //MÅSKE SKA DET VÆRE EN METODE FOR SIG SELV
                for (String notFormattedWish : NonFormattedwishes){
                    String[] splittedValues = notFormattedWish.split(":::"); //også helt væk men skal være sådan
                    String wishName = splittedValues[1];
                    String wishDesc = splittedValues[2];
                    int wishPrice = Integer.valueOf(splittedValues[3]);
                    String wishUrl = splittedValues[4];
                    wishes.add(new Wish(wishName, wishDesc, wishPrice, wishUrl));
                }
                return new Wishlist(name, description, wishes);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public User getUsernameFromID(int ID){
        try(Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL = "SELECT name, password FROM Users WHERE id = ?";
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

}
