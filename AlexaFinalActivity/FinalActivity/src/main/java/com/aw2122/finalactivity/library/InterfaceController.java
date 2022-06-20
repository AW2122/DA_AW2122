package com.aw2122.finalactivity.library;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;
import java.sql.Date;
import java.time.LocalDate;

public class InterfaceController {
    protected SessionFactory sessionFactory;

    DatabaseController db = new DatabaseController();

    InterfaceStatus state;

    UsersEntity searchedUser;
    BooksEntity searchedBook;

    @FXML
    private ImageView addButton;

    @FXML
    private ImageView bookButton;

    @FXML
    private GridPane bookMenu;

    @FXML
    private ImageView borrowButton;

    @FXML
    private GridPane ReturnMenu;

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
    private ImageView btnUserSearch;

    @FXML
    private ImageView btnBookSearch;

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
    private ImageView btnAddBorrowReturn;

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
    private TextField txtUserReturn;

    @FXML
    private TextField txtUserReturnCode;

    @FXML
    private TextField txtBookReturnCode;

    @FXML
    private TextField txtBookReturnTitle;

    @FXML
    private Label lblMenuTitle;

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
        setMainGridVisibility(state = InterfaceStatus.BORROW_IDLE);
        lblMenuTitle.setText("Borrow Menu");
    }

    @FXML
    void onReturnButtonClick(MouseEvent event) {
        setMainGridVisibility(state = InterfaceStatus.RETURN_IDLE);
        lblMenuTitle.setText("Return Menu");
    }

    @FXML
    void onUserSearchButtonClick(MouseEvent event) throws Exception {
        if (!txtUserReturnCode.getText().isEmpty()) {
            UsersEntity user;
            if (db.GetObject(txtUserReturnCode.getText(), "code").size() < 1) {
                setAlertDialog("User not found", "", Alert.AlertType.WARNING);
            } else {
                user = (UsersEntity) db.GetObject(txtUserReturnCode.getText(), "code").get(0);
                txtUserReturn.setText(user.getName() + " " + user.getSurname());
                searchedUser = user;
                txtUserReturnCode.setDisable(true);
            }
        }
    }

    @FXML
    void onBookSearchButtonClick(MouseEvent event) throws Exception {
        if (!txtBookReturnCode.getText().isEmpty()) {
            BooksEntity book;
            System.out.println(db.GetObject(txtBookReturnCode.getText(), "isbn").size());
            if (db.GetObject(txtBookReturnCode.getText(), "isbn").size() < 1) {
                setAlertDialog("Book not found", "", Alert.AlertType.WARNING);
            } else {
                book = (BooksEntity) db.GetObject(txtBookReturnCode.getText(), "isbn").get(0);
                txtBookReturnCode.setText(book.getIsbn());
                txtBookReturnTitle.setText(book.getTitle());
                searchedBook = book;
                txtBookReturnCode.setDisable(true);
            }
        }
    }

    @FXML
    void OnAddBorrowReturnButtonClicked(MouseEvent event) throws Exception {
        if (lblMenuTitle.getText().equals("Borrow Menu"))
            state = InterfaceStatus.BORROW_ADD;
        if (lblMenuTitle.getText().equals("Return Menu"))
            state = InterfaceStatus.RETURN_ADD;
        setMainGridVisibility(state);
        disableFields(false, state);
    }


    @FXML
    void onCheckButtonClicked(MouseEvent event) throws Exception {
        if (state == InterfaceStatus.USER_ADD) {
            if (!txtCode.getText().isEmpty() && !txtName.getText().isEmpty() && !txtSurname.getText().isEmpty()) {
                UsersEntity user = new UsersEntity();
                user.setCode(txtCode.getText());
                user.setName(txtName.getText());
                user.setSurname(txtSurname.getText());
                if (dpBirthdate.getValue() != null) {
                    user.setBirthdate(Date.valueOf(dpBirthdate.getValue()));
                }
                if (db.Insert(user)) {
                    setAlertDialog("User successfully added", "", Alert.AlertType.INFORMATION);
                } else {
                    setAlertDialog("User could not be added", "The specified user code already exists.",
                            Alert.AlertType.ERROR);
                }
            } else {
                setAlertDialog("Empty fields", "Some or all required fields are empty.", Alert.AlertType.ERROR);
            }
            clearFields();
            state = InterfaceStatus.USER_IDLE;
        }
        if (state == InterfaceStatus.USER_EDIT) {
            UsersEntity user;
            if (!txtCode.getText().isEmpty()) {
                user = (UsersEntity) db.GetObject(txtCode.getText(), "code").get(0);
                user.setName(txtName.getText());
                user.setSurname(txtSurname.getText());
                if (dpBirthdate.getValue() != null) {
                    user.setBirthdate(Date.valueOf(dpBirthdate.getValue()));
                }
                db.Update(user);
                setAlertDialog("User updated correctly", "", Alert.AlertType.INFORMATION);
            } else {
                setAlertDialog("Empty fields", "Code field cannot be empty. HERE", Alert.AlertType.WARNING);
            }
            clearFields();
            state = InterfaceStatus.USER_IDLE;
        }
        if (state == InterfaceStatus.USER_SEARCH) {
            if (!txtCode.getText().isEmpty()) {
                UsersEntity user;
                if (db.GetObject(txtCode.getText(), "code").size() < 1) {
                    setAlertDialog("User not found", "", Alert.AlertType.ERROR);
                } else {
                    user = (UsersEntity) db.GetObject(txtCode.getText(), "code").get(0);
                    txtName.setText(String.valueOf(user.getName()));
                    txtSurname.setText(user.getSurname());
                    if (user.getBirthdate() != null) {
                        dpBirthdate.setValue(user.getBirthdate().toLocalDate());
                    }
                    setAlertDialog("User found", "", Alert.AlertType.INFORMATION);
                }
            } else {
                setAlertDialog("Empty field", "Code field cannot be empty", Alert.AlertType.WARNING);
            }
            state = InterfaceStatus.USER_IDLE;
        }
        if (state == InterfaceStatus.BOOK_ADD) {
            BooksEntity book = new BooksEntity();
            if (!txtIsbn.getText().isEmpty() && !txtTitle.getText().isEmpty() && !txtPublisher.getText().isEmpty()) {
                book.setIsbn(txtIsbn.getText());
                book.setTitle(txtTitle.getText());
                book.setCopies((int) copiesSlider.getValue());
                book.setCover(txtCover.getText());
                book.setOutline(txtOutline.getText());
                book.setPublisher(txtPublisher.getText());
                if (db.Insert(book)) {
                    setAlertDialog("Book successfully added", "", Alert.AlertType.INFORMATION);
                } else {
                    setAlertDialog("Book could not be added", "The specified ISBN already exists.",
                            Alert.AlertType.ERROR);
                }

            } else {
                setAlertDialog("Empty fields", "Some or all required fields are empty.",
                        Alert.AlertType.WARNING);
            }
            clearFields();
            state = InterfaceStatus.BOOK_IDLE;
        }
        if (state == InterfaceStatus.BOOK_EDIT) {
            BooksEntity book;
            if (!txtIsbn.getText().isEmpty()) {
                book = (BooksEntity) db.GetObject(txtIsbn.getText(), "isbn").get(0);
                book.setTitle(txtTitle.getText());
                book.setCopies((int) copiesSlider.getValue());
                book.setPublisher(txtPublisher.getText());
                book.setOutline(txtOutline.getText());
                book.setCover(txtCover.getText());
                db.Update(book);
                setAlertDialog("Book updated correctly", "", Alert.AlertType.INFORMATION);
            } else {
                setAlertDialog("Empty fields", "ISBN field cannot be empty. HERE", Alert.AlertType.WARNING);
            }
            clearFields();
            state = InterfaceStatus.BOOK_IDLE;
        }
        if (state == InterfaceStatus.BOOK_SEARCH) {
            if (!txtIsbn.getText().isEmpty()) {
                BooksEntity book;
                if (db.GetObject(txtIsbn.getText(), "isbn").size() < 1) {
                    setAlertDialog("Book not found", "", Alert.AlertType.ERROR);
                } else {
                    book = (BooksEntity) db.GetObject(txtIsbn.getText(), "isbn").get(0);
                    txtIsbn.setText(String.valueOf(book.getIsbn()));
                    txtTitle.setText(book.getTitle());
                    copiesSlider.setValue(book.getCopies());
                    txtPublisher.setText(book.getPublisher());
                    txtOutline.setText(book.getOutline());
                    txtCover.setText(book.getCover());
                    setAlertDialog("Book found", "", Alert.AlertType.INFORMATION);
                }
            } else {
                setAlertDialog("Empty field", "The ISBN field cannot be empty.", Alert.AlertType.WARNING);
            }
            state = InterfaceStatus.BOOK_IDLE;
        }
        if (state == InterfaceStatus.BORROW_ADD) {
            if (!txtBookReturnCode.getText().isEmpty() && !txtUserReturnCode.getText().isEmpty()) {
                if (searchedUser.getLentBooks().size() >= 3) {
                    setAlertDialog("This user has already borrowed 3 books", "The user must return a book before " +
                            "they can borrow another one.", Alert.AlertType.INFORMATION);
                } else if (searchedBook.getCopies() < 1) {
                    ButtonType result = setAlertDialog("There are currently no copies left of this book. ",
                            "Would you like to reserve it?", Alert.AlertType.CONFIRMATION);
                    if (result == ButtonType.OK) {
                        ReservationsEntity reservation = new ReservationsEntity();
                        reservation.setBook(searchedBook);
                        reservation.setBorrower(searchedUser);
                        reservation.setDate(Date.valueOf(LocalDate.now()));
                        db.postReservation(reservation);
                    }
                } else if (searchedUser.getFined() != null &&
                        searchedUser.getFined().toLocalDate().isAfter(Date.valueOf(LocalDate.now()).toLocalDate())) {
                    setAlertDialog("User is still fined", "Fined until " +
                            searchedUser.getFined().toLocalDate().plusDays(14), Alert.AlertType.WARNING);

                } else if (searchedUser.getLentBooks().contains(new LendingEntity().getBook().equals(searchedBook.getIsbn()))) {
                    setAlertDialog("already borrowed", "", Alert.AlertType.ERROR);
                } else {
                    searchedUser.setFined(null);
                    db.Update(searchedUser);
                    if (searchedBook.getReservedBy().isEmpty() ||
                            searchedBook.getReservedBy().get(0).getBorrower().equals(searchedUser)) {
                        if (!searchedBook.getReservedBy().isEmpty()) {
                            db.deleteReservation(searchedBook.getReservedBy().get(0));
                            db.Update(searchedBook);
                        }
                        LendingEntity lending = new LendingEntity();
                        lending.setLendingdate(Date.valueOf(LocalDate.now()));
                        lending.setReturningdate(null);
                        lending.setBook(searchedBook);
                        lending.setBorrower(searchedUser);
                        db.Insert(lending);
                        int copies = searchedBook.getCopies() - 1;
                        searchedBook.setCopies(copies);
                        db.Update(searchedBook);
                        setAlertDialog("Book borrowed successfully", "", Alert.AlertType.INFORMATION);
                    } else {
                        setAlertDialog("Cannot be borrowed", "This book is reserved with priority.",
                                Alert.AlertType.INFORMATION);
                    }
                }
            }
            clearFields();
            state = InterfaceStatus.BORROW_IDLE;
        }
        if (state == InterfaceStatus.RETURN_ADD) {
            if (txtBookReturnCode.getText().isEmpty() && txtUserReturnCode.getText().isEmpty()) {
                setAlertDialog("Empty fields", "The required fields cannot be empty.",
                        Alert.AlertType.WARNING);
            } else {
                LendingEntity lending = db.getLending(searchedUser, searchedBook);
                lending.setReturningdate(Date.valueOf(LocalDate.now()));
                db.Update(lending);
                int copies = searchedBook.getCopies() + 1;
                searchedBook.setCopies(copies);
                db.Update(searchedBook);
                if (LocalDate.now().isAfter(lending.getLendingdate().toLocalDate().plusDays(14))) {
                    searchedUser.setFined(Date.valueOf(LocalDate.now()));
                    db.Update(searchedUser);
                }
                if (!searchedBook.getReservedBy().isEmpty()) {
                    setAlertDialog("Notify user", searchedBook.getReservedBy().get(0).getBorrower().getName() + " " +
                            searchedBook.getReservedBy().get(0).getBorrower().getSurname() + " is next in line on the " +
                            "reservations list.", Alert.AlertType.INFORMATION);
                }
            }
            clearFields();
            state = InterfaceStatus.RETURN_IDLE;
        }
        disableFields(true, state);
        setMainGridVisibility(state);
    }

    private ButtonType setAlertDialog(String header, String message, Alert.AlertType alertType) {
        Alert alertDialog = new Alert(alertType);
        alertDialog.setHeaderText(header);
        alertDialog.setContentText(message);
        alertDialog.showAndWait();
        return alertDialog.getResult();
    }

    @FXML
    void onCancelButtonClicked(MouseEvent event) {
        switch (state) {
            case USER_ADD, USER_EDIT, USER_SEARCH -> state = InterfaceStatus.USER_IDLE;
            case BOOK_ADD, BOOK_EDIT, BOOK_SEARCH -> state = InterfaceStatus.BOOK_IDLE;
            case BORROW_ADD -> state = InterfaceStatus.BORROW_IDLE;
            case RETURN_ADD -> state = InterfaceStatus.RETURN_IDLE;
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
        if (userMenu.isVisible()) {
            if (!txtCode.getText().isEmpty())
                state = InterfaceStatus.USER_EDIT;
            else {
                state = InterfaceStatus.USER_IDLE;
                setAlertDialog("Empty field", "Code field cannot be empty.", Alert.AlertType.WARNING);
            }
        }
        if (bookMenu.isVisible()) {
            if (!txtIsbn.getText().isEmpty()) {
                state = InterfaceStatus.BOOK_EDIT;
            } else {
                state = InterfaceStatus.BOOK_IDLE;
                setAlertDialog("Empty field", "ISBN field cannot be empty.", Alert.AlertType.WARNING);
            }
        }

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
            case RETURN_IDLE, BORROW_IDLE -> {
                returnMenu.setVisible(true);
                bottomPanelAdd.setVisible(true);
            }
            case BORROW_ADD, RETURN_ADD -> {
                returnMenu.setVisible(true);
                bottomSaveCancel.setVisible(true);
            }
        }
    }

    void hideGrids() {
        userMenu.setVisible(false);
        bookMenu.setVisible(false);
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
            case USER_EDIT -> {
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
            case BORROW_IDLE, BORROW_ADD, RETURN_ADD, RETURN_IDLE -> {
                txtBookReturnCode.setDisable(disable);
                txtUserReturnCode.setDisable(disable);
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
        txtBookReturnCode.clear();
        txtUserReturn.clear();
        txtBookReturnTitle.clear();
        txtUserReturnCode.clear();
        searchedBook = null;
        searchedUser = null;
    }
}