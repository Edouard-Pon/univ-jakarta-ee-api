package fr.univamu.iut.univjakartaeeapi.repository;

import fr.univamu.iut.univjakartaeeapi.model.User;

import java.util.ArrayList;

public interface UserRepositoryInterface {
    public void close();

    public User getUser(String id);

    public ArrayList<User> getAllUsers();

    public boolean addUser(String username, String password);
}
