package app.models;

import app.connection.SqliteConnection;
import app.middleware.CommentsMiddleware;
import javafx.collections.*;
import java.sql.*;

/**
 * Created by r32427 on 25/03/17.
 */
public class CommentsModel {
    private Connection conn;

    public ObservableList<CommentsMiddleware> getAll(){
        conn = SqliteConnection.connector();
        ObservableList observableList = FXCollections.observableArrayList();
        try{
            String sql =
                    "SELECT " +
                        "c.id," +
                        "u.name," +
                        "c.menu," +
                        "c.comments " +
                    "FROM " +
                        "User u " +
                    "INNER JOIN " +
                        "Comments c " +
                            "ON c.id_user = u.id";

            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            while (resultSet.next()){
                CommentsMiddleware tb = new CommentsMiddleware();

                tb.setId(Integer.toString(resultSet.getInt(1)));
                tb.setNama(resultSet.getString(2));
                tb.setMenu(resultSet.getString(3));
                tb.setComment(resultSet.getString(4));

                observableList.add(tb);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return observableList;
    }

    public ObservableList<CommentsMiddleware> getByName(String name){
        conn = SqliteConnection.connector();
        ObservableList observableList = FXCollections.observableArrayList();
        try{
            String sql =
                    "SELECT " +
                        "c.id," +
                        "u.name," +
                        "c.menu," +
                        "c.comments " +
                    "FROM " +
                        "User u " +
                    "INNER JOIN " +
                        "Comments c " +
                            "ON c.id_user = u.id "+
                    "WHERE u.name LIKE '%"+name+"%'";

            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            while (resultSet.next()){
                CommentsMiddleware tb = new CommentsMiddleware();

                tb.setId(Integer.toString(resultSet.getInt(1)));
                tb.setNama(resultSet.getString(2));
                tb.setMenu(resultSet.getString(3));
                tb.setComment(resultSet.getString(4));

                observableList.add(tb);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return observableList;
    }

    public void delete(CommentsMiddleware u) throws SQLException {
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;

        try{
            String query = "DELETE FROM Comments WHERE id=?";
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
}
