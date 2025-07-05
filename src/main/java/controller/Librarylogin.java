package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Librarylogin {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUser;

    final String username = "lakmal";
    final String password = "1234";

    @FXML
    void btnLogInOnAction(ActionEvent event) throws IOException {
        if (txtUser.getText().equals(username) && txtPassword.getText().equals(password)) {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"))));
            stage.show();
            new Alert(Alert.AlertType.INFORMATION,"Hello "+username+" \n Welcome Back").show();
            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.close();
        }else {
            new Alert(Alert.AlertType.INFORMATION,"Error There's no User With the name "+txtUser.getText()+" \n Please Try again").show();
        }

    }


}
