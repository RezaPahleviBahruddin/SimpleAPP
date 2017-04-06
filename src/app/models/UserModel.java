package app.models;

import app.helper.BCrypt;
import app.connection.SqliteConnection;
import app.helper.Sessions;
import app.middleware.UserMiddleware;
import javafx.collections.*;
import java.sql.*;
/**
 * Created by r32427 on 06/03/17.
 */
public class UserModel{
    private  Connection conn;
    private Sessions sessions = new Sessions();


    public boolean loginUser(String username, String password) throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM User " +
                "WHERE username = ? LIMIT 1";
        try {
            preparedStatement  = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next() && BCrypt.checkpw(password, resultSet.getString(6))){
                sessions.writeSessions("login_user", resultSet.getInt(1)+","+resultSet.getString(5));
                return true;
            }
            else
                return false;
        }catch (Exception e){
            return false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public boolean isFoundName(String name, String username) throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM User WHERE name = ? OR username = ?";
        try{
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return true;
            else
                return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    public void insert(UserMiddleware u) throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "INSERT INTO User(" +
                    "name, " +
                    "address, " +
                    "phone, " +
                    "username, " +
                    "password, " +
                    "created_at" +
                ") " +
                    "VALUES" +
                "(?,?,?,?,?,?)";

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getAddress());
            preparedStatement.setString(3, u.getPhone());
            preparedStatement.setString(4, u.getUsername());
            preparedStatement.setString(5, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(8)));
            preparedStatement.setString(6, new Timestamp(System.currentTimeMillis()).toString());

            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }
    }

    public void update(UserMiddleware u) throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "UPDATE User " +
                "SET " +
                    "name=?, " +
                    "address=?, " +
                    "phone=?, " +
                    "username=?, " +
                    "password=? " +
                    "created_at=? " +
                "WHERE id=?";

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, u.getName());
            preparedStatement.setString(2, u.getAddress());
            preparedStatement.setString(3, u.getPhone());
            preparedStatement.setString(4, u.getUsername());
            preparedStatement.setString(5, u.getPassword());
            preparedStatement.setString(6, new Timestamp(System.currentTimeMillis()).toString());
            preparedStatement.setString(7, u.getId());

            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }
    }

    public void delete(UserMiddleware u) throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;
        try{
            String sql = "DELETE FROM User WHERE id=?";

            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, u.getId());

            preparedStatement.execute();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }finally {
            preparedStatement.close();
        }
    }

    public ObservableList getAll(){
        conn = SqliteConnection.connector();
        ObservableList observableList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM User";
            ResultSet resultSet = conn.createStatement().executeQuery(sql);

            while (resultSet.next()){
                UserMiddleware tb = new UserMiddleware();

                tb.setId(resultSet.getString(1));
                tb.setName(resultSet.getString(2));
                tb.setAddress(resultSet.getString(3));
                tb.setPhone(resultSet.getString(4));
                tb.setUsername(resultSet.getString(5));
                tb.setPassword(resultSet.getString(6));

                observableList.add(tb);
            }

        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return observableList;
    }

    public ObservableList getByName(String name) {
        conn = SqliteConnection.connector();
        ObservableList observableList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM User " +
                    "WHERE name like"+"'%"+name+"%'"+"";

            ResultSet resultSet = conn.createStatement().executeQuery(sql);

            while (resultSet.next()){
                UserMiddleware tb = new UserMiddleware();

                tb.setId(resultSet.getString(1));
                tb.setName(resultSet.getString(2));
                tb.setAddress(resultSet.getString(3));
                tb.setPhone(resultSet.getString(4));
                tb.setUsername(resultSet.getString(5));
                tb.setPassword(resultSet.getString(6));

                observableList.add(tb);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return observableList;
    }

}
