package fr.univamu.iut.menus.service;

import com.google.gson.Gson;
import fr.univamu.iut.menus.model.Menu;
import fr.univamu.iut.menus.repository.MenuRepositoryInterface;

public class MenuService {
    protected MenuRepositoryInterface menuRepo;
    private Gson gson;

    public MenuService(MenuRepositoryInterface menuRepo) {
        this.menuRepo = menuRepo;
        this.gson = new Gson();
    }

    public String getAllMenusJSON() {
        return gson.toJson(menuRepo.getAllMenus());
    }

    public String getMenuJSON(String id) {
        Menu menu = menuRepo.getMenu(id);
        return menu == null ? null : gson.toJson(menu);
    }

    public boolean addMenu(String userId, String description, double price) {
        return menuRepo.addMenu(userId);
    }

    public boolean updateMenu(String id, String userId, String description, double price) {
        return menuRepo.updateMenu(id, userId, description, price);
    }

    public boolean deleteMenu(String id) {
        return menuRepo.deleteMenu(id);
    }
}
