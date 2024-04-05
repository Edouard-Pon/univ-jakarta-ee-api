package fr.univamu.iut.menus.repository;


import fr.univamu.iut.menus.model.User;

import java.util.ArrayList;

public interface UserRepositoryInterface {
    public void close();

    public User getUser(String id);

    public String getUsernameById(String id);

    public User getUser(String username, String password);

    public ArrayList<User> getAllUsers();

    public boolean addUser(String username, String password);
}
