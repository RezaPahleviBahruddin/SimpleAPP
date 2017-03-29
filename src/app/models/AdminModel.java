package app.models;

import app.helper.BCrypt;
import app.connection.SqliteConnection;
import app.middleware.AdminMiddleware;
import javafx.collections.*;
import java.sql.*;

/**
 * Created by r32427 on 14/03/17.
 */
public class AdminModel {
    private Connection conn;

    public boolean loginAdmin(String username, String password) throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Admin " +
            "WHERE " +
                "username = ? LIMIT 1";
        try {
            preparedStatement  = conn.prepareStatement(query);

            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next() && BCrypt.checkpw(password, resultSet.getString(3)))
                return true;
            else
                return false;
        }catch (Exception e){
            return false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public void insert(AdminMiddleware u) throws SQLException {
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;

        try{
            String query = "INSERT INTO Admin(" +
                    "username, " +
                    "password, " +
                    "created_at) " +
                "VALUES" +
                    "(?,?,?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, u.getUsername());
            preparedStatement.setString(2, u.getPassword());
            preparedStatement.setString(3, new Timestamp(System.currentTimeMillis()).toString());

            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }

    }

    public void update(AdminMiddleware u) throws SQLException {
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;

        try{
            String query = "UPDATE Admin " +
                "SET " +
                    "username=?, " +
                    "password=? " +
                    "updated_at=? " +
                "WHERE " +
                    "id=?";
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, u.getUsername());
            preparedStatement.setString(2, u.getPassword());
            preparedStatement.setString(3, new Timestamp(System.currentTimeMillis()).toString());
            preparedStatement.setInt(4, Integer.parseInt(u.getId()));

            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }
    }

    public void delete(AdminMiddleware u) throws SQLException {
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;

        try{
            String query = "DELETE FROM Admin WHERE id=?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, u.getId());

            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }
    }

    public ObservableList<AdminMiddleware> getAll() {
        conn = SqliteConnection.connector();
        ObservableList observableList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM Admin";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            while (resultSet.next()){
                AdminMiddleware tb = new AdminMiddleware();

                tb.setId(Integer.toString(resultSet.getInt(1)));
                tb.setUsername(resultSet.getString(2));
                tb.setPassword(resultSet.getString(3));

                observableList.add(tb);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return observableList;
    }

    public ObservableList <AdminMiddleware>getByName(String name) {

        conn = SqliteConnection.connector();
        ObservableList observableList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM Admin WHERE username " +
                    "like"+"'%"+name+"%'"+"";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            while (resultSet.next()){
                AdminMiddleware tb = new AdminMiddleware();

                tb.setId(Integer.toString(resultSet.getInt(1)));
                tb.setUsername(resultSet.getString(2));
                tb.setPassword(resultSet.getString(3));

                observableList.add(tb);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return observableList;
    }
}
