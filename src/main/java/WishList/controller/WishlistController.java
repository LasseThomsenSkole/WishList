package WishList.controller;

import WishList.model.User;
import WishList.model.Wish;
import WishList.model.Wishlist;
import WishList.service.WishlistService;
import org.h2.engine.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller //localhost:8081
@RequestMapping("")
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
        boolean isAuthenticated = service.authenticateUser(user.getUsername(), user.getPassword());
        if (isAuthenticated) {
            return "redirect:/homepage"; // Redirect to user homepage
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login"; // Stay on login page
        }
    }

    /** user Forside **/
    @GetMapping("{userID}/homepage")
    public String homepage (Model model, @PathVariable int userID) { //TODO SET PARAMETERVARIABLE
        //createWishList knap til oprettelse af wishlist
        model.addAttribute("wishlists", service.getWishlistsFromUserID(userID));
        //knap til at se wishlisten
        return "homepage";
    }

    /** Wishlist page **/
    @GetMapping("/{wishlistID}") //viser en wishlist via wishlistID
    public String getWishlist(Model model, @PathVariable int wishlistID){
        model.addAttribute("wishlist", service.getWishlist(wishlistID));
        return "getWishlist"; //skal den ik returnere en html?
    }

    /** Opret ønskeliste **/

    @GetMapping("/create") //unik url
    public String createWishList(Model model){
        model.addAttribute("wishlist", new Wishlist());
        return "createWishList";
    }

    @PostMapping("/{userID}/create") //TODO HVORDAN SKAL USERID VÆRE DER
    public String postWishList(@ModelAttribute Wishlist wishlist, @PathVariable int userID){
        service.createWishlist(wishlist, userID);
        return "redirect:homepage"; //TODO: rettelse: return "redirect:/" + userID + "/homepage";
    }
    @GetMapping("/{wishlistID}/createWish") //jeg tror ikke den behøver userid
    public String createWish(Model model, @PathVariable int wishlistID){
        model.addAttribute("wish", new Wish());
        model.addAttribute("wishlistId", wishlistID);
        return "createWish";
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
        return "redirect:getWishlist"; // todo: skal det være return "redirect:/wishlist/" + wishlistId; ??
    }

    /** Slet ønskeliste **/
    @PostMapping("/{wishlistID}/delete-wishlist") //det skal være postmapping med mindre vi ville bruge JavaScript
    public String deleteWishlist (@PathVariable int wishlistID) {
        int userID = service.getUserIDFromWishlistID(wishlistID);
        service.deleteWishlist(wishlistID);
        return "redirect:/" + userID +"/homepage";
    }

    @GetMapping("/createProfile")
    public String createProfile(Model model){
        model.addAttribute("user", new User());
        return "createProfile";
    }

    @PostMapping("/createProfile")
    public String postProfile(@ModelAttribute User user){
        service.createProfile(user);
        return "redirect:/homepage"
     
    }


}
