package controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import config.AppModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private AnchorPane root;
    private Injector injector;
    @FXML
    void btnHomeFormOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/.fxml");

        assert resource != null;
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(injector::getInstance);
        Parent load = loader.load();
        this.root.getChildren().clear();
        this.root.getChildren().add(load);


    }

    @FXML
    void btnUserManagmentFormOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/user_management.fxml");

        assert resource != null;
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(injector::getInstance);
        Parent load = loader.load();
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }
    @FXML
    void btnLogoutOnAction(ActionEvent event) {


    }

    @FXML
    void btnBookManagementFormOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/book_management.fxml");

        assert resource != null;
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(injector::getInstance);
        Parent load = loader.load();
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        injector = Guice.createInjector(new AppModule());
    }
}
