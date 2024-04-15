package WishList;

import WishList.model.Wishlist;
import WishList.repository.WishlistJDBC;
import WishList.service.WishlistService;
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

@SpringBootTest
@Sql({"createDB.sql", "InsertTestData.sql"})
class WishListApplicationTests {
    JdbcTemplateAutoConfiguration jdbcTemplate;

    // tror vi skal bruge noget @mock

    @Autowired
    WishlistService service;

    @Test
    void contextLoads() {

    }

    @Test
    void getWishList() {

    }



}
