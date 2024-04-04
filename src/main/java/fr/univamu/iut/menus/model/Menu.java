package fr.univamu.iut.menus.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Menu {
    protected String id;
    protected String userId;
    protected String creationDate;
    protected String updateDate;
    protected ArrayList<Dish> dishes;

    public Menu() {}

    public Menu(String id, String userId, String creationDate, String updateDate, ArrayList<Dish> dishes) {
        this.id = id;
        this.userId = userId;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.dishes = new ArrayList<Dish>();
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

    public ArrayList<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<Dish> dishes) {
        this.dishes = dishes;
    }

    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }

    public void removeDish(Dish dish) {
        this.dishes.remove(dish);
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
