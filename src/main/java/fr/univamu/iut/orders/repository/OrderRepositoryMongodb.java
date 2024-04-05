package fr.univamu.iut.orders.repository;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.univamu.iut.orders.model.Menu;
import fr.univamu.iut.orders.model.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderRepositoryMongodb implements OrderRepositoryInterface {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Inject
    public OrderRepositoryMongodb(MongoClient mongoClient, MongoDatabase database) {
        this.mongoClient = mongoClient;
        this.database = database;
        this.collection = database.getCollection("orders");
    }

    @Override
    public void close() {
        mongoClient.close();
    }

    @Override
    public Order getOrder(String id) {
        Document doc = collection.find(new Document("_id", id)).first();
        return doc == null ? null : orderFromDocument(doc);
    }

    @Override
    public ArrayList<Order> getAllOrders() {
        List<Document> docs = collection.find().into(new ArrayList<>());
        ArrayList<Order> orders = new ArrayList<>();
        for (Document doc : docs) {
            orders.add(orderFromDocument(doc));
        }
        return orders;
    }

    @Override
    public boolean addOrder(Menu menu, String address, String deliveryDate, double price) {
        Gson gson = new Gson();
        String menuJson = gson.toJson(menu);
        Document menuDoc = Document.parse(menuJson);

        Document order = new Document();
        order.append("menu", menuDoc);
        order.append("address", address);
        order.append("deliveryDate", deliveryDate);
        order.append("price", price);
        order.append("creationDate", LocalDateTime.now().toString());

        return collection.insertOne(order).wasAcknowledged();
    }

    private Order orderFromDocument(Document doc) {
        Order order = new Order();

        Document menuDoc = (Document) doc.get("menu");
        Gson gson = new Gson();
        Menu menu = gson.fromJson(menuDoc.toJson(), Menu.class);

        order.setMenu(menu);
        order.setAddress(doc.getString("address"));
        order.setDeliveryDate(doc.getString("deliveryDate"));
        order.setPrice(doc.getDouble("price"));
        order.setCreationDate(doc.getString("creationDate"));
        order.setId(doc.getObjectId("_id").toString());

        return order;
    }
}
