package fr.univamu.iut.orders.model;

public class Order {
    protected String id;
    protected String deliveryDate;
    protected String creationDate;
    protected String address;
    protected double price;
    protected Menu menu;

    public Order() {}

    public Order(String id, String deliveryDate, String creationDate, String address, double price, Menu menu) {
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.creationDate = creationDate;
        this.address = address;
        this.price = price;
        this.menu = menu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", deliveryDate='" + deliveryDate + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", menu=" + menu +
                '}';
    }
}
