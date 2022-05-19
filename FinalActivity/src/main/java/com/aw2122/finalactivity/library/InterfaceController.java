package com.aw2122.finalactivity.library;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Slider;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class InterfaceController {

    DatabaseController db = new DatabaseController();

    Alert alertDialog = new Alert(Alert.AlertType.NONE);

    //String state;
    InterfaceStatus state;

    // enum estados if state ==

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
    private Slider copiesSlider;

    @FXML
    private DatePicker dpBirthdate;

    @FXML
    private ImageView btnCheck;

    @FXML
    private ImageView userButton;

    @FXML
    private ImageView btnCancel;

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
    private TextField txtCover;

    @FXML
    private TextField txtIsbn;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtOutline;

    @FXML
    private TextField txtPublisher;

    @FXML
    private TextField txtSurname;

    @FXML
    private TextField txtTitle;

    @FXML
    private GridPane userMenu;

    @FXML
    void onUserButtonClick(MouseEvent event) {
        setMainGridVisibility(InterfaceStatus.USER_IDLE);
    }

    @FXML
    void onBookButtonClick(MouseEvent event) {
        setMainGridVisibility(InterfaceStatus.BOOK_IDLE);
    }

    @FXML
    void onBorrowButtonClick(MouseEvent event) {
        setMainGridVisibility(InterfaceStatus.BORROW_IDLE);
    }

    @FXML
    void onReturnButtonClick(MouseEvent event) {
        setMainGridVisibility(InterfaceStatus.RETURN_IDLE);
    }

    @FXML
    void onCheckButtonClicked(MouseEvent event) throws Exception {
        if (state == InterfaceStatus.USER_ADD) {
            UsersEntity user = new UsersEntity();
            user.setCode(txtCode.getText());
            user.setName(txtName.getText());
            user.setSurname(txtSurname.getText());
            if (dpBirthdate.getValue() != null) {
                user.setBirthdate(Date.valueOf(dpBirthdate.getValue()));
            }
            successfulTransactionAlert(db.Insert(user), state);
            state = InterfaceStatus.USER_IDLE;
        }
        if (state == InterfaceStatus.USER_EDIT) {
            UsersEntity user;
            if (!txtCode.getText().isEmpty()) {
                user = (UsersEntity) db.GetObject(txtCode.getText(), "code", state.toString()).get(0);
                user.setName(txtName.getText());
                user.setSurname(txtSurname.getText());
                if (dpBirthdate.getValue() != null) {
                    user.setBirthdate(Date.valueOf(dpBirthdate.getValue()));
                }
                db.Update(user);
                successfulTransactionAlert(true, state);
            }
            clearFields();
            state = InterfaceStatus.USER_IDLE;
        }
        if (state == InterfaceStatus.USER_SEARCH) {
            if (!txtCode.getText().isEmpty()) {
                UsersEntity user;
                if (db.GetObject(txtCode.getText(), "code", state.toString()).size() < 1) {
                    alertDialog.setAlertType(Alert.AlertType.WARNING);
                    alertDialog.setContentText("User not found.");
                    alertDialog.show();
                }
                else {
                    user = (UsersEntity) db.GetObject(txtCode.getText(), "code", state.toString()).get(0);
                    txtName.setText(String.valueOf(user.getName()));
                    txtSurname.setText(user.getSurname());
                    if (user.getBirthdate() != null) {
                        LocalDate date = user.getBirthdate().toLocalDate();
                        dpBirthdate.setValue(date);
                    }
                    //successfulTransactionAlert(true, state);
                }
            } else {
                alertDialog.setAlertType(Alert.AlertType.WARNING);
                alertDialog.setContentText("Code field cannot be empty.");
                alertDialog.show();
            }
            state = InterfaceStatus.USER_IDLE;
        }
        if (state == InterfaceStatus.BOOK_ADD) {
            BooksEntity book = new BooksEntity();
            if (!txtIsbn.getText().isEmpty()) {
                book.setIsbn(txtIsbn.getText());
                book.setTitle(txtTitle.getText());
                book.setCopies((int) copiesSlider.getValue());
                book.setCover(txtCover.getText());
                book.setOutline(txtOutline.getText());
                book.setPublisher(txtPublisher.getText());
                successfulTransactionAlert(db.Insert(book), state);
            } else {
                alertDialog.setAlertType(Alert.AlertType.WARNING);
                alertDialog.setContentText("ISBN field cannot be empty.");
                alertDialog.show();
            }
            state = InterfaceStatus.BOOK_IDLE;
        }
        if (state == InterfaceStatus.BOOK_EDIT) {
            BooksEntity book;
            if (!txtIsbn.getText().isEmpty()) {
                book = (BooksEntity) db.GetObject(txtIsbn.getText(), "isbn", state.toString()).get(0);
                book.setTitle(txtTitle.getText());
                book.setCopies((int) copiesSlider.getValue());
                book.setPublisher(txtPublisher.getText());
                book.setOutline(txtOutline.getText());
                book.setCover(txtCover.getText());
                db.Update(book);
                successfulTransactionAlert(true, state);
            }
            clearFields();
            state = InterfaceStatus.BOOK_IDLE;
        }
        if (state == InterfaceStatus.BOOK_SEARCH) {
            if (!txtIsbn.getText().isEmpty()) {
                BooksEntity book;
                if (db.GetObject(txtIsbn.getText(), "isbn", state.toString()).size() < 1) {
                    alertDialog.setAlertType(Alert.AlertType.WARNING);
                    alertDialog.setContentText("Book not found.");
                    alertDialog.show();
                }
                else {
                    book = (BooksEntity) db.GetObject(txtIsbn.getText(), "isbn", state.toString()).get(0);
                    txtIsbn.setText(String.valueOf(book.getIsbn()));
                    txtTitle.setText(book.getTitle());
                    copiesSlider.setValue(book.getCopies());
                    txtPublisher.setText(book.getPublisher());
                    txtOutline.setText(book.getOutline());
                    txtCover.setText(book.getCover());
                    successfulTransactionAlert(true, state);
                }
            } else {
                alertDialog.setAlertType(Alert.AlertType.WARNING);
                alertDialog.setContentText("ISBN field cannot be empty.");
                alertDialog.show();
            }
            state = InterfaceStatus.BOOK_IDLE;
        }
        //clearFields();
        disableFields(true, state);
        setMainGridVisibility(state);
    }

    private void successfulTransactionAlert(boolean success, InterfaceStatus state) {
        if (success) {
            alertDialog.setAlertType(Alert.AlertType.INFORMATION);
            switch (state) {
                case USER_ADD -> alertDialog.setContentText("User successfully added.");
                case USER_EDIT -> alertDialog.setContentText("User successfully edited.");
                case BOOK_ADD -> alertDialog.setContentText("Book successfully added.");
                case BOOK_EDIT -> alertDialog.setContentText("Book successfully edited.");
                case USER_SEARCH -> alertDialog.setContentText("User found.");
            }
        } else {
            alertDialog.setAlertType(Alert.AlertType.WARNING);
            switch (state) {
                case USER_ADD -> alertDialog.setContentText("User could not be added.");
                case USER_EDIT -> alertDialog.setContentText("User could not be edited.");
                case BOOK_ADD -> alertDialog.setContentText("Book could not be added.");
                case BOOK_EDIT -> alertDialog.setContentText("Book could not be edited.");
                case USER_SEARCH -> alertDialog.setContentText("User could not be found.");
                case BOOK_SEARCH -> alertDialog.setContentText("Book could not be found.");
            }
        }
        alertDialog.show();
    }

    @FXML
    void onCancelButtonClicked(MouseEvent event) {
        switch (state) {
            case USER_ADD, USER_EDIT, USER_SEARCH -> state = InterfaceStatus.USER_IDLE;
            case BOOK_ADD, BOOK_EDIT, BOOK_SEARCH -> state = InterfaceStatus.BOOK_IDLE;
        }
        setMainGridVisibility(state);
        disableFields(true, state);
        clearFields();
    }

    @FXML
    void onAddButtonClicked(MouseEvent event) {
        clearFields();
        if (userMenu.isVisible())
            state = InterfaceStatus.USER_ADD;
        if (bookMenu.isVisible())
            state = InterfaceStatus.BOOK_ADD;
        setMainGridVisibility(state);
        disableFields(false, state);
    }

    @FXML
    void onEditButtonClicked(MouseEvent event) {
        if (userMenu.isVisible() && !txtCode.getText().isEmpty())
            state = InterfaceStatus.USER_EDIT;
        if (bookMenu.isVisible())
            state = InterfaceStatus.BOOK_EDIT;
        setMainGridVisibility(state);
        disableFields(false, state);
    }

    @FXML
    void onSearchButtonClick(MouseEvent event) {
        clearFields();
        if (userMenu.isVisible())
            state = InterfaceStatus.USER_SEARCH;
        if (bookMenu.isVisible())
            state = InterfaceStatus.BOOK_SEARCH;
        setMainGridVisibility(state);
        disableFields(true, state);
    }

    @FXML
    void onExitButtonClick(MouseEvent event) {
        Platform.exit();
    }

    void setMainGridVisibility(InterfaceStatus state) {
        hideGrids();
        switch (state) {
            case USER_IDLE -> {
                userMenu.setVisible(true);
                bottomPanelMenu.setVisible(true);
            }
            case BOOK_IDLE -> {
                bookMenu.setVisible(true);
                bottomPanelMenu.setVisible(true);
            }
            case USER_ADD, USER_EDIT, USER_SEARCH -> {
                userMenu.setVisible(true);
                bottomSaveCancel.setVisible(true);
            }
            case BOOK_ADD, BOOK_EDIT, BOOK_SEARCH -> {
                bookMenu.setVisible(true);
                bottomSaveCancel.setVisible(true);
            }
            case BORROW_IDLE -> {
                borrowMenu.setVisible(true);
                bottomPanelAdd.setVisible(true);
            }
            case RETURN_IDLE -> {
                returnMenu.setVisible(true);
                bottomPanelAdd.setVisible(true);
            }
        }
    }

    void hideGrids() {
         userMenu.setVisible(false);
         bookMenu.setVisible(false);
         borrowMenu.setVisible(false);
         returnMenu.setVisible(false);
         bottomPanelMenu.setVisible(false);
         bottomSaveCancel.setVisible(false);
         bottomPanelAdd.setVisible(false);
    }

    void disableFields(Boolean disable, InterfaceStatus state) {
        switch (state) {
            case USER_ADD, USER_IDLE -> {
                txtCode.setDisable(disable);
                txtName.setDisable(disable);
                txtSurname.setDisable(disable);
                dpBirthdate.setDisable(disable);
            }
            case USER_SEARCH -> {
                txtCode.setDisable(false);
                txtName.setDisable(disable);
                txtSurname.setDisable(disable);
                dpBirthdate.setDisable(disable);
            }
            case  USER_EDIT -> {
                txtCode.setDisable(true);
                txtName.setDisable(disable);
                txtSurname.setDisable(disable);
                dpBirthdate.setDisable(disable);
            }
            case BOOK_ADD, BOOK_IDLE -> {
                txtIsbn.setDisable(disable);
                txtTitle.setDisable(disable);
                txtPublisher.setDisable(disable);
                txtCover.setDisable(disable);
                txtOutline.setDisable(disable);
                copiesSlider.setDisable(disable);
            }
            case BOOK_EDIT -> {
                txtIsbn.setDisable(true);
                txtTitle.setDisable(disable);
                txtPublisher.setDisable(disable);
                txtCover.setDisable(disable);
                txtOutline.setDisable(disable);
                copiesSlider.setDisable(disable);
            }
            case BOOK_SEARCH -> {
                txtIsbn.setDisable(false);
                txtTitle.setDisable(disable);
                txtPublisher.setDisable(disable);
                txtCover.setDisable(disable);
                txtOutline.setDisable(disable);
                copiesSlider.setDisable(disable);
            }
        }
    }

    void clearFields() {
        txtCode.setText("");
        txtName.setText("");
        txtSurname.setText("");
        txtIsbn.setText("");
        txtTitle.setText("");
        txtPublisher.setText("");
        txtCover.setText("");
        txtOutline.setText("");
        copiesSlider.adjustValue(0);
        dpBirthdate.getEditor().clear();
    }
}