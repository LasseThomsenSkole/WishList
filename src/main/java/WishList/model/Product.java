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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
