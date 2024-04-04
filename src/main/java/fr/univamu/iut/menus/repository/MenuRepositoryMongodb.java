package fr.univamu.iut.menus.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import fr.univamu.iut.menus.model.Dish;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import fr.univamu.iut.menus.model.Menu;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MenuRepositoryMongodb implements MenuRepositoryInterface {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Inject
    public MenuRepositoryMongodb(MongoClient mongoClient, MongoDatabase database) {
        this.mongoClient = mongoClient;
        this.database = database;
        this.collection = database.getCollection("menus");
    }

    @Override
    public void close() {
        mongoClient.close();
    }

    @Override
    public Menu getMenu(String id) {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        return doc == null ? null : menuFromDocument(doc);
    }

    @Override
    public ArrayList<Menu> getAllMenus() {
        List<Document> docs = collection.find().into(new ArrayList<>());
        ArrayList<Menu> menus = new ArrayList<>();
        for (Document doc : docs) {
            menus.add(menuFromDocument(doc));
        }
        return menus;
    }

    @Override
    public boolean updateMenu(String id, String userId, String description, double price) {
        Document doc = new Document("_id", new ObjectId(id))
                .append("userId", userId)
                .append("description", description)
                .append("price", price);
        return collection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc).wasAcknowledged();
    }

    @Override
    public boolean addMenu(String userId) {
        Document doc = new Document("userId", userId);
        return collection.insertOne(doc).wasAcknowledged();
    }

    @Override
    public boolean deleteMenu(String id) {
        return collection.deleteOne(Filters.eq("_id", new ObjectId(id))).wasAcknowledged();
    }

    private Menu menuFromDocument(Document doc) {
        // Get the list of Dish documents from the document
        List<Document> dishDocs = (List<Document>) doc.get("dishes");

        // Create a new list to hold the Dish objects
        ArrayList<Dish> dishes = new ArrayList<>();

        // Convert each Dish document into a Dish object and add it to the list
        for (Document dishDoc : dishDocs) {
            Dish dish = new Dish(
                    dishDoc.getString("_id"),
                    dishDoc.getString("name"),
                    dishDoc.getString("description"),
                    dishDoc.getDouble("price")
            );
            dishes.add(dish);
        }

        // Create a new Menu object using the converted dishes
        return new Menu(
                doc.getObjectId("_id").toString(),
                doc.getString("userId"),
                doc.getString("creationDate"),
                doc.getString("updateDate"),
                dishes
        );
    }
}
