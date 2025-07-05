package controller;

import dto.BookDto;
import dto.UserDto;
import jakarta.inject.Inject;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import service.custom.BookService;
import service.custom.UserService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class HomeFormController implements Initializable {

    public Label lblTotalBookCount;
    public Label lblUserCount;
    public Label lblAvailableBookCCount;
    public Label lblBorrowedBookCount;
    @FXML
    private AnchorPane root;
    @Inject
    private UserService userService;
    @Inject
    private BookService bookService;

    @FXML
    void btnViewProfileOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserCount();
        loadBookCount();
    }

    private void loadBookCount() {
        int totalBookCount = 0;
        int availableBookCount = 0;
        int borrowedBookCount = 0;
        try {
            List<BookDto> all = bookService.getAll();
            for (BookDto book : all) {
                totalBookCount+=1;
                if (Boolean.TRUE.equals(book.getAvailable())) {
                    availableBookCount++;
                } else {
                    borrowedBookCount++;
                }
            }
            lblTotalBookCount.setText(String.valueOf(totalBookCount));
            lblAvailableBookCCount.setText(String.valueOf(availableBookCount));
            lblBorrowedBookCount.setText(String.valueOf(borrowedBookCount));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load Book data: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    private void loadUserCount() {
        int userCount = 0;
        try {
            List<UserDto> all = userService.getAll();
            for (UserDto user : all) {
                userCount+=1;
            }
            lblUserCount.setText(String.valueOf(userCount));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer data: " + e.getMessage()).show();
            e.printStackTrace();
        }

    }
}
