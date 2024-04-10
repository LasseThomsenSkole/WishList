package WishList.controller;

import WishList.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("wishlist") //localhost:8080/wishlist
public class WishlistController {
    private WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    /** Login side **/



    /** user Forside **/
    @GetMapping("{ID}/homepage")
    public String homepage (Model model) {
        //createWishList knap til oprettelse af wishlist
        //Wishlists skal være herinde
        //Slet ønskeliste

        return "homepage";
    }

    /** Wishlist page **/
    @GetMapping("/{ID}")
    public String getWishlist(Model model, @PathVariable int ID){
        model.addAttribute("wishlist", service.getWishlist(ID));
        return "getWishlist";
    }

    /** Slet ønskeliste **/

    /** Opret ønskeliste - navn **/

    @GetMapping("/create") //unik url
    public String createWishList(Model model){
        //metode som opretter wishlist med et ID, (laver et url), og hvor man kan navngive den.
        //model.addAttribute("", );
        return "createWishList";
    }

    @PostMapping("/{ID}/update")
    public String updateWish(Model model, @PathVariable int ID){
        service.updateWish(ID, );
        return "homepage";//ved ikke hvad den skal return
    }

    @DeleteMapping("/{ID}/delete-wish")
    public String deleteWish(@RequestBody int ID){
         service.deleteWish(ID);
        return "homepage"; //ved ikke hvad det her skal være. mici
    }



    /** INDEN UNDER WISHLIST PAGE: **/
    /** Tilføj ønske - tilføje ønske, url, pris, navn**/
    /** Slet fra ønskeliste/redigering af ønskeliste **/
    /** Reservere ønske (IKKE MUST) **/
    /** Dele ønskeliste (IKKE MUST) **/




}
