package controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;

public class UserManagementFormController {

    @FXML
    private TreeTableColumn ColName;

    @FXML
    private TreeTableColumn colAddress;

    @FXML
    private TreeTableColumn colContact;

    @FXML
    private TreeTableColumn colUserId;

    @FXML
    private JFXTreeTableView tblUser;

    @FXML
    private JFXTextArea txtSearch;

    @FXML
    void btnAddUserOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {

    }

}

