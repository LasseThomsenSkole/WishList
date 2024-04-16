package WishList;

import WishList.model.Wish;
import WishList.model.Wishlist;
import WishList.repository.WishlistJDBC;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class) // extender JUnit
public class WishlistJDBCIT {
    @Autowired
    private WishlistJDBC repository;
   /* @MockBean
    private DataSource dataSource;

   /*@Autowired
    public WishlistJDBCIT(WishlistJDBC repository) {
        this.repository = repository;
    }*/

    @Test
    public void testCreateWishList() {
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

}
