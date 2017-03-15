package app.middleware;

import javafx.beans.property.*;

/**
 * Created by r32427 on 14/03/17.
 */
public class ReviewMiddleware {

    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty menu = new SimpleStringProperty();
    private final StringProperty comment = new SimpleStringProperty();

    public void setId(String id) {
        this.id.set(id);
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public void setMenu(String menu) {
        this.menu.set(menu);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getId() {
        return id.get();
    }

    public String getComment() {
        return comment.get();
    }

    public String getMenu() {
        return menu.get();
    }

    public String getName() {
        return name.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public StringProperty menuProperty() {
        return menu;
    }

    public StringProperty nameProperty() {
        return name;
    }
}
