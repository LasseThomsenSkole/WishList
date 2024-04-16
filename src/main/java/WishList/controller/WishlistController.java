package WishList.controller;

import WishList.model.User;
import WishList.model.Wish;
import WishList.model.Wishlist;
import WishList.service.WishlistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("")
public class WishlistController {
    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @GetMapping("")
    public String loginForm(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/{userID}/homepage")
    public String homepage(Model model, @PathVariable int userID) {
        List<Wishlist> wishlists = service.getWishlistsFromUserID(userID);
        model.addAttribute("wishlists", wishlists);
        return "homepage";
    }

    @GetMapping("/{wishlistID}")
    public String getWishlist(Model model, @PathVariable int wishlistID){
        model.addAttribute("wishlist", service.getWishlist(wishlistID));
        return "getWishlist";
    }

    @GetMapping("/create")
    public String createWishList(Model model){
        model.addAttribute("wishlist", new Wishlist());
        return "createWishList";
    }

    @PostMapping("/{userID}/create")
    public String postWishList(@ModelAttribute Wishlist wishlist, @PathVariable int userID){
        service.createWishlist(wishlist, userID);
        return "redirect:/" + userID + "/homepage";
    }

    @GetMapping("/{wishlistID}/createWish")
    public String createWish(Model model, @PathVariable int wishlistID){
        model.addAttribute("wish", new Wish());
        model.addAttribute("wishlistId", wishlistID);
        return "createWish";
    }

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

    @PostMapping("/{wishlistId}/wish/{wishId}/update")
    public String updateWish(@PathVariable int wishlistId, @PathVariable int wishId, @ModelAttribute("wish") Wish wish, Model model) {
        service.updateWish(wishId, wish);
        return "redirect:/wishlist/" + wishlistId;
    }

    @DeleteMapping("/{wishID}/delete-wish")
    public String deleteWish(@PathVariable int wishID){
        service.deleteWish(wishID);
        return "redirect:/wishlist/" + wishID; // Assuming you have a view to return to after delete
    }

    @PostMapping("/{wishlistID}/delete-wishlist")
    public String deleteWishlist(@PathVariable int wishlistID) {
        int userID = service.getUserIDFromWishlistID(wishlistID);
        service.deleteWishlist(wishlistID);
        return "redirect:/" + userID + "/homepage";
    }

    @GetMapping("/createProfile")
    public String createProfile(Model model){
        model.addAttribute("user", new User());
        return "createProfile";
    }

    @PostMapping("/createProfile")
    public String postProfile(@ModelAttribute User user){
        service.createProfile(user);
        return "redirect:/homepage";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        Integer userId = service.authenticateUserAndGetId(user.getUsername(), user.getPassword());
        if (userId != null) {
            redirectAttributes.addFlashAttribute("userId", userId);
            return "redirect:/" + userId + "/homepage";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login";
        }
    }
}
