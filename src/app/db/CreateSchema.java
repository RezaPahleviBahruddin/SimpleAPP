package app.db;

import app.connection.SqliteConnection;
import java.sql.*;
import static java.lang.System.out;

/**
 * Created by r32427 on 24/03/17.
 */
public class CreateSchema {
    private Connection conn;

    public void up(){
        String res = "";

        try{
            res = (isCreateAdminTable() && isCreateCommentsTable()
                    && isCreateUserTable() && insertAdmin("reza", "@reza27#"))
                    ? "success" : "failed";
        }catch (Exception e){
            out.println(e.getMessage());
        }

        out.println("Database schema is "+res+" to be created !");
    }

    private boolean isCreateAdminTable() throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;

        try{
            String query = "CREATE TABLE `Admin` ( " +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`username` TEXT, " +
                    "`password` TEXT " +
                    "`created_at` TEXT"+
                    "`updated_at` TEXT"+
                ")";

            preparedStatement = conn.prepareStatement(query);

            if (preparedStatement.execute())
                return true;
            else
                return false;
        }catch(Exception e){
            out.println(e.getMessage());
            return false;
        }finally {
            preparedStatement.close();
        }
    }

    private boolean isCreateUserTable() throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;

        try{
            String query = "CREATE TABLE `User` ( " +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`name` TEXT, " +
                    "`address` TEXT, " +
                    "`phone` TEXT, " +
                    "`username` TEXT, " +
                    "`password` TEXT, " +
                    "`created_at` TEXT " +
                    "`updated_at` TEXT"+
                ")";

            preparedStatement = conn.prepareStatement(query);

            if (preparedStatement.execute())
                return true;
            else
                return false;
        }catch(Exception e){
            out.println(e.getMessage());
            return false;
        }finally {
            preparedStatement.close();
        }
    }

    private boolean isCreateCommentsTable() throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;

        try{
            String query = "CREATE TABLE `Comments` ( " +
                    "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                    "`id_user` INTEGER NOT NULL, " +
                    "`menu` TEXT, " +
                    "`comments` TEXT " +
                    "`created_at` TEXT"+
                    "`updated_at` TEXT"+
                ")";

            preparedStatement = conn.prepareStatement(query);

            if (preparedStatement.execute())
                return true;
            else
                return false;
        }catch(Exception e){
            out.println(e.getMessage());
            return false;
        }finally {
            preparedStatement.close();
        }
    }

    private boolean insertAdmin(String username, String password) throws SQLException{
        conn = SqliteConnection.connector();
        PreparedStatement preparedStatement = null;
        try{
            String query = "INSERT INTO Admin(" +
                    "username, " +
                    "password, " +
                    "created_at" +
                ") " +
                "VALUES" +
                    "(?,?,?)";

            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, new Timestamp(System.currentTimeMillis()).toString());

            if (preparedStatement.execute())
                return true;
            else
                return false;
        }catch (Exception e){
            out.println(e.getMessage());
            return false;
        }finally {
            preparedStatement.close();
        }
    }

}
