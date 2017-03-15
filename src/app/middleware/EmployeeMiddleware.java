package app.middleware;

import java.time.LocalDate;
import javafx.beans.property.*;

public class EmployeeMiddleware{
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty bornDate = new SimpleObjectProperty<>();
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();

    public EmployeeMiddleware() {
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getId() {
        return id.get();
    }

    public String getPassword() {
        return password.get();
    }

    public void setBornDate(Object bornDate) {
        this.bornDate.set(bornDate);
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String value) {
        username.set(value);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public LocalDate getBornDate() {
        return (LocalDate) bornDate.get();
    }

    public void setBornDate(LocalDate value) {
        bornDate.set(value);
    }

    public ObjectProperty bornDateProperty() {
        return bornDate;
    }

}