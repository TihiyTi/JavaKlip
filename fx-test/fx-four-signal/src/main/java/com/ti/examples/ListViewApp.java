package com.ti.examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListViewApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll("Вариант 1", "Вариант 2", "Вариант 3", "Вариант 4");

        VBox root = new VBox(listView);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("Простое JavaFX приложение");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
