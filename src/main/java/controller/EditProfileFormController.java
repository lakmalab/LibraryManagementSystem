package controller;

import com.google.inject.Inject;
import dto.UserDto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.custom.UserService;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditProfileFormController {

    public Label lbluserID;
    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtIdNumber;

    @FXML
    private TextField txtName;
    private UserDto user;

    @Inject
    private UserService userService;

    public void setUser(UserDto user) {
        this.user = user;
        txtName.setText(user.getName());
        txtAddress.setText(user.getAddress());
        txtContact.setText(user.getContact());
        txtIdNumber.setText(user.getIdNumber());
        lbluserID.setText(String.valueOf(user.getUserId()));
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String name = txtName.getText();
        String address = txtAddress.getText();
        String idnumber = txtIdNumber.getText();
        String ContactStr = txtContact.getText();

        if ( name.isEmpty() || address.isEmpty() || ContactStr.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all fields.").show();
            return;
        }

        UserDto newUser = new UserDto(user.getUserId(), name,idnumber, address, ContactStr);

        try {
            Boolean b = userService.updateUser(newUser);

            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "User Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update User.").show();
            }
        } finally {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }



    public void btnDeleteOnAction(ActionEvent actionEvent) {
        UserDto newUser = new UserDto(user.getUserId(), null,null, null, null);

        try {
            Boolean b = userService.updateUser(newUser);

            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "User Deleted Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update User.").show();
            }
        } finally {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }

    }
}
