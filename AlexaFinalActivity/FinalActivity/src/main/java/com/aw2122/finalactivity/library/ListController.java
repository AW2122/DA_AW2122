package com.aw2122.finalactivity.library;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class ListController {
    @FXML
    private ListView<Object> objectListView;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button btnCancel;

    Object selectedObject;
    List<Object> objectList;
    InterfaceController interfaceController;

    public void setInterfaceController(InterfaceController interfaceController) {
        this.interfaceController = interfaceController;
    }

    /**
     * This method gets all items from the object list.
     * @param objectList
     */
    public void setObjectList(List<Object> objectList) {
        this.objectList = objectList;
        objectListView.getItems().addAll(FXCollections.observableList(objectList));
    }

    /**
     * When clicked, gets the selected item from the list and closes the window.
     * @param event
     */
    @FXML
    void onConfirmClick(MouseEvent event) {
        selectedObject = objectListView.getSelectionModel().getSelectedItem();
        Stage stage = (Stage) objectListView.getScene().getWindow();
        stage.close();
    }

    /**
     * This method closes the window.
     * @param event
     */
    @FXML
    void onCancelClick(MouseEvent event) {
        Stage stage = (Stage) objectListView.getScene().getWindow();
        stage.close();
    }
}
