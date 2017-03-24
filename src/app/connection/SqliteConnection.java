package app.connection;
import java.sql.*;
/**
 * Created by r32427 on 05/03/17.
 */
public class SqliteConnection {
    public static Connection connector(){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection conn  = DriverManager.getConnection("jdbc:sqlite:src/app/db/EmployeeDB.sqlite");
            return conn;
        }catch(Exception e){
            return null;
        }
    }
}
