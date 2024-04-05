package fr.univamu.iut.orders.repository;

import fr.univamu.iut.orders.model.Dish;
import fr.univamu.iut.orders.model.Menu;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;


import java.io.StringReader;
import java.util.List;
import java.util.Map;

public class MenusRepositoryAPI implements MenuRepositoryInterface {
    String url;

    public MenusRepositoryAPI(String url) {
        this.url = url;
    }

    @Override
    public void close() {}

    @Override
    public Menu getMenu(String id) {
        Menu menu = new Menu();

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget endpoint = target.path("/menus/" + id);
        Response response = endpoint.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200) {
            String json = response.readEntity(String.class);
            JsonReader jsonReader = Json.createReader(new StringReader(json));
            JsonObject jsonObject = jsonReader.readObject();

            // Convert the 'dishes' object into a Map
            JsonObject dishesObject = jsonObject.getJsonObject("dishes");
            List<Dish> dishesList = new ArrayList<>();
            for (Map.Entry<String, JsonValue> entry : dishesObject.entrySet()) {
                Dish dish = new Dish();
                JsonObject dishObject = entry.getValue().asJsonObject();
                dish.setId(dishObject.getString("id"));
                dish.setName(dishObject.getString("name"));
                dish.setDescription(dishObject.getString("description"));
                dish.setPrice(dishObject.getJsonNumber("price").doubleValue());
                dish.setQuantity(dishObject.getInt("quantity"));
                dishesList.add(dish);
            }

            // Set the 'dishes' field of the 'menu' object
            menu.setDishes(dishesList);

            // Set the other fields of the 'menu' object
            menu.setId(jsonObject.getString("id"));
            menu.setUserId(jsonObject.getString("userId"));
            menu.setUsername(jsonObject.getString("username"));
            menu.setCreationDate(jsonObject.getString("creationDate"));
            menu.setUpdateDate(jsonObject.getString("updateDate"));
        }

        client.close();
        return menu;
    }
}
