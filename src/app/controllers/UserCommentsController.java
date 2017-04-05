package app.controllers;

import app.helper.Sessions;
import app.helper.Transition;
import app.middleware.CommentsMiddleware;
import app.models.CommentsModel;
import com.jfoenix.controls.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by r32427 on 01/04/17.
 */
public class UserCommentsController implements Initializable {
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

    private CommentsModel commentsModel = new CommentsModel();

    private Sessions sessions = new Sessions();

    private Transition transition = new Transition();

    private final String sessionName = "login_user";

    private ObservableList<CommentsMiddleware> data;

    private String id;

    @FXML
    private JFXButton btnAllComments;

    @FXML
    private JFXButton btnMyComments;

    @FXML
    void clickAdmin(MouseEvent event) {
        System.out.println("Click on the admin table !");
    }

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException{
        if(event.getSource().equals(btnLogout)){
            sessions.deleteSessions(sessionName);
            transition.switchScene(btnLogout, "Login Page", "login");
        }else if (event.getSource().equals(btnAllComments))
            transition.switchScene(btnAllComments, "All Comments", "user_comments");
        else if(event.getSource().equals(btnMyComments))
            transition.switchScene(btnMyComments, "My Comments !", "user_mycomments");
    }

    @FXML
    void searchAction(KeyEvent event) {
        data = commentsModel.getByName(txtSearch.getText());
        tableView.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id = "";
        if(sessions.isFoundSessions(sessionName))
            labelAdmin.setText(sessions.readSessions(sessionName));

        colNo.setCellValueFactory((TableColumn.CellDataFeatures<CommentsMiddleware, String> cellData) -> cellData.getValue().idProperty());
        colNama.setCellValueFactory((TableColumn.CellDataFeatures<CommentsMiddleware, String> cellData) -> cellData.getValue().namaProperty());
        colMenu.setCellValueFactory((TableColumn.CellDataFeatures<CommentsMiddleware, String> cellData) -> cellData.getValue().menuProperty());
        colKomentar.setCellValueFactory((TableColumn.CellDataFeatures<CommentsMiddleware, String> cellData) -> cellData.getValue().commentProperty());

        data = FXCollections.observableArrayList();
        tableView.getSelectionModel().clearSelection();
        showData();
    }

    private void showData(){
        data = commentsModel.getAll();
        tableView.setItems(data);
    }

}
