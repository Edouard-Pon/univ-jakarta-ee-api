package fr.univamu.iut.menus;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ApplicationScoped
public class DatabaseConnection {
    private MongoClient mongoClient;
    private MongoDatabase database;

    public DatabaseConnection() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(input);
            mongoClient = MongoClients.create(prop.getProperty("mongodb.url"));
            database = mongoClient.getDatabase(prop.getProperty("mongodb.database"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Produces
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    @Produces
    public MongoDatabase getMongoDatabase() {
        return database;
    }

    public void close(@Disposes MongoClient mongoClient) {
        mongoClient.close();
    }
}
