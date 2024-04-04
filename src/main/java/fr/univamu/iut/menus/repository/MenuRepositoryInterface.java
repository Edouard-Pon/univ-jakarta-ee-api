package fr.univamu.iut.menus.repository;

import fr.univamu.iut.menus.model.Menu;
import fr.univamu.iut.menus.model.User;

import java.util.ArrayList;

public interface MenuRepositoryInterface {
    public void close();
    public Menu getMenu(String id);
    public ArrayList<Menu> getAllMenus();
    public boolean addMenu(String userId);
    public boolean deleteMenu(String id);
    public boolean updateMenu(String id, String name, String description, double price);
}
