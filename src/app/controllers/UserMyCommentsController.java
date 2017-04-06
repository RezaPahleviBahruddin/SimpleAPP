package app.controllers;

import app.helper.*;
import app.middleware.CommentsMiddleware;
import app.models.CommentsModel;
import com.jfoenix.controls.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
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

    private ObservableList<CommentsMiddleware> data;

    private String id;

    private Transition transition = new Transition();

    private Sessions sessions = new Sessions();

    private final String sessionName = "login_user";

    private String arrayTemp[] = new String[3];

    private int id_user;

    private CommentsModel commentsModel = new CommentsModel();

    private String tempData;

    @FXML
    void clickAdmin(MouseEvent event) {
        CommentsMiddleware click = tableView.getSelectionModel().getSelectedItems().get(0);
        tempData = click.getId()+","+click.getMenu()+","+click.getComment();
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
        }else if(event.getSource().equals(btnAdd)){
            transition.showModals(btnAdd, "Komentar", "modal_user");
            showData();
        }
    }

    private void showData(){
        data = commentsModel.getById(id_user);
        tableView.setItems(data);
    }

    @FXML
    void searchAction(KeyEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(sessions.isFoundSessions(sessionName)) {
            arrayTemp = sessions.readSessions(sessionName).split(",");
            labelAdmin.setText(arrayTemp[1]);
            id_user = Integer.parseInt(arrayTemp[0]);
        }

        colNo.setCellValueFactory((TableColumn.CellDataFeatures<CommentsMiddleware, String> cellData) -> cellData.getValue().idProperty());
        colNama.setCellValueFactory((TableColumn.CellDataFeatures<CommentsMiddleware, String> cellData) -> cellData.getValue().id_userProperty());
        colMenu.setCellValueFactory((TableColumn.CellDataFeatures<CommentsMiddleware, String> cellData) -> cellData.getValue().menuProperty());
        colKomentar.setCellValueFactory((TableColumn.CellDataFeatures<CommentsMiddleware, String> cellData) -> cellData.getValue().commentProperty());
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
                clickAdmin(null);
                try{
                    CommentsMiddleware commentsMiddleware = new CommentsMiddleware();
                    commentsMiddleware.setId(tempData.split(",")[0]);
                    commentsModel.delete(commentsMiddleware);
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }finally {
                    transition.showNotif(Alert.AlertType.INFORMATION, "Komentar sukses dihapus !");
                    showData();
                }
            });

            // edit cell
            cellButtonEdit.setStyle("-fx-background-color: white; -fx-text-fill: teal");
            cellButtonEdit.setDefaultButton(true);
            cellButtonEdit.setOnAction((ActionEvent t) -> {
                int row = getTableRow().getIndex();
                tableView.getSelectionModel().select(row);
                clickAdmin(null);
                sessions.writeSessions("crud_user", tempData);
                try{
                    transition.showModals(btnAdd, "Update komentar", "modal_user");
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }finally {
                    showData();
                }
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
