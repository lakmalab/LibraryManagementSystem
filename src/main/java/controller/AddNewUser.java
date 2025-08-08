package controller;

import dto.UserDto;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.custom.UserService;
import util.ServiceType;

import java.io.IOException;
import java.sql.SQLException;

public class AddNewUser {

    public TextField txtIdNumber;
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @Inject
    private UserService userService;

    @FXML
    void btnAddOnAction(ActionEvent event) throws IOException {
        String name = txtName.getText();
        String address = txtAddress.getText();
        String idnumber = txtIdNumber.getText();
        String ContactStr = txtContact.getText();

        if ( name.isEmpty() || address.isEmpty() || ContactStr.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all fields.").show();
            return;
        }
        try {
            UserDto userDto = userService.searchById(idnumber);
            if (userDto!=null) {
                new Alert(Alert.AlertType.ERROR, "User with ID " + idnumber + " already exists.").show();
                return;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error checking for existing user: " + e.getMessage()).show();
            e.printStackTrace();
            return;
        }
        UserDto newUser = new UserDto(null, name,idnumber, address, ContactStr);

        try {
            Boolean b = userService.addUser(newUser);

            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "User Added Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add User.").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace(); // Log the error
        }finally {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            refreshMainStage();
        }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    private void refreshMainStage() throws IOException {
        DashboardFormController Controller = DashboardFormController.getInstance();
        Controller.btnUserManagmentFormOnAction(new ActionEvent());
    }
}
