package app.controllers;

import app.middleware.ReviewMiddleware;
import app.models.ReviewModel;
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
    private TableView<ReviewMiddleware> treeView;

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
    private TableColumn<ReviewMiddleware, String> tbNo;

    private String id;

    @FXML
    private TableColumn<ReviewMiddleware, String> tbNamaPelanggan;

    @FXML
    private TableColumn<ReviewMiddleware, String> tbMenu;

    @FXML
    private TableColumn<ReviewMiddleware, String> tbKomentar;

    @FXML
    private TableColumn<ReviewMiddleware, String> colAction;

    @FXML
    private Label usrLabel = new Label();

    private ReviewModel reviewModel = new ReviewModel();
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
        tbNo.setCellValueFactory((TableColumn.CellDataFeatures<ReviewMiddleware, String> cellData) -> cellData.getValue().idProperty());
        tbNamaPelanggan.setCellValueFactory((TableColumn.CellDataFeatures<ReviewMiddleware, String> cellData) -> cellData.getValue().nameProperty());
        tbMenu.setCellValueFactory((TableColumn.CellDataFeatures<ReviewMiddleware, String> cellData) -> cellData.getValue().menuProperty());
        tbKomentar.setCellValueFactory((TableColumn.CellDataFeatures<ReviewMiddleware, String> cellData) -> cellData.getValue().commentProperty());
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
        listData = reviewModel.getAll();
        treeView.setItems(listData);
    }

    @FXML
    private void saveAction(ActionEvent event){
        ReviewMiddleware reviewMiddleware = new ReviewMiddleware();
        reviewMiddleware.setName(txtName.getText());
        reviewMiddleware.setMenu(txtMakanan.getText());
        reviewMiddleware.setComment(txtKomentar.getText());
        reviewMiddleware.setId(id);

        try{
            if(statusKode == 0)
                reviewModel.insert(reviewMiddleware);
            else
                reviewModel.update(reviewMiddleware);
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
        ReviewMiddleware reviewMiddleware = new ReviewMiddleware();
        reviewMiddleware.setId(id);
        try{
            reviewModel.delete(reviewMiddleware);
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
            ReviewMiddleware click = treeView.getSelectionModel().getSelectedItems().get(0);
            id = click.getId();
            txtName.setText(click.getName());
            txtMakanan.setText(click.getMenu());
            txtKomentar.setText(click.getComment());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void searchAction(KeyEvent event){
        listData = reviewModel.getByName(txtSearch.getText());
        treeView.setItems(listData);
    }

    @FXML
    private void refreshAction(ActionEvent event){
        clearText();
        showData();
    }

}
