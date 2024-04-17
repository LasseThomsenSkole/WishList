package WishList;

import WishList.model.Wish;
import WishList.model.Wishlist;
import WishList.repository.WishlistJDBC;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class) // extender JUnit
@SpringJUnitConfig
public class WishlistJDBCIT {
    @Autowired
    private WishlistJDBC repository;

    //@Value("${spring.datasource.url}")
    private final String db_url = "jdbc:mysql://localhost:3306/WishlistDB";
    //@Value("${spring.datasource.username}")
    private String username = "root";
   // @Value("${spring.datasource.password}")
    private String pw = "-mads18B";

    @Test
    public void getWishById() {
        int id = 1;
        Wish found = repository.getWishById(id);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(id);
    }
   /* @MockBean
    private DataSource dataSource;

   /*@Autowired
    public WishlistJDBCIT(WishlistJDBC repository) {
        this.repository = repository;
    }*/

   /* @Test
    public void testCreateWishList() { //HVORFOR ER ALT NULL LDWKJHFBWELSDKHFB
        String name = "test name";
        String description = "test description";
        List<Wish> wishes = new ArrayList<>();
        Wishlist wishlist = new Wishlist(name, description, wishes);
        int userId = 1;

        repository.createWishlist(wishlist, userId);

        Wishlist savedWishlist = repository.getWishlist(wishlist.getId()); // det er = null
        assertThat(savedWishlist)
                .isNotNull()
                .extracting(Wishlist::getName, Wishlist::getDescription, Wishlist::getId)
                .containsExactly(name, description, wishlist.getId());

    }*/

    private int getWishIdFromDB(Wish wish) {
        int wishId;
        try (Connection con = DriverManager.getConnection(db_url, username, pw)){
            String SQL =
                    "SELECT id FROM Wish " +
                            "WHERE name = ? " +
                            "AND description = ? " +
                            "AND price = ? " +
                            "AND url = ? " +
                            "AND wishlist_id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, wish.getName());
            preparedStatement.setString(2, wish.getDescription());
            preparedStatement.setDouble(3, wish.getPrice());
            preparedStatement.setString(4, wish.getUrl());
            preparedStatement.setInt(5, wish.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                wishId = resultSet.getInt("id");
            } else {
                throw new RuntimeException("");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wishId;
    }

    @Test
    public void testGetWishById() {
        Wish testWish = new Wish("Temporary Wish", "Temporary Description", 50.0, "https://ønskelink.com");
        int testWishId = getWishIdFromDB(testWish);
        repository.insertWish(testWish, testWishId);

        Wish retrievedWish = repository.getWishById(testWishId);

        assertThat(retrievedWish)
                .isNotNull()
                .extracting(Wish::getName, Wish::getDescription, Wish::getPrice, Wish::getUrl)
                .containsExactly(testWish.getName(), testWish.getDescription(), testWish.getPrice(), testWish.getUrl());
    }

}
