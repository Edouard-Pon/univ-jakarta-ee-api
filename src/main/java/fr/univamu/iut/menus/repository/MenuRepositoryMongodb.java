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
        List<Document> dishDocs = (List<Document>) doc.get("dishes");

        if (dishDocs == null) {
            dishDocs = new ArrayList<>();
        }

        Map<Integer, Dish> dishes = new HashMap<>();

        DishRepositoryAPI dishRepo = new DishRepositoryAPI("http://localhost:8080/univ-jakarta-ee-api-1.0-SNAPSHOT/api"); // TODO - replace with the correct URL

        for (Document dishDoc : dishDocs) {
            String dishId = dishDoc.getObjectId("dishId").toString();
            Dish dish = dishRepo.getDish(dishId);
            int quantity = dishDoc.getInteger("quantity");

            dishes.put(quantity, dish);
        }

        return new Menu(
                doc.getObjectId("_id").toString(),
                doc.getObjectId("userId").toString(),
                doc.getString("creationDate"),
                doc.getString("updateDate"),
                dishes
        );
    }
}
