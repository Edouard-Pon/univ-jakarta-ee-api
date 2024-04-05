package fr.univamu.iut.univjakartaeeapi.service;

import com.google.gson.Gson;
import fr.univamu.iut.univjakartaeeapi.model.User;
import fr.univamu.iut.univjakartaeeapi.repository.UserRepositoryInterface;
import jakarta.json.Json;
import jakarta.json.JsonObject;

public class UserService {
    protected UserRepositoryInterface userRepo;
    private Gson gson;

    public UserService(UserRepositoryInterface userRepo) {
        this.userRepo = userRepo;
        this.gson = new Gson();
    }

    public String getAllUsersJSON() {
        return gson.toJson(userRepo.getAllUsers());
    }

    public String getUserJSON(String id) {
        User user = userRepo.getUser(id);
        return user == null ? null : gson.toJson(user);
    }

    public boolean addUser(String username, String password) {
        return userRepo.addUser(username, password);
    }

    public String getUsernameByIdJSON(String id) {
        String username = userRepo.getUsernameById(id);
        if (username == null) return null;
        JsonObject json = Json.createObjectBuilder()
                .add("username", username)
                .build();
        return String.valueOf(json);
    }
}
