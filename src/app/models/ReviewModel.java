package app.models;

import app.connection.SqliteConnection;
import app.interfaces.InterfacesTemplate2;
import app.middleware.ReviewMiddleware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

/**
 * Created by r32427 on 14/03/17.
 */
public class ReviewModel implements InterfacesTemplate2{
    Connection conn;

    @Override
    public void insert(ReviewMiddleware u) throws SQLException {
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;

        try{
            String query = "INSERT INTO Komentar(nama, menu, komentar) VALUES(?,?,?)";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getMenu());
            preparedStatement.setString(3, u.getComment());

            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }

    }

    @Override
    public void update(ReviewMiddleware u) throws SQLException {
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;

        try{
            String query = "UPDATE Komentar SET nama=?, menu=?, komentar=? WHERE idreview=?";
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getMenu());
            preparedStatement.setString(3, u.getComment());
            preparedStatement.setString(4, u.getId());

            preparedStatement.execute();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }
    }

    @Override
    public void delete(ReviewMiddleware u) throws SQLException {
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;
        try{
            String query = "DELETE FROM Komentar WHERE idreview=?";
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

    @Override
    public ObservableList getAll() {
        conn = SqliteConnection.connector();
        ObservableList observableList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM Komentar";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            while (resultSet.next()){
                ReviewMiddleware tb = new ReviewMiddleware();
                tb.setId(Integer.toString(resultSet.getInt(1)));
                tb.setName(resultSet.getString(2));
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

    @Override
    public ObservableList getByName(String name) {

        conn = SqliteConnection.connector();
        ObservableList observableList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM Komentar WHERE nama like"+"'%"+name+"%'"+"";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);
            while (resultSet.next()){
                ReviewMiddleware tb = new ReviewMiddleware();
                tb.setId(resultSet.getString(1));
                tb.setName(resultSet.getString(2));
                tb.setMenu(resultSet.getString(3));
                tb.setComment(resultSet.getString(4));
                observableList.add(tb);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return observableList;
    }
}
