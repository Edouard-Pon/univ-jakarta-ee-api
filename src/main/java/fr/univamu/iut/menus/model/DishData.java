package fr.univamu.iut.menus.model;

public class DishData {
    private String dishId;
    private int quantity;

    public DishData() {}

    public DishData(String dishId, int quantity) {
        this.dishId = dishId;
        this.quantity = quantity;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
