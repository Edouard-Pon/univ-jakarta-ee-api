package fr.univamu.iut.univjakartaeeapi.repository;

import fr.univamu.iut.univjakartaeeapi.model.Dish;

import java.util.ArrayList;

public interface DishRepositoryInterface {
    public void close();

    public Dish getDish(String id);

    public ArrayList<Dish> getAllDishes();

    public boolean updateDish(String id, String name, String description, double price);

    public boolean addDish(String name, String description, double price);

    public boolean deleteDish(String id);
}
