package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import dto.UserDto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.custom.UserService;
import util.ServiceType;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class UserManagementFormController  implements Initializable {

    public TableColumn colNationalIdNo;
    @FXML
    private TableColumn<UserDto, String> ColName;

    @FXML
    private TableColumn<UserDto, String> colAddress;

    @FXML
    private TableColumn<UserDto, String> colContact;

    @FXML
    private TableColumn<UserDto, Integer> colUserId;

    @FXML
    private TableView<UserDto> tblUser;

    @FXML
    private JFXTextArea txtSearch;
    UserService userService = ServiceFactory.getInstance().getServiceType(ServiceType.USER);

    @FXML
    void btnAddUserOnAction(ActionEvent event) throws IOException {

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/addNewUser.fxml"))));
        stage.show();

    }
    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        ColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNationalIdNo.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
        loadTable();

    }

    private void loadTable() {
        try {
            List<UserDto> all = userService.getAll();
            tblUser.setItems(FXCollections.observableArrayList(all));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer data: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }
}

