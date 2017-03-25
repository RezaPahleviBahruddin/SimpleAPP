package app.middleware;

import javafx.beans.property.*;

/**
 * Created by r32427 on 14/03/17.
 */
public class AdminMiddleware {

    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public void setId(String id) {
        this.id.set(id);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getId() {
        return id.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty usernameProperty() {
        return username;
    }
}
