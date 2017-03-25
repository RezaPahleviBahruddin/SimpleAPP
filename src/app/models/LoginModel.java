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

    public String isLogin(String username, String password) throws SQLException{
        if (new UserModel().loginUser(username, password))
            return "user";
        else if(new AdminModel().loginAdmin(username, password))
            return "admin";
        else
            return null;
    }
}
