package app.controllers;

import app.helper.Transition;
import app.middleware.UserMiddleware;
import app.models.UserModel;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.*;

/**
 * Created by r32427 on 27/03/17.
 */
public class RegisterController implements Initializable{
    @FXML
    private JFXTextField txtNama;

    @FXML
    private JFXTextField txtAlamat;

    @FXML
    private JFXTextField txtTelp;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private GridPane registrationPane;

    private UserModel registerModel = new UserModel();

    private Transition transition = new Transition();

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException{
        String res = "register failed !";

        if (event.getSource().equals(btnRegister)){
            try{
                if (registerModel.isFoundName(txtNama.getText(), txtUsername.getText()))
                    res = "user: "+txtUsername.getText()+" telah terdaftar, silahkan login!";
                else if(txtNama.getText().equals("") || txtTelp.equals("") || txtAlamat.getText().equals("")
                        || txtUsername.getText().equals("") || txtPassword.getText().equals(""))
                    res = "anda harus mengisi semua field diatas!";
                else{
                    UserMiddleware u = new UserMiddleware();

                    u.setName(txtNama.getText());
                    u.setPhone(txtTelp.getText());
                    u.setAddress(txtAlamat.getText());
                    u.setUsername(txtUsername.getText());
                    u.setPassword(txtPassword.getText());

                    registerModel.insert(u);
                    res = "Registrasi sukses silahkan login!";
                }

                showToast(registrationPane, res, 2000);

            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }else if(event.getSource().equals(btnBack))
            transition.switchScene(btnBack, "Login Page", "login");

        clearText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void clearText(){
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelp.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
    }

    private void showToast(GridPane pane, String message, long timeout){
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.show(message, timeout);
    }

}
