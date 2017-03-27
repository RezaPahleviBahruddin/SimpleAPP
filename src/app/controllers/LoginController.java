package app.controllers;

import app.assets.Transition;
import app.models.LoginModel;
import com.jfoenix.controls.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private GridPane loginContainer;

    private LoginModel loginModel = new LoginModel();

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        Parent root = null;
        Stage stage = null;
        String res = "";

        if (event.getSource().equals(btnLogin)){
            try{
                res = "login failed !";

                if (loginModel.isLogin(txtUsername.getText(), txtPassword.getText()).equals("user"))
                    res = "login user: "+txtUsername.getText()+" success";

                if(loginModel.isLogin(txtUsername.getText(), txtPassword.getText()).equals("admin"))
                    res = "login admin: "+txtUsername.getText()+" success";

                clearText();

                showToast(loginContainer, res, 1000);
            }catch(SQLException e){

            }

        }else if(event.getSource().equals(btnRegister)){
            stage = (Stage) btnRegister.getScene().getWindow();;
            stage.setTitle("Register Member Page");
            root = FXMLLoader.load(getClass().getResource("../views/register.fxml"));
        }


        if(root != null && stage != null){
            new Transition().fadeTransition(root, 2000);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /*clear all textfield*/
    private void clearText(){
        txtUsername.setText("");
        txtPassword.setText("");
    }

    /*show toast*/
    private void showToast(GridPane pane, String message, long timeout){
        JFXSnackbar jfxSnackbar = new JFXSnackbar(pane);
        jfxSnackbar.show(message, timeout);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
