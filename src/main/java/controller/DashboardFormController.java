package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class DashboardFormController {

    @FXML
    private AnchorPane root;

    @FXML
    void btnHomeFormOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);


    }

    @FXML
    void btnUserManagmentFormOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/user_management.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }
    @FXML
    void btnLogoutOnAction(ActionEvent event) {

    }

    @FXML
    void btnBookManagementFormOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }

}
