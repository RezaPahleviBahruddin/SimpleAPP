package app.interfaces;
import app.middleware.EmployeeMiddleware;
import javafx.collections.*;
import java.sql.SQLException;

/**
 * Created by r32427 on 06/03/17.
 */
public interface InterfaceTemplate {
    void insert(EmployeeMiddleware u) throws SQLException;
    void update(EmployeeMiddleware u) throws SQLException;
    void delete(EmployeeMiddleware u) throws SQLException;
    ObservableList getAll();
    ObservableList getByName(String name);
}
