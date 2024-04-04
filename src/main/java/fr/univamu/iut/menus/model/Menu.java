package fr.univamu.iut.menus.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class Menu {
    protected String id;
    protected String userId;
    protected String creationDate;
    protected String updateDate;
    protected ArrayList<Map<String, Object>> dishes;

    public Menu() {}

    public Menu(String id, String userId, String creationDate, String updateDate, ArrayList<Map<String, Object>> dishes) {
        this.id = id;
        this.userId = userId;
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

    public String getUser() {
        return userId;
    }

    public void setUser(String userId) {
        this.userId = userId;
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

    public ArrayList<Map<String, Object>> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Map<String, Object>> dishes) {
        this.dishes = dishes;
    }

    public void addDish(Dish dish, int quantity) {

    }

    public void removeDish(Dish dish) {
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", creationDate='" + creationDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", dishes=" + dishes +
                '}';
    }
}
