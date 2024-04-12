package WishList.controller;

import WishList.model.Wish;
import WishList.model.Wishlist;
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
    public String homepage (Model model, @PathVariable int userID) {
        //createWishList knap til oprettelse af wishlist
        //Wishlists (i flertal) skal være herinde
        //Slet ønskeliste knap

        return "homepage";
    }

    /** Wishlist page **/
    @GetMapping("/{wishlistID}") //viser en persons wishlist via ID
    public String getWishlist(Model model, @PathVariable int wishlistID){
        model.addAttribute("wishlist", service.getWishlist(wishlistID));
        return "getWishlist";
    }

    /** Opret ønskeliste - navn **/

    @GetMapping("/create") //unik url // Det her er vel create wish? ikke wishlist - andrea
    public String createWishList(Model model){
        model.addAttribute("wishlist", new Wishlist());
        return "createWishList";
    }

    @PostMapping("/{userID}/create") //TODO HVORDAN SKAL USERID VÆRE DER
    public String postWishList(@ModelAttribute Wishlist wishlist, @PathVariable int userID){
        service.createWishlist(wishlist, userID);
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

    /** Redigering af ønskeliste **/
    @PostMapping("/{wishlistId}/wish/{wishId}/update")
    public String updateWish(@PathVariable int wishlistId, @PathVariable int wishId,
                             @ModelAttribute("wish") Wish wish, Model model) {
        service.updateWish(wishId, wish);
        return "redirect:/wishlist/";
    }
    /** Slet fra ønskeliste **/
    @DeleteMapping("/{wishID}/delete-wish")
    public String deleteWish(@PathVariable int wishID){
         service.deleteWish(wishID);
        return "redirect:getWishlist";
    }

    /** Slet ønskeliste **/
    @DeleteMapping("/{wishlistID}/delete-wishlist") //HUSK at brug 'DELETE' som http request i html
    public String deleteWishlist (@PathVariable int wishlistID) {
        service.deleteWishlist(wishlistID);
        return "redirect: homepage";
    }


    /** INDEN UNDER WISHLIST PAGE: **/
    /** Tilføj ønske - tilføje ønske, url, pris, navn**/
    /** Reservere ønske (IKKE MUST) **/
    /** Dele ønskeliste (IKKE MUST) **/




}
