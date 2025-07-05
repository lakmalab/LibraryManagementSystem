package controller;

import com.jfoenix.controls.JFXButton;
import dto.BookDto;
import dto.UserDto;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;
import service.custom.BookService;
import service.custom.UserService;
import util.HibernateUtil;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ReportFormController implements Initializable {
    @Inject
    private UserService userService;
    @Inject
    private BookService bookService;
    @FXML
    private JFXButton BtnOverdueOnAction;

    @FXML
    private JFXButton BtnTotalUsersOnAction;

    @FXML
    private JFXButton btnTotalBooksReportOnAction;

    @FXML
    private Label lblOverdueBookCount;

    @FXML
    private Label lblTotalBookCount;
    Connection connection;
    @FXML
    private Label lblTotalUsersCount;
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
            lblOverdueBookCount.setText(String.valueOf(borrowedBookCount));
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
            lblTotalUsersCount.setText(String.valueOf(userCount));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer data: " + e.getMessage()).show();
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUserCount();
        loadBookCount();
    }

    public void BtnTotalUsersOnAction(ActionEvent actionEvent) {

        try {
            Session session = HibernateUtil.getSession();
             connection = session.doReturningWork(conn -> conn);
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/allUsers.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "allUsers.pdf");
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }


    public void btnTotalBooksReportOnAction(ActionEvent actionEvent) {
        try {
            Session session = HibernateUtil.getSession();
            connection = session.doReturningWork(conn -> conn);
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/allBooks.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "allBooks.pdf");
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void BtnOverdueOnAction(ActionEvent actionEvent) {
        try {
            Session session = HibernateUtil.getSession();
            connection = session.doReturningWork(conn -> conn);
            JasperDesign design = JRXmlLoader.load("src/main/resources/report/overdueBooks.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
            JasperExportManager.exportReportToPdfFile(jasperPrint, "overdueBooks.pdf");
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
