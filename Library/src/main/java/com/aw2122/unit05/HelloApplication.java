package com.aw2122.unit05;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 536, 571.5);
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //DatabaseController db = new DatabaseController();
        //db.GetData("c001");
        launch();

    }
}