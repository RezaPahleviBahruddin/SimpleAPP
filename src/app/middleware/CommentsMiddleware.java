package app.middleware;

import javafx.beans.property.*;

/**
 * Created by r32427 on 24/03/17.
 */
public class CommentsMiddleware {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
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

    public void setNama(String nama) {
        this.nama.set(nama);
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

    public String getNama() {
        return nama.get();
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

    public StringProperty namaProperty() {
        return nama;
    }
}
