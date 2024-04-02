package fr.univamu.iut.univjakartaeeapi.repository;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import fr.univamu.iut.univjakartaeeapi.model.Dish;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DishRepositoryMongodb implements DishRepositoryInterface {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public DishRepositoryMongodb() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(input);
            mongoClient = MongoClients.create(prop.getProperty("mongodb.url"));
            database = mongoClient.getDatabase(prop.getProperty("mongodb.database"));
            collection = database.getCollection(prop.getProperty("mongodb.collection"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void close() {
        mongoClient.close();
    }

    @Override
    public Dish getDish(String id) {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        return doc == null ? null : dishFromDocument(doc);
    }

    @Override
    public ArrayList<Dish> getAllDishes() {
        List<Document> docs = collection.find().into(new ArrayList<>());
        ArrayList<Dish> dishes = new ArrayList<>();
        for (Document doc : docs) {
            dishes.add(dishFromDocument(doc));
        }
        return dishes;
    }

    @Override
    public boolean updateDish(String id, String name, String description, double price) {
        Document doc = new Document("_id", new ObjectId(id))
                .append("name", name)
                .append("description", description)
                .append("price", price);
        return collection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc).wasAcknowledged();
    }

    @Override
    public boolean addDish(String name, String description, double price) {
        Document doc = new Document("name", name)
            .append("description", description)
            .append("price", price);
        return collection.insertOne(doc).wasAcknowledged();
    }

    @Override
    public boolean deleteDish(String id) {
        return collection.deleteOne(Filters.eq("_id", new ObjectId(id))).wasAcknowledged();
    }

    private Dish dishFromDocument(Document doc) {
        return new Dish(doc.getObjectId("_id").toString(), doc.getString("name"), doc.getString("description"), doc.getDouble("price"));
    }
}
