package controller;

import com.google.inject.Guice;
import com.google.inject.Injector;
import config.AppModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private AnchorPane root;
    private Injector injector;
    @Getter
    private static DashboardFormController instance;

    @FXML
    void btnHomeFormOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/home.fxml");

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
    void btnLogoutOnAction(ActionEvent event)  throws IOException{
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/library_login.fxml"))));
            stage.show();

            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.close();
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
        instance = this;
        injector = Guice.createInjector(new AppModule());
        URL resource = this.getClass().getResource("/view/home.fxml");

        assert resource != null;
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(injector::getInstance);
        Parent load = null;
        try {
            load = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.root.getChildren().clear();
        this.root.getChildren().add(load);

    }

    public void btnBookLendOnAction(ActionEvent event) throws IOException {
        URL resource = this.getClass().getResource("/view/book_lend.fxml");

        assert resource != null;
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(injector::getInstance);
        Parent load = loader.load();
        this.root.getChildren().clear();
        this.root.getChildren().add(load);


    }


    public void btnReportOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/report_management.fxml");

        assert resource != null;
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(injector::getInstance);
        Parent load = loader.load();
        this.root.getChildren().clear();
        this.root.getChildren().add(load);
    }
}
