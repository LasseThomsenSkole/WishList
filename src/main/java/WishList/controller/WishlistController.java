package WishList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WishlistController {
    @GetMapping("")
    public String createWishList(Model model){
        //kode som opretter en wishlist ????
        return "createWishList";
    }
}
