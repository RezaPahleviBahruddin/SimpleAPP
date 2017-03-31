package app.controllers;

import app.helper.Sessions;
import app.helper.Transition;
import app.middleware.AdminMiddleware;
import app.models.AdminModel;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by r32427 on 30/03/17.
 */
public class AdminModal implements Initializable {
    @FXML
    private Label labelModal;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnSave;

    private String actionStatus;

    private String arrayTemp[];

    private final String sessionName = "crud_admin";

    private Sessions sessions = new Sessions();

    private AdminModel adminModel = new AdminModel();

    private Transition transition = new Transition();

    private String id = "";

    private void closeModal(){
        sessions.deleteSessions(sessionName);
        Stage modal = (Stage) btnCancel.getScene().getWindow();
        modal.close();
    }

    @FXML
    void handleButtonAction(ActionEvent event) throws SQLException{
        String res = "";

        if(event.getSource().equals(btnCancel))
           closeModal();
        else if(event.getSource().equals(btnSave)){
            AdminMiddleware adminMiddleware = new AdminMiddleware();

            adminMiddleware.setUsername(txtUsername.getText());
            adminMiddleware.setPassword(txtPassword.getText());
            adminMiddleware.setId(id);

            if (!actionStatus.equals("update")){
                adminModel.insert(adminMiddleware);
                res = "Insert admin sukses!";
            }else{
                adminModel.update(adminMiddleware);
                res = "Update admin sukses!";
            }

            transition.showNotif(Alert.AlertType.INFORMATION, res);
            closeModal();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actionStatus = "";

        if(sessions.isFoundSessions(sessionName)){
            actionStatus = "update";
            arrayTemp = sessions.readSessions(sessionName).split(",");

            txtUsername.setText(arrayTemp[1]);
            txtPassword.setText(arrayTemp[2]);
            id = arrayTemp[0];

            System.out.println("id: "+id);
            System.out.println("username: "+arrayTemp[1]);
            System.out.println("password: "+arrayTemp[2]);
        }
    }
}
