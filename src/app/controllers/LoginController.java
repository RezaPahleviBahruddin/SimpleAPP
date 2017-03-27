package app.controllers;

import app.models.LoginModel;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import java.net.URL;
import java.sql.SQLException;
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

    private LoginModel loginModel = new LoginModel();

    @FXML
    void handleButtonAction(ActionEvent event) throws SQLException{
        Parent root = null;
        Stage stage = null;

        if (event.getSource() == btnLogin){
            if (loginModel.isLogin(txtUsername.getText(), txtPassword.getText()).equals("user")){
                System.out.println("login user: "+txtUsername.getText()+" success");
            }else if(loginModel.isLogin(txtUsername.getText(), txtPassword.getText()).equals("admin")){
                System.out.println("login admin: "+txtUsername.getText()+" succese");
            }else{
                System.out.println("login failed !!");
            }
        }

        if(root != null && stage != null){
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
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
