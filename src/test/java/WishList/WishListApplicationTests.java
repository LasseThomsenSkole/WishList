package WishList;

import WishList.model.Wishlist;
import WishList.repository.WishlistJDBC;
import WishList.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisProperties;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootTest
class WishListApplicationTests {
    JdbcTemplateAutoConfiguration jdbcTemplate;

    /*@Autowired
    private WishlistJDBC repository;

    @BeforeEach
    public void setUp() throws SQLException {
        try (Connection conn = DriverManager.getConnection();
             Statement stmt = conn.createStatement()) {
            // Create necessary tables or clear existing data
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Wishlists (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(30), description VARCHAR(255), user_id INT)");
            stmt.executeUpdate("DELETE FROM Wishlists");
        }
    }*/

    @Test //hvad gør den her?
    void contextLoads() {
    }
/*
    @Test
    void createWishListTest() {
        Wishlist wishlist = new Wishlist("name", "description");
        int userId = 1;

        repository.createWishlist(wishlist, userId);

        Wishlist savedInDatabase = repository.ge

    }*/



}
