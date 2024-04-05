package fr.univamu.iut.menus.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class Menu {
    protected String id;
    protected String userId;
    protected String username;
    protected String creationDate;
    protected String updateDate;
    protected Map<Integer, Dish> dishes; // TODO - replace with ArrayList

    public Menu() {}

    public Menu(String id, String userId, String username, String creationDate, String updateDate, Map<Integer, Dish> dishes) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.dishes = dishes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Map<Integer, Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Map<Integer, Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", dishes=" + dishes +
                '}';
    }
}
