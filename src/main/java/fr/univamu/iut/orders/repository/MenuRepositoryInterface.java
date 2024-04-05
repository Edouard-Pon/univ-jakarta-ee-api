package fr.univamu.iut.orders.repository;


import fr.univamu.iut.orders.model.Menu;

import java.util.ArrayList;

public interface MenuRepositoryInterface {
    public void close();
    public Menu getMenu(String id);
//    public ArrayList<Menu> getAllMenus();
//    public boolean addMenu(String userId, ArrayList<DishData> dishesData);
//    public boolean deleteMenu(String id);
//    public boolean updateMenu(String id, String name, String description, double price);
}
