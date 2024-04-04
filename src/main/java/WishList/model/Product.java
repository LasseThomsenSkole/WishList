package WishList.model;
//SKAL MÅSKE VÆRE ET ANDET STED IDK
public class Product {
    private String name;
    private String description;
    private double price;
    private String url;
    public Product(String name, String description, double price, String url){
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
    }

}
