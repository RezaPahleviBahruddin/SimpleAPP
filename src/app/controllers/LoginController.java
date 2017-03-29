package app.controllers;

import app.helper.Sessions;
import app.helper.Transition;
import app.models.LoginModel;
import com.jfoenix.controls.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.layout.GridPane;
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

    private Transition transition = new Transition();

    private Sessions sessions = new Sessions();

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException {
        if (event.getSource().equals(btnLogin)){
            try{
                if (loginModel.isLogin(txtUsername.getText(), txtPassword.getText()).equals("user"))
                    System.out.println("Login user: "+txtUsername.getText());
                else if(loginModel.isLogin(txtUsername.getText(), txtPassword.getText()).equals("admin")){
                    sessions.writeSessions("login_admin", txtUsername.getText());
                    transition.switchScene(btnLogin, "Admin - Customer Comments", "admin_comment");
                }
                else
                    showToast(loginContainer, "Login failed, check username & password !", 2000);

                clearText();

            }catch(SQLException e){

            }

        }else if(event.getSource().equals(btnRegister))
            transition.switchScene(btnRegister, "Register Member Page", "register");

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
