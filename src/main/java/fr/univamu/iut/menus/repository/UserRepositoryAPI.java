package fr.univamu.iut.menus.repository;

import fr.univamu.iut.menus.model.Dish;
import fr.univamu.iut.menus.model.User;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.StringReader;
import java.util.ArrayList;

public class UserRepositoryAPI implements UserRepositoryInterface {
    String url;

    public UserRepositoryAPI(String url) {
        this.url = url;
    }

    @Override
    public void close() {}

    @Override
    public User getUser(String id) {
        return null;
    }

    @Override
    public String getUsernameById(String id) {
        String username = null;

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        WebTarget endpoint = target.path("/users/" + id + "/username");
        Response response = endpoint.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200) {
            String jsonResponse = response.readEntity(String.class);
            JsonReader jsonReader = Json.createReader(new StringReader(jsonResponse));
            JsonObject jsonObject = jsonReader.readObject();
            username = jsonObject.getString("username");
            jsonReader.close();
        }

        client.close();
        return username;
    }

    @Override
    public User getUser(String username, String password) {
        return null;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return null;
    }

    @Override
    public boolean addUser(String username, String password) {
        return false;
    }
}
