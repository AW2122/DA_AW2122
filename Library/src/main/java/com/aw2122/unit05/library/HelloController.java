package com.aw2122.unit05.library;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class HelloController {

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
    private ImageView userButton;

    @FXML
    private GridPane userMenu;

    @FXML
    void onBookButtonClick(MouseEvent event) {
        setGridVisibility();
        bookMenu.setVisible(true);
    }

    @FXML
    void onBorrowButtonClick(MouseEvent event) {
        setGridVisibility();
        borrowMenu.setVisible(true);
    }
    
    @FXML
    void onReturnButtonClick(MouseEvent event) {
        setGridVisibility();
        returnMenu.setVisible(true);
    }

    @FXML
    void onUserButtonClick(MouseEvent event) {
        setGridVisibility();
        userMenu.setVisible(true);
    }

    @FXML
    void onAddButtonClicked(KeyEvent event) {

    }

    @FXML
    void onEditButtonClicked(KeyEvent event) {

    }

    @FXML
    void onExitButtonClick(MouseEvent event) {

    }



    @FXML
    void onSearchButtonClick(KeyEvent event) {

    }



    void setGridVisibility() {
        // pasarle un numero para que active la pesataña segun el numero
        userMenu.setVisible(false);
        bookMenu.setVisible(false);
        borrowMenu.setVisible(false);
        returnMenu.setVisible(false);
    }
}
