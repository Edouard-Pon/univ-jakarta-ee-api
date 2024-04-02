package fr.univamu.iut.univjakartaeeapi.repository;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import fr.univamu.iut.univjakartaeeapi.model.UserRole;
import org.bson.Document;
import fr.univamu.iut.univjakartaeeapi.model.User;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepositoryMongodb implements UserRepositoryInterface {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public UserRepositoryMongodb() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(input);
            mongoClient = MongoClients.create(prop.getProperty("mongodb.url"));
            database = mongoClient.getDatabase(prop.getProperty("mongodb.database"));
            collection = database.getCollection(prop.getProperty("mongodb.collection.users"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void close() {
        mongoClient.close();
    }

    @Override
    public User getUser(String id) {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        return doc == null ? null : userFromDocument(doc);
    }

    @Override
    public ArrayList<User> getAllUsers() {
        List<Document> docs = collection.find().into(new ArrayList<>());
        ArrayList<User> users = new ArrayList<>();
        for (Document doc : docs) {
            users.add(userFromDocument(doc));
        }
        return users;
    }

    @Override
    public boolean addUser(String username, String password) {
        Document doc = new Document("username", username)
            .append("password", password);
        return collection.insertOne(doc).wasAcknowledged();
    }

    private User userFromDocument(Document doc) {
        return new User(doc.getObjectId("_id").toString(), doc.getString("username"), doc.getString("password"), UserRole.valueOf(doc.getString("role").toUpperCase()));
    }
}