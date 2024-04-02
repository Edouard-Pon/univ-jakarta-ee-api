package fr.univamu.iut.univjakartaeeapi.repository;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import fr.univamu.iut.univjakartaeeapi.model.UserRole;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import fr.univamu.iut.univjakartaeeapi.model.User;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class UserRepositoryMongodb implements UserRepositoryInterface {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Inject
    public UserRepositoryMongodb(MongoClient mongoClient, MongoDatabase database) {
        this.mongoClient = mongoClient;
        this.database = database;
        this.collection = database.getCollection("users");
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
    public User getUser(String username, String password) {
        Document doc = collection.find(Filters.and(Filters.eq("username", username), Filters.eq("password", password))).first();
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
            .append("password", password)
            .append("role", UserRole.SUBSCRIBER.toString()); // default role
        return collection.insertOne(doc).wasAcknowledged();
    }

    private User userFromDocument(Document doc) {
        return new User(doc.getObjectId("_id").toString(), doc.getString("username"), doc.getString("password"), UserRole.valueOf(doc.getString("role").toUpperCase()));
    }
}