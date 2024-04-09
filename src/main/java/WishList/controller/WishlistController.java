package WishList.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WishlistController {
    @GetMapping("")
    public String createWishList(Model model){
        //metode som opretter wishlist med et ID, (laver et url), og hvor man kan navngive den.
        model.addAttribute("", );
        return "createWishList";
    }
}
