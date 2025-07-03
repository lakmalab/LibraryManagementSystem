package controller;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.jfoenix.controls.JFXTextArea;
import dto.BookDto;
import dto.BorrowRecordDto;
import dto.UserDto;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.custom.BookService;
import service.custom.BorrowRecordService;
import service.custom.UserService;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class BookLendFormController implements Initializable {

    @FXML
    private TableColumn ColBook;

    @FXML
    private ComboBox<String> cmbBookCode;

    @FXML
    private ComboBox<String> cmbUserName;

    @FXML
    private TableColumn colBorrowDate;

    @FXML
    private TableColumn colFine;

    @FXML
    private TableColumn colRecordId;

    @FXML
    private TableColumn colReturnDate;

    @FXML
    private TableColumn colUser;

    @FXML
    private Label lblBookAvailability;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTIme;

    @FXML
    private Label lblUserEligibility;

    @FXML
    private TableView<BorrowRecordDto> tblRecord;

    @FXML
    private JFXTextArea txtSearch;
    @Inject
    private UserService userService;
    @Inject
    private BookService bookService;
    @Inject
    private BorrowRecordService borrowRecordService ;

    private Injector injector;

    @FXML
    public void btnAddRecordOnAction(ActionEvent event) throws SQLException {
        String username = cmbUserName.getValue();
        String bookname = cmbBookCode.getValue();
        BookDto bookDto;
        if (username == null || bookname == null || username.isEmpty() || bookname.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please select all fields.").show();
            return;
        }

        try {
            UserDto userDto = userService.searchById(username);
             bookDto = bookService.searchById(bookname);

            if (userDto == null) {
                new Alert(Alert.AlertType.ERROR, "User with ID " + username + " not found.").show();
                return;
            }

            if (bookDto == null) {
                new Alert(Alert.AlertType.ERROR, "Book with Title " + bookname + " not found.").show();
                return;
            }

            BorrowRecordDto newRecord = new BorrowRecordDto(null, userDto, bookDto, LocalDate.now(),
                    null, 1000.00);

            boolean success = borrowRecordService.add(newRecord);

            if (success) {
                bookService.updateBookAvailability(bookDto.getBookID(), false);
                new Alert(Alert.AlertType.INFORMATION, "Borrow record added successfully!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to add borrow record.").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            e.printStackTrace();
        }
    }


    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colRecordId.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("user"));
        ColBook.setCellValueFactory(new PropertyValueFactory<>("book"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colFine.setCellValueFactory(new PropertyValueFactory<>("fine"));


        loadDateAndTime();
        loadUserNames();
        loadBooks();
        loadTable();
    }

    private void loadBooks() {
        try {
            List<String> books = bookService.getBookNames();
            cmbBookCode.setItems(FXCollections.observableArrayList(books));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadUserNames() {
        try {
            List<String> userNames = userService.getUserNames();
            cmbUserName.setItems(FXCollections.observableArrayList(userNames));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadTable() {
        List<BorrowRecordDto> all = borrowRecordService.getAll();
        tblRecord.setItems(FXCollections.observableArrayList(all));
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = format.format(date);
        lblDate.setText(format1);


        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    LocalTime now = LocalTime.now();
                    lblTIme.setText(now.getHour() + " : " + now.getMinute() + " : " + now.getSecond());
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }
}
