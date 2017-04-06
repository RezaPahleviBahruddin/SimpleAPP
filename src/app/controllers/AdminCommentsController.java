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
 * Created by r32427 on 28/03/17.
 */
public class AdminCommentsController implements Initializable{

    @FXML
    private AnchorPane comment_anchorpane;

    @FXML
    private TableView<CommentsMiddleware> tableView;

    @FXML
    private TableColumn<CommentsMiddleware, String> colNo;

    @FXML
    private TableColumn<CommentsMiddleware, String> colNama;

    @FXML
    private TableColumn<CommentsMiddleware, String> colKomentar;

    @FXML
    private TableColumn<CommentsMiddleware, String> colMenu;

    @FXML
    private TableColumn colAction;

    @FXML
    private JFXButton btnComments;

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnMember;

    @FXML
    private Label labelAdmin;

    private String id;

    private ObservableList<CommentsMiddleware> data;

    private Transition transition = new Transition();

    private CommentsModel commentsModel = new CommentsModel();

    private Sessions sessions = new Sessions();

    private final String sessionName = "login_admin";

    @FXML
    void searchAction(KeyEvent event){
        if (event.getSource().equals(txtSearch)){
            data = commentsModel.getByName(txtSearch.getText());
            tableView.setItems(data);
        }
    }

    @FXML
    void clickAdmin(MouseEvent event) throws IOException{
        if (event.getSource().equals(labelAdmin))
            transition.switchScene(btnLogout, "Admin Management", "admin_manage");
    }

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException{
        if(event.getSource().equals(btnLogout)){
            sessions.deleteSessions(sessionName);
            transition.switchScene(btnLogout, "Login Page", "login");
        }else if(event.getSource().equals(btnAdmin))
            transition.switchScene(btnAdmin, "Admin - Admin Management", "admin_manage");
        else if(event.getSource().equals(btnComments))
            transition.switchScene(btnComments, "Admin - User Comments", "admin_comment");
        else if(event.getSource().equals(btnMember))
            transition.switchScene(btnMember, "Admin - Member Lists", "admin_member");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        if(sessions.isFoundSessions(sessionName))
            labelAdmin.setText(sessions.readSessions(sessionName));
    }

    @FXML
    private void onClickTable(MouseEvent event){
        CommentsMiddleware click = tableView.getSelectionModel().getSelectedItems().get(0);
        id = click.getId();
    }

    private void showData(){
        data = commentsModel.getAll();
        tableView.setItems(data);
    }

    private class ButtonCell extends TableCell<Object, Boolean> {
        final JFXButton cellButtonDelete = new JFXButton("Delete");
        final HBox hb = new HBox(cellButtonDelete);

        ButtonCell(final TableView tblView){
            //delete cell
            cellButtonDelete.setStyle("-fx-background-color: white; -fx-text-fill: teal");
            cellButtonDelete.setDefaultButton(true);
            cellButtonDelete.setOnAction((ActionEvent t) -> {
                int row = getTableRow().getIndex();
                tableView.getSelectionModel().select(row);
                CommentsMiddleware del = new CommentsMiddleware();
                onClickTable(null);
                del.setId(id);
                try{
                    commentsModel.delete(del);
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }finally {
                    transition.showNotif(Alert.AlertType.INFORMATION, "Komentar berhasil dihapus !");
                    id = "";
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
