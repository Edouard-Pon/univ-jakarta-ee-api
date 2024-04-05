package fr.univamu.iut.orders.service;

import com.google.gson.Gson;
import fr.univamu.iut.orders.model.Menu;
import fr.univamu.iut.orders.repository.MenusRepositoryAPI;
import fr.univamu.iut.orders.repository.OrderRepositoryInterface;
import fr.univamu.iut.orders.model.Dish;

public class OrderService {
    protected OrderRepositoryInterface orderRepo;
    private Gson gson;

    public OrderService(OrderRepositoryInterface orderRepo) {
        this.orderRepo = orderRepo;
        this.gson = new Gson();
    }

    public String getAllOrdersJSON() {
        return gson.toJson(orderRepo.getAllOrders());
    }

    public String getOrderJSON(String id) {
        return gson.toJson(orderRepo.getOrder(id));
    }

    public boolean addOrder(String menuId, String address, String deliveryDate) {
        MenusRepositoryAPI menusRepo = new MenusRepositoryAPI("http://localhost:8080/menus-1.0-SNAPSHOT/api");

        Menu menu = menusRepo.getMenu(menuId);

        double price = 0.0;
        for (Dish dish : menu.getDishes()) {
            price += dish.getPrice() * dish.getQuantity();
        }

        return orderRepo.addOrder(menu, address, deliveryDate, price);
    }
}
