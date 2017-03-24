package app.models;
import app.templates.InterfaceTemplate;
import app.connection.SqliteConnection;
import app.middleware.EmployeeMiddleware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Date;
import java.sql.*;
/**
 * Created by r32427 on 06/03/17.
 */
public class UserModel implements InterfaceTemplate{
    private  Connection conn;

    @Override
    public void insert(EmployeeMiddleware u) throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "INSERT INTO Employee(name, born_date, username, password, created_at) VALUES(?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getBornDate().toString());
            preparedStatement.setString(3, u.getUsername());
            preparedStatement.setString(4, u.getPassword());
            preparedStatement.setString(5, new Date().toString());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            preparedStatement.close();
        }
    }

    @Override
    public void update(EmployeeMiddleware u) throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "UPDATE Employee SET name=?, born_date=?, username=?, password=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getBornDate().toString());
            preparedStatement.setString(3, u.getUsername());
            preparedStatement.setString(4, u.getPassword());
            preparedStatement.setString(5, u.getId());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            preparedStatement.close();
        }
    }

    @Override
    public void delete(EmployeeMiddleware u) throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "DELETE FROM Employee WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, u.getId());
            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            preparedStatement.close();
        }
    }

    @Override
    public ObservableList getAll(){
        conn = SqliteConnection.connector();
        ObservableList observableList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM Employee";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            while (resultSet.next()){
                EmployeeMiddleware tb = new EmployeeMiddleware();
                tb.setId(resultSet.getString(1));
                tb.setName(resultSet.getString(2));
                tb.setBornDate(resultSet.getString(3));
                tb.setUsername(resultSet.getString(4));
                tb.setPassword(resultSet.getString(5));
                observableList.add(tb);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return observableList;
    }

    @Override
    public ObservableList getByName(String name) {
        conn = SqliteConnection.connector();
        ObservableList observableList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM Employee WHERE name like"+"'%"+name+"%'"+"";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            while (resultSet.next()){
                EmployeeMiddleware tb = new EmployeeMiddleware();
                tb.setId(resultSet.getString(1));
                tb.setName(resultSet.getString(2));
                tb.setBornDate(resultSet.getDate(3));
                tb.setUsername(resultSet.getString(4));
                tb.setPassword(resultSet.getString(5));
                observableList.add(tb);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return observableList;
    }

}
