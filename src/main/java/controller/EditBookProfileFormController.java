package controller;

import com.google.inject.Inject;
import com.jfoenix.controls.JFXComboBox;
import dto.BookDto;
import dto.UserDto;
import enums.Genre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import service.custom.BookService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditBookProfileFormController implements Initializable {

    @FXML
    private JFXComboBox<Genre> cmbGenre;

    @FXML
    private ImageView imgCover;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtIsbn;

    @FXML
    private TextField txtTitle;
    private BookDto newValue;

    @Inject
    private BookService bookService;
    @FXML
    void btnCancelOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
       try {
            Boolean b = bookService.deleteById(newValue.getBookID());

            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Book Deleted Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update Book.").show();
            }
        } finally {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws IOException {
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

        BookDto newBook = new BookDto(newValue.getBookID(),isbn, title,author, genre, null,description,true);

        try {
            Boolean b = bookService.updateUser(newBook);

            if (b) {
                new Alert(Alert.AlertType.INFORMATION, "Book Updated Successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to Update Book.").show();
            }
        } finally {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            refreshMainStage();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmbGenre.getItems().addAll(Genre.values());


    }

    public void setUser(BookDto newValue) {
        this.newValue = newValue;
        txtTitle.setText(newValue.getTitle());
        txtAuthor.setText(newValue.getAuthor());
        txtIsbn.setText(String.valueOf(newValue.getIsbn()));
        txtDescription.setText(newValue.getDescription());
        cmbGenre.setValue(newValue.getGenre());
    }
    private void refreshMainStage() throws IOException {
        DashboardFormController Controller = DashboardFormController.getInstance();
        Controller.btnBookManagementFormOnAction(new ActionEvent());
    }
}
