package app.models;
import app.connection.SqliteConnection;

import java.sql.*;
/**
 * Created by r32427 on 05/03/17.
 */
public class LoginModel {
    Connection connection;

    public LoginModel(){
        connection = SqliteConnection.connector();
        if(connection == null){
            System.exit(1);
        }
    }

    public boolean isDbConnected(){
        try{
            return !connection.isClosed();
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean loginUser(String username, String password) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Employee WHERE username = ? AND password = ?";
        try {
            preparedStatement  = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
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

    private boolean loginAdmin(String username, String password) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Admin WHERE username = ? AND password = ?";
        try {
            preparedStatement  = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
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

    public String isLogin(String username, String password) throws SQLException{
        if (loginUser(username, password))
            return "user";
        else if(loginAdmin(username, password))
            return "admin";
        else
            return "index";

    }

    public boolean isFoundName(String name) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Employee WHERE name = ?";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
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
}
