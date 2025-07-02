package controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jfoenix.controls.JFXTextArea;
import config.AppModule;
import dto.UserDto;
import jakarta.inject.Inject;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
    @Inject
    private UserService userService;
    private Injector injector;
    @FXML
    void btnAddUserOnAction(ActionEvent event) throws IOException {

        URL resource = getClass().getResource("/view/addNewUser.fxml");
        assert resource != null;

        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }
    @FXML
    void btnSearchOnAction(ActionEvent event) {
        loadTable(txtSearch.getText().trim());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        injector = Guice.createInjector(new AppModule());

        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        ColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colNationalIdNo.setCellValueFactory(new PropertyValueFactory<>("idNumber"));
        loadTable();
        tblUser.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    openProfile(newSelection,injector);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    private void openProfile(UserDto user, Injector injector) throws IOException {
        URL resource = getClass().getResource("/view/editProfile.fxml");
        assert resource != null;

        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load();

        EditProfileFormController controller = loader.getController();
        controller.setUser(user);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
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
    private void loadTable(String idNumber) {
        try {
            UserDto user = userService.searchById(idNumber);
            if (user != null) {
                tblUser.setItems(FXCollections.observableArrayList(user));
            } else {
                loadTable();
                new Alert(Alert.AlertType.INFORMATION, "No user found with that ID number.").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer data: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }
}

