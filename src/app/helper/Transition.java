package app.helper;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Created by r32427 on 27/03/17.
 */
public class Transition {
    public void fadeTransition(Parent pane, long milis){
        FadeTransition fadeTransition = new FadeTransition(new Duration(milis), pane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(100);
        fadeTransition.setCycleCount(1);
        fadeTransition.setInterpolator(Interpolator.LINEAR);
        fadeTransition.play();
    }

    public void translateTransition(Parent pane, long milis){
        TranslateTransition translateTransition = new TranslateTransition(new Duration(milis), pane);
        translateTransition.setFromX(-300f);
        translateTransition.setToX(300f);
        translateTransition.setAutoReverse(true);
    }

    public void rotateTransition(Parent pane, long milis){
        RotateTransition rotateTransition = new RotateTransition(new Duration(milis), pane);
        rotateTransition.setByAngle(180f);
        rotateTransition.setAutoReverse(true);
    }

    public void switchScene(Button btn, String title, String filename) throws IOException{
        Parent root = null;
        Stage stage = null;

        stage = (Stage) btn.getScene().getWindow();
        stage.setTitle(title);
        stage.centerOnScreen();
        root = FXMLLoader.load(getClass().getResource("../views/"+filename+".fxml"));

        if(root != null && stage != null){
            fadeTransition(root, 2000);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void showNotif(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType,message);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Informasi");
        alert.showAndWait();
    }

    public void showModals(Button btn, String title, String filename) throws IOException{
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("../views/"+filename+".fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(btn.getScene().getWindow());
        stage.showAndWait();

    }
}
