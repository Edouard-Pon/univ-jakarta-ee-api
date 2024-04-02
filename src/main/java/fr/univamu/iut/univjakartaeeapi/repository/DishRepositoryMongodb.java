package fr.univamu.iut.univjakartaeeapi.repository;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import fr.univamu.iut.univjakartaeeapi.model.Dish;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class DishRepositoryMongodb implements DishRepositoryInterface {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Inject
    public DishRepositoryMongodb(MongoClient mongoClient, MongoDatabase database) {
        this.mongoClient = mongoClient;
        this.database = database;
        this.collection = database.getCollection("dishes");
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
