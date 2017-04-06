package app.connection;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by r32427 on 06/04/17.
 */
public class MySQLConnection {
    public static Connection connector(){
        String url = "jdbc:mysql://localhost/3306/SimpleAPP";
        String username = "root";
        String password = "@reza27#";
        try{
            Connection conn  = DriverManager.getConnection(url, username, password);
            return conn;
        }catch(Exception e){
            return null;
        }
    }
}
