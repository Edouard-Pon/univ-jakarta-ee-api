package fr.univamu.iut.menus.repository;

import fr.univamu.iut.menus.model.Dish;

import java.util.ArrayList;

public interface DishRepositoryInterface {
    public void close();

    public Dish getDish(String id);

    public ArrayList<Dish> getAllDishes();

    public boolean updateDish(String id, String name, String description, double price);

    public boolean addDish(String name, String description, double price);

    public boolean deleteDish(String id);
}
