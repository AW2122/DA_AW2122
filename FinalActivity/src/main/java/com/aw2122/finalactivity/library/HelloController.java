package com.aw2122.finalactivity.library;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class HelloController {

    DatabaseController db = new DatabaseController();

    @FXML
    private ImageView addButton;

    @FXML
    private ImageView bookButton;

    @FXML
    private GridPane bookMenu;

    @FXML
    private ImageView borrowButton;

    @FXML
    private GridPane borrowMenu;

    @FXML
    private GridPane bottomPanelAdd;

    @FXML
    private GridPane bottomPanelMenu;

    @FXML
    private GridPane bottomSaveCancel;

    @FXML
    private ImageView editButton;

    @FXML
    private ImageView exitButton;

    @FXML
    private ImageView returnButton;

    @FXML
    private GridPane returnMenu;

    @FXML
    private ImageView searchButton;

    @FXML
    private GridPane topPanel;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSurname;

    @FXML
    private DatePicker dpBirthdate;

    @FXML
    private ImageView userButton;

    @FXML
    private GridPane userMenu;

    @FXML
    void onUserButtonClick(MouseEvent event) {
        setMainGridVisibility(1);

    }

    @FXML
    void onBookButtonClick(MouseEvent event) {
        setMainGridVisibility(2);

    }

    @FXML
    void onBorrowButtonClick(MouseEvent event) {
        setMainGridVisibility(3);

    }

    @FXML
    void onReturnButtonClick(MouseEvent event) {
        setMainGridVisibility(4);

    }

    @FXML
    void onAddButtonClicked(MouseEvent event) {
        /*UsersEntity user = new UsersEntity();
        user.setCode(txtCode.getText());
        user.setName();
        user.setSurname();
        user.setBirthdate();

        db.InsertUsers(user);*/
        UsersEntity user = new UsersEntity();
        user.setCode(txtCode.getText());
        user.setName(txtName.getText());
        user.setSurname(txtSurname.getText());
        //user.setBirthdate(dpBirthdate.getValue().);
        db.InsertUser(user);
    }

    @FXML
    void onEditButtonClicked(KeyEvent event) {

    }

    @FXML
    void onSearchButtonClick(MouseEvent event) {
        //db.GetUser("","");
    }

    @FXML
    void onExitButtonClick(MouseEvent event) {
        Platform.exit();
    }

    void setMainGridVisibility(int menuValue) {
        // pasarle un numero para que active la pesataña segun el numero
        userMenu.setVisible(false);
        bookMenu.setVisible(false);
        borrowMenu.setVisible(false);
        returnMenu.setVisible(false);

        bottomPanelMenu.setVisible(false);
        bottomSaveCancel.setVisible(false);
        bottomPanelAdd.setVisible(false);

        switch (menuValue) {
            case 2 -> bookMenu.setVisible(true);
            case 3 -> borrowMenu.setVisible(true);
            case 4 -> returnMenu.setVisible(true);
            default -> userMenu.setVisible(true);
        }
        switch (menuValue) {
            case 3, 4 -> bottomPanelAdd.setVisible(true);
            default -> bottomPanelMenu.setVisible(true);
        }
    }

    // Incluir booleanos para control de pestañas
}