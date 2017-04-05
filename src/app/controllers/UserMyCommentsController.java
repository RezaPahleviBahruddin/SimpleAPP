package app.controllers;

import app.helper.Sessions;
import app.helper.Transition;
import app.middleware.CommentsMiddleware;
import com.jfoenix.controls.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by r32427 on 03/04/17.
 */
public class UserMyCommentsController implements Initializable{
    @FXML
    private AnchorPane comment_anchorpane;

    @FXML
    private TableView<CommentsMiddleware> tableView;

    @FXML
    private TableColumn<CommentsMiddleware, String> colNo;

    @FXML
    private TableColumn<CommentsMiddleware, String> colNama;

    @FXML
    private TableColumn<CommentsMiddleware, String> colMenu;

    @FXML
    private TableColumn<CommentsMiddleware, String> colKomentar;

    @FXML
    private TableColumn colAction;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private Label labelAdmin;

    @FXML
    private JFXButton btnAllComments;

    @FXML
    private JFXButton btnMyComments;

    @FXML
    private JFXButton btnAdd;

    private Transition transition = new Transition();

    private Sessions sessions = new Sessions();

    private final String sessionName = "login_user";
    @FXML
    void clickAdmin(MouseEvent event) {

    }

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException{
        if(event.getSource().equals(btnAllComments))
            transition.switchScene(btnAllComments, "All user comments", "user_comments");
        else if(event.getSource().equals(btnMyComments))
            transition.switchScene(btnMyComments, "My comments", "user_mycomments");
        else if(event.getSource().equals(btnLogout)){
            sessions.deleteSessions(sessionName);
            transition.switchScene(btnLogout, "Login page", "login");
        }
    }

    @FXML
    void searchAction(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(sessions.isFoundSessions(sessionName))
            labelAdmin.setText(sessions.readSessions(sessionName));
    }
}
