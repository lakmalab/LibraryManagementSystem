package controller;

import com.google.inject.Injector;
import com.jfoenix.controls.JFXTextArea;
import jakarta.inject.Inject;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;
import service.custom.BookService;
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
    private TableColumn<?, ?> ColBook;

    @FXML
    private ComboBox<String> cmbBookCode;

    @FXML
    private ComboBox<String> cmbUserName;

    @FXML
    private TableColumn<?, ?> colBorrowDate;

    @FXML
    private TableColumn<?, ?> colFine;

    @FXML
    private TableColumn<?, ?> colRecordId;

    @FXML
    private TableColumn<?, ?> colReturnDate;

    @FXML
    private TableColumn<?, ?> colUser;

    @FXML
    private Label lblBookAvailability;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTIme;

    @FXML
    private Label lblUserEligibility;

    @FXML
    private TableView<?> tblRecord;

    @FXML
    private JFXTextArea txtSearch;
    @Inject
    private UserService userService;
    @Inject
    private BookService bookService;

    private Injector injector;

    @FXML
    void btnAddRecordOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        loadUserNames();
        //loadBooks();
    }

    private void loadUserNames() {
        try {
            List<String> userNames = userService.getUserNames();
            cmbUserName.setItems(FXCollections.observableArrayList(userNames));
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
