package WishList.model;

public class User {
    private String name;
    private String password;

    public User(String username, String password){
        this.name = username;
        this.password = password;
    }

    public User() {
        //Skal være her for createProfile
    }


    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }



}
