package app.controllers;

import app.middleware.AdminMiddleware;
import app.models.AdminModel;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by r32427 on 12/03/17.
 */
public class AdminControlller implements Initializable{
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableView<AdminMiddleware> tableView;

    @FXML
    private TableColumn<AdminMiddleware, String> colNo;

    @FXML
    private TableColumn<AdminMiddleware, String> colNama;

    @FXML
    private TableColumn<AdminMiddleware, String> colMenu;

    @FXML
    private TableColumn<AdminMiddleware, String> colKomentar;

    private String id;

    private void showToast(AnchorPane pane, String message, long timeout){
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.show(message, timeout);
    }

    @FXML
    private void searchAction(KeyEvent event) {
        listData = adminModel.getByName(txtSearch.getText());
        tableView.setItems(listData);
    }

    @FXML
    private void deleteAction(ActionEvent event){
        AdminMiddleware adminMiddleware = new AdminMiddleware();
        adminMiddleware.setId(id);

        try{
            adminModel.delete(adminMiddleware);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        showToast(rootPane, "Data Telah Dihapus", 1000);
        showData();
    }

    private AdminModel adminModel = new AdminModel();
    private ObservableList listData;

    @FXML
    private void logout(){
        Stage now = (Stage) txtSearch.getScene().getWindow();
        now.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNo.setCellValueFactory((TableColumn.CellDataFeatures<AdminMiddleware, String> cellData) -> cellData.getValue().idProperty());
        colNama.setCellValueFactory((TableColumn.CellDataFeatures<AdminMiddleware, String> cellData) -> cellData.getValue().usernameProperty());
        colMenu.setCellValueFactory((TableColumn.CellDataFeatures<AdminMiddleware, String> cellData) -> cellData.getValue().passwordProperty());
        colKomentar.setCellValueFactory((TableColumn.CellDataFeatures<AdminMiddleware, String> cellData) -> cellData.getValue().commentProperty());

        listData = FXCollections.observableArrayList();
        showData();

        tableView.getSelectionModel().clearSelection();
    }

    private void showData(){
        listData = adminModel.getAll();
        tableView.setItems(listData);
    }

    @FXML
    private void onClickTable(MouseEvent event){
        try{
            AdminMiddleware click = tableView.getSelectionModel().getSelectedItems().get(0);
            id = click.getId();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
