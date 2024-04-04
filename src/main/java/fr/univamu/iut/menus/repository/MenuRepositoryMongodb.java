package fr.univamu.iut.menus.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import fr.univamu.iut.menus.model.Dish;
import fr.univamu.iut.menus.model.DishData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.bson.types.ObjectId;
import fr.univamu.iut.menus.model.Menu;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                .append("userId", userId);
        return collection.replaceOne(Filters.eq("_id", new ObjectId(id)), doc).wasAcknowledged();
    }

    @Override
    public boolean addMenu(String userId, ArrayList<DishData> dishesData) {
        List<Document> dishes = new ArrayList<>();
        for (DishData dishData : dishesData) {
            dishes.add(new Document("dishId", new ObjectId(dishData.getDishId()))
                    .append("quantity", dishData.getQuantity()));
        }

        Document doc = new Document("userId", new ObjectId(userId))
                .append("creationDate", LocalDateTime.now().toString())
                .append("updateDate", LocalDateTime.now().toString())
                .append("dishes", dishes);
        return collection.insertOne(doc).wasAcknowledged();
    }

    @Override
    public boolean deleteMenu(String id) {
        return collection.deleteOne(Filters.eq("_id", new ObjectId(id))).wasAcknowledged();
    }

    private Menu menuFromDocument(Document doc) {
        // Get the list of Dish documents from the document
        List<Document> dishDocs = (List<Document>) doc.get("dishes");

        // Check if dishDocs is null and initialize it to an empty list if it is
        if (dishDocs == null) {
            dishDocs = new ArrayList<>();
        }

        // Create a new list to hold the Dish objects
        ArrayList<Map<String, Object>> dishes = new ArrayList<>();

        // Convert each Dish document into a Map and add it to the list
        for (Document dishDoc : dishDocs) {
            Map<String, Object> dish = new HashMap<>();
            String dishId = dishDoc.getObjectId("_id").toString();
            Integer quantity = dishDoc.getInteger("quantity");

            // Call the second API to get the full dish data by dishId
//            Dish fullDishData = getDishById(dishId);
            Dish fullDishData = null;

            // Add the full dish data and quantity to the dish map
            dish.put("dishData", fullDishData);
            dish.put("quantity", quantity);
            dishes.add(dish);
        }

        // Create a new Menu object using the converted dishes
        return new Menu(
                doc.getObjectId("_id").toString(),
                doc.getObjectId("userId").toString(),
                doc.getString("creationDate"),
                doc.getString("updateDate"),
                dishes
        );
    }
}
