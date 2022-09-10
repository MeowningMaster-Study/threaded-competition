package com.meowningmaster.threadedcompetition;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class BController implements Initializable {
    public Button startOneButton;
    public Button stopOneButton;
    public Button startTwoButton;
    public Button stopTwoButton;
    @FXML
    private Slider slider;

    private BWorkerThread threadOne;
    private BWorkerThread threadTwo;

    final AtomicInteger value = new AtomicInteger(50);
    final AtomicBoolean semaphore = new AtomicBoolean(false);
    final AtomicBoolean updatePooled = new AtomicBoolean(false);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Update fn = () -> Platform.runLater(() -> {
            slider.setValue(value.get());
            updatePooled.set(false);
        });

        startOneButton.setOnAction((event) -> {
            threadOne = new BWorkerThread(value, 10, updatePooled, fn, semaphore);
            threadOne.setPriority(1);
            startOneButton.setDisable(true);
            stopOneButton.setDisable(false);
            threadOne.start();
        });

        startTwoButton.setOnAction((event) -> {
            threadTwo = new BWorkerThread(value, 90, updatePooled, fn, semaphore);
            threadTwo.setPriority(10);
            startTwoButton.setDisable(true);
            stopTwoButton.setDisable(false);
            threadTwo.start();
        });

        stopOneButton.setOnAction((event) -> {
            threadOne.interrupt();
            startOneButton.setDisable(false);
            stopOneButton.setDisable(true);
        });

        stopTwoButton.setOnAction((event) -> {
            threadTwo.interrupt();
            startTwoButton.setDisable(false);
            stopTwoButton.setDisable(true);
        });
    }
}

