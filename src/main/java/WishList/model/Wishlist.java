package WishList.model;
import java.util.List;

public class Wishlist {
    private String name;
    private String description;
    private List<Wish> wishes;
    private int id;

    public Wishlist(String name, String description, List<Wish> wishes){
        this.name = name;
        this.description = description;
        this.wishes = wishes;
    }

    public Wishlist(int id, String name, String description, List<Wish> wishes){
        this.name = name;
        this.description = description;
        this.wishes = wishes;
        this.id = id;
    }

    public Wishlist(){}//den her skal være der for at /create virker

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

    public int getId() {return id;}


}
