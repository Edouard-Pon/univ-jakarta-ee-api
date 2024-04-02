package fr.univamu.iut.menus.model;

public class Menu {
    protected String id;
    protected User user;
    protected String creationDate;
    protected String updateDate;

    public Menu() {}

    public Menu(String id, User user, String creationDate, String updateDate) {
        this.id = id;
        this.user = user;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", creationDate='" + creationDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                '}';
    }
}
