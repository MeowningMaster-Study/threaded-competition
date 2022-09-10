package com.meowningmaster.threadedcompetition;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class AController implements Initializable {
    @FXML
    private Slider slider;

    @FXML
    private Spinner<Integer> threadIncSpinner;

    @FXML
    private Spinner<Integer> threadDecSpinner;

    @FXML
    private Button startButton;

    private AWorkerThread threadInc;
    private AWorkerThread threadDec;

    final AtomicInteger value = new AtomicInteger(50);
    final AtomicBoolean updatePooled = new AtomicBoolean(false);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> valueFactoryInc =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        valueFactoryInc.setValue(5);
        threadIncSpinner.setValueFactory(valueFactoryInc);
        threadIncSpinner.valueProperty().addListener((observable, oldValue, newValue) -> threadIncSetPriority(newValue));

        SpinnerValueFactory<Integer> valueFactoryDec =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10);
        valueFactoryDec.setValue(5);
        threadDecSpinner.setValueFactory(valueFactoryDec);
        threadDecSpinner.valueProperty().addListener((observable, oldValue, newValue) -> threadDecSetPriority(newValue));

        startButton.setOnAction(event -> onStartButtonClick());

        Update fn = () -> Platform.runLater(() -> {
            slider.setValue(value.get());
            updatePooled.set(false);
        });

        threadInc = new AWorkerThread(value, 10, updatePooled, fn);
        threadInc.setPriority(5);

        threadDec = new AWorkerThread(value, 90, updatePooled, fn);
        threadDec.setPriority(5);
    }

    protected void threadIncSetPriority(Integer value) {
        threadInc.setPriority(value);
        System.out.println("Thread Inc Priority: " + value);
    }

    protected void threadDecSetPriority(Integer value) {
        threadDec.setPriority(value);
        System.out.println("Thread Dec Priority: " + value);
    }


    protected void onStartButtonClick() {
        threadInc.start();
        threadDec.start();

        startButton.setDisable(true);
    }
}

