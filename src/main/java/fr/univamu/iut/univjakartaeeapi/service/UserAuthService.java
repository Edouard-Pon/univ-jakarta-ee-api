package fr.univamu.iut.univjakartaeeapi.service;

import fr.univamu.iut.univjakartaeeapi.repository.UserRepositoryInterface;
import fr.univamu.iut.univjakartaeeapi.model.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserAuthService {
    @Inject
    private UserRepositoryInterface userRepository;

    @Inject
    private TokenService tokenService;

    public String authenticate(String username, String password) {
        User user = userRepository.getUser(username, password);
        if (user != null && user.getPassword().equals(password)) {
            return tokenService.generateToken(username, user.getRole().toString().toUpperCase());
        }
        return null;
    }
}