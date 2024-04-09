package WishList.model;
import java.util.List;

public class Wishlist {
    private String name;
    private String description;
    private List<Wish> wishes;

    public Wishlist(String name, String description, List<Wish> wishes){
        this.name = name;
        this.description = description;
        this.wishes = wishes;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public List<Wish> getWishes(){
        return wishes;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setWishes(List<Wish> wishes){
        this.wishes = wishes;
    }
}
