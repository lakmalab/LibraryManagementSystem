package controller;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.jfoenix.controls.JFXTextArea;
import config.AppModule;
import dto.BookDto;
import dto.UserDto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.custom.BookService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BookFormController implements Initializable {

    @FXML
    private ListView<BookDto> bookListView;

    @FXML
    private JFXTextArea txtSearch;
    private AnchorPane root;
    private Injector injector;
    @Inject
    private BookService bookService;
    @FXML
    void btnAddBookOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("/view/addNewBook.fxml");
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

    }
    private void loadList() {
        try {
            List<BookDto> all = bookService.getAll();
            bookListView.setItems(FXCollections.observableArrayList(all));
            bookListView.setCellFactory(param -> new ListCell<BookDto>() {
                @Override
                protected void updateItem(BookDto item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hBox = new HBox(10);
                        hBox.setAlignment(Pos.CENTER_LEFT);

                        VBox layout = new VBox();
                        String coverUrl = item.getCover();
                        Image coverImage;

                        try {
                            if (coverUrl != null && !coverUrl.isEmpty()) {
                                coverImage = new Image(coverUrl, true);
                            } else {
                                coverImage = new Image("images/cover.png");
                            }
                        } catch (Exception e) {
                            coverImage = new Image("images/cover.png");
                        }

                        ImageView imageView = new ImageView(coverImage);
                        imageView.setFitWidth(100);
                        imageView.setFitHeight(150);
                        imageView.setPreserveRatio(true);


                        VBox labelBox = new VBox(5);
                        labelBox.setAlignment(Pos.TOP_LEFT);

                        Label titleLabel = new Label(item.getTitle());
                        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: navyblue;" );
                        Label authorLabel = new Label("By " + item.getAuthor());
                        authorLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;" );
                        Label genre = new Label("Genre " +item.getGenre().toString());
                        genre.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;" );
                        Label availibility;
                        if (item.getAvailable() == false) {
                            availibility = new Label("Availability :" +"Not Available" );
                            availibility.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: red;");
                        } else {
                            availibility = new Label("Availability :" +"Available" );
                            availibility.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: green;");
                        }


                        Label description = new Label(item.getDescription());


                        labelBox.getChildren().addAll(titleLabel, authorLabel,genre,availibility,description);

                        hBox.getChildren().addAll(imageView, labelBox);

                        setText(null);
                        setGraphic(hBox);
                    }
                }
            });

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer data: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        injector = Guice.createInjector(new AppModule());
        loadList();
    }
}
