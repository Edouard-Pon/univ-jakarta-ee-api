package fr.univamu.iut.orders.repository;

import fr.univamu.iut.orders.model.Menu;
import fr.univamu.iut.orders.model.Order;

import java.util.ArrayList;

public interface OrderRepositoryInterface {
    public void close();

    public Order getOrder(String id);

    public ArrayList<Order> getAllOrders();

    public boolean addOrder(Menu menu, String address, String deliveryDate, double price);

//    public boolean updateOrder(String id);

//    public boolean deleteOrder(String id);
}
