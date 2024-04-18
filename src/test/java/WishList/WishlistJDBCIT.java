package WishList;

import WishList.model.IncorrectWishException;
import WishList.model.Wish;
import WishList.model.Wishlist;
import WishList.repository.WishlistJDBC;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class) // extender JUnit
@SpringJUnitConfig
public class WishlistJDBCIT {

    @Autowired
    private WishlistJDBC repository;


    @Value("jdbc:mysql://localhost:3306/WishlisttestDB")
    private String db_url;

    @Value("root")
    private String uid;

    @Value("Andrea1999!")
    private String pwd;


    @BeforeEach
    public void setup() { //fungerer
        try (
                Connection conn =
                        DriverManager.getConnection(db_url, uid, pwd)) {
            Statement stmt = conn.createStatement();

            String initSchema = "CREATE SCHEMA IF NOT EXISTS WISHLISTDB";
            String dropTableUsers = "drop table if exists users";
            String dropTableWish = "drop table if exists wish";
            String dropTableWishlist = "drop table if exists wishlists";
            stmt.addBatch(initSchema);
            stmt.addBatch(dropTableUsers);
            stmt.addBatch(dropTableWish);
            stmt.addBatch(dropTableWishlist);
            String createTable = "CREATE TABLE wishlists " +
                    "(id INTEGER, " +
                    " name VARCHAR(30), " +
                    " description VARCHAR(30), " +
                    " user_id INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmt.addBatch(createTable);
            stmt.executeBatch();
            System.out.println("Database created");

            String sqlInsertRow = "INSERT INTO wishlists VALUES (10,'Fødselsdag','Til min fødselsdag', 11)";
            stmt.addBatch(sqlInsertRow);
            sqlInsertRow = "INSERT INTO wishlists VALUES (20,'Jul','Julegaver', 21)";
            stmt.addBatch(sqlInsertRow);
            sqlInsertRow = "INSERT INTO wishlists VALUES (30,'Bryllup','Bryllupsgaver', 31)";
            stmt.addBatch(sqlInsertRow);
            sqlInsertRow = "INSERT INTO wishlists VALUES(40,'Alle ønsker','Liste over alle mine ønsker', 41)";
            stmt.addBatch(sqlInsertRow);
            int rows[] = stmt.executeBatch();
            System.out.println("Inserted " + rows.length + " records into the table");
        } catch (SQLException e) {
            System.out.println("Database call went wrong" + e.getMessage());
        }
    }

    @Test
    void findWishlistByID() throws SQLException {
        Wishlist found = repository.getWishlist(10);
        assertEquals("Fødselsdag", found.getName());
    }

    @Test
    void findWishlist30() throws SQLException {
        Wishlist found = repository.getWishlist(30);
        assertNotNull(found);
    }


    @Test
    public void deleteWishlist() throws IncorrectWishException {
        repository.deleteWishlist(20);
    }


    /*


    @Test
    public void getWishById() {
        int id = 1;
        Wish found = repository.getWishById(id);
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(id);
    }*/

/*
    @Test
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

    }
/*
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
*/
}
