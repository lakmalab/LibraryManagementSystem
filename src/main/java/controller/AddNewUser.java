package controller;

import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import service.ServiceFactory;
import service.custom.UserService;
import util.ServiceType;

import java.sql.SQLException;

public class AddNewUser {

    public TextField txtIdNumber;
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;
    UserService userService = ServiceFactory.getInstance().getServiceType(ServiceType.USER);

    @FXML
    void btnAddOnAction(ActionEvent event) {
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
                new Alert(Alert.AlertType.ERROR, "Customer with ID " + idnumber + " already exists.").show();
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
                new Alert(Alert.AlertType.INFORMATION, "Customer Added Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add customer.").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
            e.printStackTrace(); // Log the error
        }
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

}
