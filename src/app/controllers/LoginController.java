package app.controllers;

import app.models.*;
import app.middleware.EmployeeMiddleware;
import com.jfoenix.controls.JFXSnackbar;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable{
    private LoginModel loginModel = new LoginModel();
    private UserModel userModel = new UserModel();

    public Stage theStage;

    /*container*/
    @FXML
    private AnchorPane loginPane;
    @FXML
    private AnchorPane registratonPane;

    /*login textfield*/
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;

    /*register textfield*/
    @FXML
    private TextField txtNames;
    @FXML
    private DatePicker bornDate;
    @FXML
    private TextField txtUsernames;
    @FXML
    private TextField txtPasswords;

    public LoginController(){
        theStage = new Stage();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    private void showToast(AnchorPane pane, String message, long timeout){
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.show(message, timeout);
    }

    @FXML
    private void login(javafx.event.ActionEvent event){
        try {
            if(txtUsername.getText().equals("") || txtPassword.getText().equals(""))
                showToast(loginPane, "Fill username and password !", 1000);
            else if(loginModel.isLogin(txtUsername.getText(), txtPassword.getText()).equals("user")){
                Parent root = FXMLLoader.load(getClass().getResource("/app/views/MainWindow.fxml"));
                theStage.setTitle("MainWindow");
                Scene scene = new Scene(root);
                theStage.setScene(scene);
                theStage.setResizable(false);
                theStage.show();
            }else if(loginModel.isLogin(txtUsername.getText(), txtPassword.getText()).equals("admin")){
                Parent root = FXMLLoader.load(getClass().getResource("/app/views/Admin.fxml"));
                theStage.setTitle("Admin Page");
                Scene scene = new Scene(root);
                theStage.setScene(scene);
                theStage.setResizable(false);
                theStage.show();
            }
            else
                showToast(loginPane, "Username and password is not correct", 1000);
        }catch (Exception e){
            showToast(loginPane, "Username and password is not correct", 1000);
            e.printStackTrace();
        }
        clearText();
    }

    @FXML
    private void registerUser(javafx.event.ActionEvent event){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/app/views/Register.fxml"));
            theStage.setTitle("Register User");
            Scene scene = new Scene(root);
            theStage.setScene(scene);
            theStage.setResizable(false);
            theStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void keyEvent(){
        txtPassword.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                login(new ActionEvent());
            }
        });

    }

    @FXML
    private void saveUser(javafx.event.ActionEvent event){
        String message;
        boolean redirect = false;
        try {
            if (loginModel.isFoundName(txtNames.getText())) {
                message = "You have registered, please Sign In !";
                redirect = true;
            } else if (txtNames.getText().equals("") || bornDate.getValue().equals("") || txtUsernames.getText().equals("") || txtPasswords.getText().equals("")){
                message = "You have to filled out all form field !";
            }else {
                /*insert into database while user register*/
                EmployeeMiddleware tb = new EmployeeMiddleware();
                tb.setName(txtNames.getText());
                tb.setBornDate(bornDate.getValue());
                tb.setUsername(txtUsernames.getText());
                tb.setPassword(txtPasswords.getText());

                userModel.insert(tb);

                message = "Register Success, Please Sign In !";

                redirect = true;
            }

            /*show toast*/
            showToast(registratonPane, message, 1000);

            /*redirect after success registration*/
            if(redirect) {
                Task<Void> sleeper = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                sleeper.setOnSucceeded(eventAction -> {
                    Stage now = (Stage) txtNames.getScene().getWindow();
                    now.close();
                });

                new Thread(sleeper).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            clearRegisterForm();
        }

    }

    private void clearRegisterForm(){
        txtNames.setText("");
        bornDate.setValue(null);
        txtUsernames.setText("");
        txtPasswords.setText("");
    }

    @FXML
    private void logout(){
        Stage now = (Stage) txtNames.getScene().getWindow();
        now.close();
    }

    private void clearText(){
        txtPassword.setText("");
        txtUsername.setText("");
    }
}
