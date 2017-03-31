package app.controllers;

import app.helper.Sessions;
import app.helper.Transition;
import app.middleware.CommentsMiddleware;
import app.middleware.UserMiddleware;
import app.models.UserModel;
import com.jfoenix.controls.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
 * Created by r32427 on 31/03/17.
 */
public class AdminViewMemberController implements Initializable {
    @FXML
    private AnchorPane comment_anchorpane;

    @FXML
    private TableView<UserMiddleware> tableView;

    @FXML
    private TableColumn<UserMiddleware, String> colNo;

    @FXML
    private TableColumn<UserMiddleware, String> colNama;

    @FXML
    private TableColumn<UserMiddleware, String> colAlamat;

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
    private JFXButton btnMember;

    private Sessions sessions = new Sessions();

    private Transition transition = new Transition();

    private final String sessionName = "login_admin";

    private UserModel userModel = new UserModel();

    private ObservableList<UserMiddleware> data;

    private String id = "";

    private void showData(){
        data = userModel.getAll();
        tableView.setItems(data);
    }

    @FXML
    void clickAdmin(MouseEvent event) {
        UserMiddleware click = tableView.getSelectionModel().getSelectedItems().get(0);
        id = click.getId();
    }

    @FXML
    void handleButtonAction(ActionEvent event) throws IOException{
        if(event.getSource().equals(btnAdmin))
            transition.switchScene(btnAdmin, "Admin - Admin Management", "admin_manage");
        else if(event.getSource().equals(btnComments))
            transition.switchScene(btnComments, "Admin - User Comments", "admin_comment");
        else if(event.getSource().equals(btnMember))
            transition.switchScene(btnMember, "Admin - Member Lists", "admin_member");
        else if(event.getSource().equals(btnLogout)) {
            sessions.deleteSessions(sessionName);
            transition.switchScene(btnLogout, "Login Page", "login");
        }
    }

    @FXML
    void searchAction(KeyEvent event) {
        if (event.getSource().equals(txtSearch)){
            data = userModel.getByName(txtSearch.getText());
            tableView.setItems(data);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(sessions.isFoundSessions(sessionName))
            labelAdmin.setText(sessions.readSessions(sessionName));

        colNo.setCellValueFactory((TableColumn.CellDataFeatures<UserMiddleware, String> cellData) -> cellData.getValue().idProperty());
        colNama.setCellValueFactory((TableColumn.CellDataFeatures<UserMiddleware, String> cellData) -> cellData.getValue().nameProperty());
        colAlamat.setCellValueFactory((TableColumn.CellDataFeatures<UserMiddleware, String> cellData) -> cellData.getValue().addressProperty());
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
                return new AdminViewMemberController.ButtonCell(tableView);
            }
        });
        data = FXCollections.observableArrayList();
        showData();
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
                UserMiddleware del = new UserMiddleware();
                clickAdmin(null);
                del.setId(id);
                try{
                    userModel.delete(del);
                }catch (SQLException e){
                    System.out.println(e.getMessage());
                }finally {
                    transition.showNotif(Alert.AlertType.INFORMATION, "Member berhasil dihapus !");
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
