package com.meowningmaster.threadedcompetition;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LabApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //! change to: a-view.fxml | b-view.fxml
        String view = "b-view.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(LabApplication.class.getResource(view));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Lab 1");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}