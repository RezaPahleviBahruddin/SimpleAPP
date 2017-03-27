package app.controllers;

import app.middleware.AdminMiddleware;
import app.models.AdminModel;
import com.jfoenix.controls.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by r32427 on 08/03/17.
 */

public class MainWindowController implements Initializable{
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton btnLogout = new JFXButton();

    @FXML
    private TableView<AdminMiddleware> treeView;

    @FXML
    private JFXTextArea txtKomentar;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtMakanan;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnRefresh;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TableColumn<AdminMiddleware, String> tbNo;

    private String id;

    @FXML
    private TableColumn<AdminMiddleware, String> tbNamaPelanggan;

    @FXML
    private TableColumn<AdminMiddleware, String> tbMenu;

    @FXML
    private TableColumn<AdminMiddleware, String> tbKomentar;

    @FXML
    private TableColumn<AdminMiddleware, String> colAction;

    @FXML
    private Label usrLabel = new Label();

    private AdminModel adminModel = new AdminModel();
    private int statusKode;
    private ObservableList listData;

    public void logout(){
        Stage now = (Stage) btnLogout.getScene().getWindow();
        now.close();
    }

    public void setUsrLabel(String user){
        usrLabel.setText(user);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tbNo.setCellValueFactory((TableColumn.CellDataFeatures<AdminMiddleware, String> cellData) -> cellData.getValue().idProperty());
        tbNamaPelanggan.setCellValueFactory((TableColumn.CellDataFeatures<AdminMiddleware, String> cellData) -> cellData.getValue().usernameProperty());
        tbMenu.setCellValueFactory((TableColumn.CellDataFeatures<AdminMiddleware, String> cellData) -> cellData.getValue().passwordProperty());
        tbKomentar.setCellValueFactory((TableColumn.CellDataFeatures<AdminMiddleware, String> cellData) -> cellData.getValue().passwordProperty());
        listData = FXCollections.observableArrayList();
        statusKode = 0;
        showData();
        treeView.getSelectionModel().clearSelection();
    }

    private void showToast(AnchorPane pane, String message, long timeout){
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        snackbar.show(message, timeout);
    }

    private void clearText(){
        txtName.setText("");
        txtKomentar.setText("");
        txtMakanan.setText("");
        statusKode = 0;
    }

    private void showData(){
        listData = adminModel.getAll();
        treeView.setItems(listData);
    }

    @FXML
    private void saveAction(ActionEvent event){
        AdminMiddleware adminMiddleware = new AdminMiddleware();
        adminMiddleware.setUsername(txtName.getText());
        adminMiddleware.setPassword(txtMakanan.getText());
        adminMiddleware.setUsername(txtKomentar.getText());
        adminMiddleware.setId(id);

        try{
            if(statusKode == 0)
                adminModel.insert(adminMiddleware);
            else
                adminModel.update(adminMiddleware);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        if(statusKode == 0)
            showToast(rootPane, "Data Telah Disimpan !", 1000);
        else
            showToast(rootPane, "Data Telah Diupdate !", 1000);

        showData();
        clearText();
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
        clearText();
    }

    @FXML
    private void onClickTable(MouseEvent event){
        statusKode = 1;
        try{
            AdminMiddleware click = treeView.getSelectionModel().getSelectedItems().get(0);
            id = click.getId();
            txtName.setText(click.getUsername());
            txtMakanan.setText(click.getPassword());
            txtKomentar.setText(click.getUsername());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void searchAction(KeyEvent event){
        listData = adminModel.getByName(txtSearch.getText());
        treeView.setItems(listData);
    }

    @FXML
    private void refreshAction(ActionEvent event){
        clearText();
        showData();
    }

}
