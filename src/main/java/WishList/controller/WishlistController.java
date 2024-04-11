package WishList.controller;

import WishList.model.Wish;
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
    @GetMapping("")
    public String logIn (Model model){
        return "login";
    }

    /** user Forside **/
    @GetMapping("{ID}/homepage")
    public String homepage (Model model) {
        //createWishList knap til oprettelse af wishlist
        //Wishlists skal være herinde
        //Slet ønskeliste

        return "homepage";
    }

    /** Wishlist page **/
    @GetMapping("/{ID}") //viser en persons wishlist via ID
    public String getWishlist(Model model, @PathVariable int ID){
        model.addAttribute("wishlist", service.getWishlist(ID));
        return "getWishlist";
    }

    /** Slet ønskeliste **/

    /** Opret ønskeliste - navn **/

    @GetMapping("/create") //unik url // Det her er vel create wish? ikke wishlist - andrea
    public String createWishList(Model model){
        //metode som opretter wishlist med et ID, (laver et url), og hvor man kan navngive den.
        //model.addAttribute("", );
        return "createWishList";
    }


    // Metode til at vise formular for redigering af et ønske
    @GetMapping("/{wishlistId}/wish/{wishId}/edit")
    public String showEditWishForm(@PathVariable int wishlistId, @PathVariable int wishId, Model model) {
        Wish wish = service.getWishById(wishId);
        if (wish != null) {
            model.addAttribute("wish", wish);
            model.addAttribute("wishlistId", wishlistId);
            return "editWish";
        } else {
            return "redirect:/wishlist/" + wishlistId;
        }
    }

    /** Slet fra ønskeliste/redigering af ønskeliste **/
    @PostMapping("/{wishlistId}/wish/{wishId}/update")
    public String updateWish(@PathVariable int wishlistId, @PathVariable int wishId,
                             @ModelAttribute("wish") Wish wish, Model model) {
        service.updateWish(wishId, wish);
        return "redirect:/wishlist/";
      

    @DeleteMapping("/{ID}/delete-wish")
    public String deleteWish(@RequestBody int ID){
         service.deleteWish(ID);
        return "homepage"; //ved ikke hvad det her skal være. mici
    }



    /** INDEN UNDER WISHLIST PAGE: **/
    /** Tilføj ønske - tilføje ønske, url, pris, navn**/
    /** Reservere ønske (IKKE MUST) **/
    /** Dele ønskeliste (IKKE MUST) **/




}
