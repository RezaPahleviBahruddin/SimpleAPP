package app.controllers;

import app.helper.Sessions;
import app.helper.Transition;
import app.middleware.AdminMiddleware;
import app.middleware.CommentsMiddleware;
import app.models.AdminModel;
import com.jfoenix.controls.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by r32427 on 12/03/17.
 */
public class AdminManageControlller implements Initializable{
    @FXML
    private AnchorPane comment_anchorpane;

    @FXML
    private TableView<AdminMiddleware> tableView;

    @FXML
    private TableColumn<AdminMiddleware, String> colNo;

    @FXML
    private TableColumn<AdminMiddleware, String> colNama;

    @FXML
    private TableColumn<AdminMiddleware, String> colPassword;

    @FXML
    private TableColumn colAction;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private Label labelAdmin;

    @FXML
    private JFXButton btnComments;

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private JFXButton btnAdd;

    private Transition transition = new Transition();

    private Sessions sessions = new Sessions();

    private final String sessionName = "login_admin";

    private ObservableList<AdminMiddleware> data;

    private AdminModel adminModel = new AdminModel();

    @FXML
    void clickAdmin(MouseEvent event) {
        AdminMiddleware click = tableView.getSelectionModel().getSelectedItems().get(0);
        sessions.writeSessions("crud", click.getId()+":"+click.getUsername()+":"+click.getPassword());
    }

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException{
        if(event.getSource().equals(btnAdmin))
            transition.switchScene(btnAdmin, "Admin - Admin Management", "admin_manage");
        else if(event.getSource().equals(btnComments))
            transition.switchScene(btnComments, "Admin - User Comments", "admin_comment");
        else if(event.getSource().equals(btnLogout)){
            sessions.deleteSessions(sessionName);
            transition.switchScene(btnLogout, "Login Page", "login");
        }else if(event.getSource().equals(btnAdd))
            System.out.println("button add admin has been clicked !");
    }

    @FXML
    void searchAction(KeyEvent event) {

    }

    private void showData(){
        data = adminModel.getAll();
        tableView.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(sessions.isFoundSessions(sessionName))
            labelAdmin.setText(sessions.readSessions(sessionName));

        colNo.setCellValueFactory((TableColumn.CellDataFeatures<AdminMiddleware, String> cellData) -> cellData.getValue().idProperty());
        colNama.setCellValueFactory((TableColumn.CellDataFeatures<AdminMiddleware, String> cellData) -> cellData.getValue().usernameProperty());
        colPassword.setCellValueFactory((TableColumn.CellDataFeatures<AdminMiddleware, String> cellData) -> cellData.getValue().passwordProperty());
        colAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Boolean>,
                ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });

        colAction.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
            @Override
            public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
                return new ButtonCell(tableView);
            }
        });

        data = FXCollections.observableArrayList();
        tableView.getSelectionModel().clearSelection();
        showData();

    }

    private class ButtonCell extends TableCell<Object, Boolean> {
        final JFXButton cellButtonDelete = new JFXButton("Delete");
        final JFXButton cellButtonEdit = new JFXButton("Edit");
        final HBox hb = new HBox(cellButtonDelete, cellButtonEdit);

        ButtonCell(final TableView tblView){
            hb.setSpacing(6);

            //delete cell
            cellButtonDelete.setStyle("-fx-background-color: white; -fx-text-fill: teal");
            cellButtonDelete.setDefaultButton(true);
            cellButtonDelete.setOnAction((ActionEvent t) -> {
                int row = getTableRow().getIndex();
                tableView.getSelectionModel().select(row);
                System.out.println("Delete action has been clicked !");
            });

            // edit cell
            cellButtonEdit.setStyle("-fx-background-color: white; -fx-text-fill: teal");
            cellButtonEdit.setDefaultButton(true);
            cellButtonEdit.setOnAction((ActionEvent t) -> {
                int row = getTableRow().getIndex();
                tableView.getSelectionModel().select(row);
                System.out.println("Edit action has been clicked !");
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(hb);
            }else{
                setGraphic(null);
            }
        }
    }

}


