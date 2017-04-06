package app.controllers;

import app.helper.Sessions;
import app.helper.Transition;
import app.middleware.CommentsMiddleware;
import app.models.CommentsModel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by r32427 on 30/03/17.
 */
public class UserModal implements Initializable {
    @FXML
    private Label labelModal;

    @FXML
    private JFXTextField txtMenu;

    @FXML
    private JFXTextField txtComments;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnSave;

    private String actionStatus;

    private String arrayTemp[];

    private final String sessionName = "crud_user";

    private final String sessionUser = "login_user";

    private Sessions sessions = new Sessions();

    private CommentsModel commentsModel = new CommentsModel();

    private Transition transition = new Transition();

    private int id;

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
            CommentsMiddleware commentsMiddleware = new CommentsMiddleware();

            commentsMiddleware.setMenu(txtMenu.getText());
            commentsMiddleware.setComment(txtComments.getText());
            commentsMiddleware.setId_user(Integer.toString(id));
            commentsMiddleware.setId(Integer.toString(id));

            if (!actionStatus.equals("update")){
                commentsModel.insert(commentsMiddleware);
                res = "Insert komentar sukses!";
            }else{
                commentsModel.update(commentsMiddleware);
                res = "Update komentar sukses!";
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

            txtMenu.setText(arrayTemp[1]);
            txtComments.setText(arrayTemp[2]);
            id = Integer.parseInt(arrayTemp[0]);

            System.out.println("menu: "+arrayTemp[1]);
            System.out.println("comments: "+arrayTemp[2]);
        }else
            id = Integer.parseInt(sessions.readSessions(sessionUser).split(",")[0]);

    }
}
