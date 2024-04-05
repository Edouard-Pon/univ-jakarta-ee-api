package fr.univamu.iut.menus.repository;

import fr.univamu.iut.menus.model.Dish;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

public class DishRepositoryAPI implements DishRepositoryInterface {
    String url;

    public DishRepositoryAPI(String url) {
        this.url = url;
    }

    @Override
    public void close() {}

    @Override
    public Dish getDish(String id) {
        Dish dish = new Dish();

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget endpoint = target.path("/dishes/" + id);
        Response response = endpoint.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200) {
            dish = response.readEntity(Dish.class);
        }

        client.close();
        return dish;
    }

    @Override
    public ArrayList<Dish> getAllDishes() {
        return null;
    }

    @Override
    public boolean addDish(String name, String description, double price) {
        return false;
    }

    @Override
    public boolean deleteDish(String id) {
        return false;
    }

    @Override
    public boolean updateDish(String id, String name, String description, double price) {
        return false;
    }
}
