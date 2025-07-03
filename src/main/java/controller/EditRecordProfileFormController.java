package controller;

import com.google.inject.Inject;
import com.jfoenix.controls.JFXCheckBox;
import dto.BookDto;
import dto.BorrowRecordDto;
import dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import service.custom.BookService;
import service.custom.BorrowRecordService;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditRecordProfileFormController implements Initializable {

    @FXML
    private JFXCheckBox chbReturened;

    @FXML
    private DatePicker dateBorrowed;

    @FXML
    private DatePicker dateReturened;

    @FXML
    private ImageView imgCover;

    @FXML
    private TextField txtFine;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtUser;

    private BorrowRecordDto newSelection;

    @Inject
    private BorrowRecordService borrowRecordService;
    @Inject
    BookService bookService;
    LocalDate returenddate;
    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
       BookDto bookDto;
        String username = txtUser.getText();
        String bookname = txtTitle.getText();


        if (username == null || bookname == null || username.isEmpty() || bookname.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select all fields.").show();
            return;
        }

        try {


            bookDto = bookService.searchById(bookname);


            BorrowRecordDto newRecord = new BorrowRecordDto(newSelection.getRecordId(), newSelection.getUser(), newSelection.getBook(), newSelection.getBorrowDate(),
                    returenddate, 1000.00);

            boolean success = borrowRecordService.update(newRecord);

            if (success) {
                if (chbReturened.isSelected()) {

                    bookService.updateBookAvailability(bookDto.getBookID(), true);
                }
                new Alert(Alert.AlertType.INFORMATION, "Borrow record added successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add borrow record.").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }

    public void setRecord(BorrowRecordDto newSelection) {
        this.newSelection = newSelection;
        txtUser.setText(String.valueOf(newSelection.getUser()));
        txtTitle.setText(String.valueOf(newSelection.getBook()));
        txtFine.setText(String.valueOf(newSelection.getFine()));
        dateBorrowed.setValue(newSelection.getBorrowDate());
        if (newSelection.getReturnDate() != null) {
            dateReturened.setValue(newSelection.getReturnDate());
            chbReturened.setSelected(true);
        } else {
            dateReturened.setValue(null);
            chbReturened.setSelected(false);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chbReturened.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                dateReturened.setValue(LocalDate.now());
                dateReturened.setDisable(false);
                returenddate = dateReturened.getValue();
            } else {
                dateReturened.setValue(null);
                dateReturened.setDisable(true);
            }
        });

        dateReturened.setDisable(true);
    }
}
