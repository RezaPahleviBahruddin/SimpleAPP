package app.interfaces;

import app.middleware.EmployeeMiddleware;
import app.middleware.ReviewMiddleware;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Created by r32427 on 14/03/17.
 */
public interface InterfacesTemplate2 {
    void insert(ReviewMiddleware u) throws SQLException;
    void update(ReviewMiddleware u) throws SQLException;
    void delete(ReviewMiddleware u) throws SQLException;
    ObservableList getAll();
    ObservableList getByName(String name);
}
