package controller;

import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.custom.AdminService;
import service.custom.UserService;

import java.io.IOException;
import java.sql.SQLException;

public class Librarylogin {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    @Inject
    private AdminService adminService;

    @FXML
    void btnLogInOnAction(ActionEvent event) throws IOException, SQLException {

        if (txtUser.getText().isEmpty() || txtPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.INFORMATION, "Please Fill All the Fields").show();
            return;
        }
        if (txtPassword.getText().equals(adminService.searchByName(txtUser.getText()).getPassword())){
            new Alert(Alert.AlertType.INFORMATION, "Welcome Back,"+txtUser.getText()).show();
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
            stage.show();
            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.close();

        }else {
            new Alert(Alert.AlertType.INFORMATION,"Error There's no User With the name "+txtUser.getText()+" \n Please Try again").show();
        }

    }


}
