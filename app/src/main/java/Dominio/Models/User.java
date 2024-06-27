package Dominio.Models;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {

    private String email;
    private List<String> favorites;
    private String id;
    private String type;
    private String username;

    public User(String email, List<String> favorites, String id, String type, String username) {
        this.email = email;
        this.favorites = favorites;
        this.id = id;
        this.type = type;
        this.username = username;
    }

    public User() {
    }

    public static User fromMap(Map<String, Object> map) {
        User user = new User();
        user.setEmail((String) map.get("email"));
        user.setFavorites((List<String>) map.get("favorites"));
        user.setId((String) map.get("id"));
        user.setType((String) map.get("type"));
        user.setUsername((String) map.get("username"));
        return user;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("email", email);
        userMap.put("favorites", favorites);
        userMap.put("id", id);
        userMap.put("type", type);
        userMap.put("username", username);
        return userMap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
