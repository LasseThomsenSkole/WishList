package WishList.repository;

import WishList.model.Wishlist;

import java.util.List;

public class WishlistJDBC {
    private final String db_url = ""; //ik hardcode det her hvis vi kan det få det til at fungere uden
    private String username = "";
    private String pw = "";

    //public List<Wishlist> getWishlists(){} //TODO til forside

    public Wishlist getWishlist(int wishlistId){
        try (Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL = "SELECT name, description FROM Wishlists WHERE id = ?"; //TODO BRUG CONCAT
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setInt(1, wishlistId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                return new Wishlist(name, description);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

}
