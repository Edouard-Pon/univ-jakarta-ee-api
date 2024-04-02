package fr.univamu.iut.univjakartaeeapi;

import fr.univamu.iut.univjakartaeeapi.repository.DishRepositoryInterface;
import fr.univamu.iut.univjakartaeeapi.repository.DishRepositoryMongodb;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
public class Main extends Application {

    @Produces
    private DishRepositoryInterface openDbConnection(){
        DishRepositoryMongodb db = null;
        try {
            db = new DishRepositoryMongodb();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return db;
    }

    private void closeDbConnection(@Disposes DishRepositoryInterface dishRepo) {
        dishRepo.close();
    }

}