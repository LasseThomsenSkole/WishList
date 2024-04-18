package WishList.controller;

import WishList.model.User;
import WishList.model.Wish;
import WishList.model.Wishlist;
import WishList.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller //localhost:8081
@RequestMapping("/wishlist")
public class WishlistController {
    private WishlistService service;
    public WishlistController(WishlistService service) {
        this.service = service;
    }

    /** Login side **/
    @GetMapping("")
    public String loginForm(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        Integer userId = service.authenticateUserAndGetId(user.getUsername(), user.getPassword());
        return "redirect:/wishlist/" + userId + "/homepage";
    }

    /** user Forside **/
    @GetMapping("/{userID}/homepage")
    public String homepage (Model model, @PathVariable int userID) { //TODO SET PARAMETERVARIABLE
        List<Wishlist> wishlists = service.getWishlistsFromUserID(userID);
        model.addAttribute("wishlists", wishlists);
        model.addAttribute("userId", userID);
        return "homepage";
    }

    /** Wishlist page **/
    @GetMapping("/{wishlistID}") //viser en wishlist via wishlistID
    public String getWishlist(Model model, @PathVariable int wishlistID){
        model.addAttribute("wishlist", service.getWishlist(wishlistID));
        return "getWishlist"; //det er en html... jeg har bare lavet et dårligt navn - Lasse :)
    }

    /** Opret ønskeliste **/

    @GetMapping("{userID}/create")
    public String createWishList(Model model, @PathVariable int userID){
        model.addAttribute("userId", userID);
        model.addAttribute("wishlist", new Wishlist());
        return "createWishList";
    }

    @PostMapping("/{userID}/create") //TODO HVORDAN SKAL USERID VÆRE DER
    public String postWishList(@ModelAttribute Wishlist wishlist, @PathVariable int userID){
        service.createWishlist(wishlist, userID);
        return "redirect:/wishlist/" + userID + "/homepage"; 
    }

    /** Opret ønske **/
    @GetMapping("/{wishlistID}/createWish") //jeg tror ikke den behøver userid
    public String createWish(Model model, @PathVariable int wishlistID){
        model.addAttribute("wishlistId", wishlistID);
        model.addAttribute("wish", new Wish());
        return "createWish";
    }

    @PostMapping("/{wishlistID}/createWish/save") //skal lige fikse redirect
    public String postWish(@ModelAttribute Wish wish, @PathVariable int wishlistID){
        service.insertWish(wish, wishlistID);
        return "redirect:/wishlist/" + wishlistID;
    }

    /** Rediger ønske **/
    @GetMapping("/{wishlistId}/wish/{wishId}/edit-form")
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
    @PostMapping("/{wishlistId}/wish/{wishId}/edit")
    public String editWish(@PathVariable int wishlistId, @PathVariable int wishId,
                             @ModelAttribute Wish wish, Model model) {
        service.editWish(wishId, wish);
        model.addAttribute("wishlistId", wishlistId);
        return "redirect:/wishlist/" + wishlistId;
    }

    /** Slet fra ønskeliste **/
    @PostMapping("/{wishID}/delete-wish")
    public String deleteWish(@PathVariable int wishID, @RequestParam("wishlistId") int wishlistId){
         service.deleteWish(wishID);
        return "redirect:/wishlist/" + wishlistId;
    }

    /** Slet ønskeliste **/
    @PostMapping("/{wishlistID}/delete-wishlist") //det skal være postmapping med mindre vi ville bruge JavaScript
    public String deleteWishlist (@PathVariable int wishlistID) {
        int userID = service.getUserIDFromWishlistID(wishlistID);
        service.deleteWishlist(wishlistID);
        return "redirect:/" + userID +"/homepage";
    }

    /**Opret Profil**/

    @GetMapping("/createProfile")
    public String createProfile( Model model){
        model.addAttribute("user", new User());
        return "createProfile";
    }

    @PostMapping("/createProfile/save") //skal lige fikse redirect + så man ikke kan have samme username og password
    public String postProfile(@ModelAttribute User user){
        service.createProfile(user);
        return "redirect:/wishlist";
    }


}
