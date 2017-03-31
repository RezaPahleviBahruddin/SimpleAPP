package app;

import app.db.CreateSchema;
import app.helper.Sessions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class Main extends Application {
    private Sessions sessions = new Sessions();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        primaryStage.setTitle("Food Com APP");

        if (sessions.isFoundSessions("login_admin")){
            root = FXMLLoader.load(getClass().getResource("views/admin_comment.fxml"));
            primaryStage.setTitle("Admin - User Comments");
        }else if(sessions.isFoundSessions("login_user")){
            root = FXMLLoader.load(getClass().getResource("views/user_comment.fxml"));
            primaryStage.setTitle("User - User Comments");
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        /*Uncomment this line to creating database schema*/
//        new CreateSchema().up();
        launch(args);
    }
}
