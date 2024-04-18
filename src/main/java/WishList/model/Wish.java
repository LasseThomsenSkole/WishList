package WishList.model;

public class Wish {
    private String name;
    private String description;
    private double price;
    private String url;
    private int id;

    public Wish(String name, String description, double price, String url){
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
    }
    public Wish(int id,String name, String description, double price, String url){
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.url = url;
    }
    public Wish(){}

    public int getId(){
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
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

    public String getUrl() {
        return url;
    }

}
