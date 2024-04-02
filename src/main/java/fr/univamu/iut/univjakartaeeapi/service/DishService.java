package fr.univamu.iut.univjakartaeeapi.service;

import com.google.gson.Gson;
import fr.univamu.iut.univjakartaeeapi.model.Dish;
import fr.univamu.iut.univjakartaeeapi.repository.DishRepositoryInterface;

public class DishService {
    protected DishRepositoryInterface dishRepo;
    private Gson gson;

    public DishService(DishRepositoryInterface dishRepo) {
        this.dishRepo = dishRepo;
        this.gson = new Gson();
    }

    public String getAllDishesJSON() {
        return gson.toJson(dishRepo.getAllDishes());
    }

    public String getDishJSON(String id) {
        Dish dish = dishRepo.getDish(id);
        return dish == null ? null : gson.toJson(dish);
    }

    public boolean addDish(String name, String description, double price) {
        return dishRepo.addDish(name, description, price);
    }

    public boolean updateDish(String id, String name, String description, double price) {
        return dishRepo.updateDish(id, name, description, price);
    }

    public boolean deleteDish(String id) {
        return dishRepo.deleteDish(id);
    }
}
