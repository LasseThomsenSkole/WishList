package WishList.controller;

import WishList.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("wishlist") //localhost:8080/wishlist
public class WishlistController {
    private WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    /** Login side **/

    @GetMapping("/{ID}")
    public String getWishlist(Model model, @PathVariable int userID){
        model.addAttribute("wishlist", service.getWishlist(userID));
        return "getWishlist";
    }

    /** user Forside **/
    @GetMapping("{ID}/homepage")
    public String homepage (Model model) {
        //createWishList knap til oprettelse af wishlish
        //Wishlists skal være herinde
        //Slet ønskeliste

        return "homepage";
    }

    /** Wishlist page **/
    @GetMapping("{ID}/your-wishlist")
    public String wishlistPage (Model model) {
        
        //Tilføje ønske, slet ønske
        return  "wishlistPage";
    }

    /** Slet ønskeliste **/

    /** Opret ønskeliste - navn **/

    @GetMapping("/create") //unik url
    public String createWishList(Model model){
        //metode som opretter wishlist med et ID, (laver et url), og hvor man kan navngive den.
        model.addAttribute("", );
        return "createWishList";
    }



    /** INDEN UNDER WISHLIST PAGE: **/
    /** Tilføj ønske - tilføje ønske, url, pris, navn**/
    /** Slet fra ønskeliste/redigering af ønskeliste **/
    /** Reservere ønske (IKKE MUST) **/
    /** Dele ønskeliste (IKKE MUST) **/




}
