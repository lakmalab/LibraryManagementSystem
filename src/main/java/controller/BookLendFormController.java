package controller;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.jfoenix.controls.JFXTextArea;
import config.AppModule;
import dto.BookDto;
import dto.BorrowRecordDto;
import dto.UserDto;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.custom.BookService;
import service.custom.BorrowRecordService;
import service.custom.UserService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
        if (lblBookAvailability.getText() == "Not Available" || lblUserEligibility.getText() == "Not Eligible") {
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
                    null, 0.0,false);

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
        injector = Guice.createInjector(new AppModule());

        colRecordId.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("user"));
        ColBook.setCellValueFactory(new PropertyValueFactory<>("book"));
        colBorrowDate.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colFine.setCellValueFactory(new PropertyValueFactory<>("fine"));

        tblRecord.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                try {
                    openRecord(newSelection,injector);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        loadDateAndTime();
        loadUserNames();
        loadBooks();
        loadTable();

    }

    private void openRecord(BorrowRecordDto recordDto, Injector injector) throws IOException {
        URL resource = getClass().getResource("/view/editRecordProfile.fxml");
        assert resource != null;

        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load();

        EditRecordProfileFormController controller = loader.getController();
        controller.setRecord(recordDto);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void loadBooks() {
        try {
            List<String> books = bookService.getBookNames();
            cmbBookCode.setItems(FXCollections.observableArrayList(books));
            cmbBookCode.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    List<BorrowRecordDto> all = borrowRecordService.getAll();

                    for (BorrowRecordDto dto : all) {
                        if (dto.getBook().getAvailable()) {
                            lblBookAvailability.setText("Not Available");
                            lblBookAvailability.setStyle("-fx-text-fill: red;");
                        }else {
                            lblBookAvailability.setText("Available");
                            lblBookAvailability.setStyle("-fx-text-fill: green;");
                        }
                    }

                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadUserNames() {
        try {
            List<String> userNames = userService.getUserNames();
            cmbUserName.setItems(FXCollections.observableArrayList(userNames));
            cmbUserName.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    List<BorrowRecordDto> all = borrowRecordService.getAll();
                    int count = 0;

                    for (BorrowRecordDto dto : all) {
                        if (dto.getUser().getName().equals(newVal) && dto.getFine() > 0) {
                            count++;
                        }
                    }

                    if (count > 3) {
                        lblUserEligibility.setText("Not Eligible");
                        lblUserEligibility.setStyle("-fx-text-fill: red;");
                    } else {
                        lblUserEligibility.setText("Eligible");
                        lblUserEligibility.setStyle("-fx-text-fill: green;");
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadTable() {
        List<BorrowRecordDto> all = borrowRecordService.getAll();
        calculateFine(all);
        tblRecord.setItems(FXCollections.observableArrayList(all));
    }

    private void calculateFine(List<BorrowRecordDto> all) {
        double finePerDay = 10.0;
        int allowedDays = 14;

        for (BorrowRecordDto dto : all) {
            double fine = 0;
            LocalDate borrowDate = dto.getBorrowDate();
            LocalDate returnDate = dto.getReturnDate();
            LocalDate compareDate = (returnDate != null) ? returnDate : LocalDate.now();

            long days = ChronoUnit.DAYS.between(borrowDate, compareDate);

            if (days > allowedDays && !dto.isPaidFine()) {
                fine = (days - allowedDays) * finePerDay;
                dto.setFine(fine);
            } else {
                dto.setFine(0.0);
            }
            BorrowRecordDto newRecord = new BorrowRecordDto(
                    dto.getRecordId(),
                    dto.getUser(),
                    dto.getBook(),
                    borrowDate,
                    returnDate,
                    fine,
                    false
            );
            borrowRecordService.update(newRecord);
        }
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
