package WishList.model;

import java.util.List;

public class User {
    private String username;
    private String password;
    private List<User> users;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, List<User> users) {
        this.username = username;
        this.password = password;
        this.users = users;
    }

    public User() {
        //Skal være her for createProfile
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setUsername(String username) {
            this.username = username;
    }

    public List<User> getUsers(){
        return users;
    }

}
