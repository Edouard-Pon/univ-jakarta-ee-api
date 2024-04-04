package fr.univamu.iut.menus.model;

import java.util.ArrayList;
import java.util.Map;

public class MenuInput {
    protected String userId;
    protected ArrayList<DishData> dishes;

    public MenuInput() {}

    public MenuInput(String userId, ArrayList<DishData> dishes) {
        this.userId = userId;
        this.dishes = dishes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<DishData> getDishes() {
        return dishes;
    }

    public void setDishes(ArrayList<DishData> dishes) {
        this.dishes = dishes;
    }
}
