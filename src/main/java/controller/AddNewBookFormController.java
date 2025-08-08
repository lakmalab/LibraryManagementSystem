package controller;

import com.jfoenix.controls.JFXComboBox;
import dto.BookDto;
import dto.UserDto;
import enums.Genre;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import service.custom.BookService;
import service.custom.UserService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Base64;
import java.util.ResourceBundle;

public class AddNewBookFormController implements Initializable{

    public TextArea txtDescription;
    @FXML
    private JFXComboBox<Genre> cmbGenre;

    @FXML
    private ImageView imgCover;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtIsbn;

    @FXML
    private TextField txtTitle;
    @Inject
    private BookService bookService;
    @FXML
    void btnAddOnAction(ActionEvent event) throws IOException {
         Long  isbn = Long.valueOf(txtIsbn.getText());
         String title=txtTitle.getText();
         String author=txtAuthor.getText();
         Genre genre = cmbGenre.getValue();
     //    String cover = convertImageToBase64(null);
         String description=txtDescription.getText();

        if ( title.isEmpty() || author.isEmpty() || description.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill in all fields.").show();
            return;
        }
        try {
            BookDto bookDto = bookService.searchById(title);
            if (bookDto!=null) {
                new Alert(Alert.AlertType.ERROR, "Book with Title " + title + " already exists.").show();
                return;
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error checking for existing Book: " + e.getMessage()).show();
            e.printStackTrace();
            return;
        }
        BookDto newbook = new BookDto(null,isbn, title,author, genre, null,description,true);

        try {
            Boolean b = bookService.addBook(newbook);

            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Book Added Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add Book.").show();
            }
        } finally {
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
    public static String convertImageToBase64(String imagePath) throws IOException {
        File file = new File(imagePath);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbGenre.getItems().addAll(Genre.values());
        cmbGenre.setValue(Genre.EDUCATIONAL);
    }
    private void refreshMainStage() throws IOException {
        DashboardFormController Controller = DashboardFormController.getInstance();
        Controller.btnBookManagementFormOnAction(new ActionEvent());
    }
}
