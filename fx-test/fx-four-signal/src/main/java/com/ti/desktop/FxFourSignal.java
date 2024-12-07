package com.ti.desktop;

import com.ti.PropertiesService;
import com.ti.viewcore.SimpleSignalPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class FxFourSignal extends Application {
    public static final String SCENE = "panebutton.fxml";
    public static void main(String[] args) {Application.launch(args);}

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Working Directory: " + System.getProperty("user.dir"));
        PropertiesService.setGlobalPropertyFileName(FxFourSignal.class.getSimpleName());
        String prop = PropertiesService.getGlobalProperty("prop");
        System.out.println(prop);

//        System.out.println(System.getProperty("java.class.path"));
        FXMLLoader loader = new FXMLLoader( getClass().getResource(SCENE));
        BorderPane root = loader.load();
        Scene scene =  new Scene(root, 700,500);
        stage.setScene(scene);
        stage.setTitle("FxFourSignal");
        stage.show();

        // Создаем VBox
        VBox vBox = new VBox(10); // отступы между Pane

        SimpleSignalPane signalPane = new SimpleSignalPane();
        signalPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        signalPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        signalPane.prefWidthProperty().bind(vBox.widthProperty());
        signalPane.prefHeightProperty().bind(root.heightProperty().divide(2).subtract(15)); // учитываем отступы


//        Random random = new Random();
//        List<Double> randomList = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            randomList.add(random.nextDouble() * 100);
//        }
//        signalPane.addPoints(randomList.toArray(new Double[0]));
        vBox.getChildren().add(signalPane);
        root.setCenter(vBox);


        // Поток для добавления данных в очередь
        Random r = new Random();
        Thread dataProducer = new Thread(() -> {
            try {
                for (int i = 1; i <= 1100; i++) {
                    Thread.sleep(40); // Симуляция вычислений
                    signalPane.addPoints(r.nextDouble()*100);
//                    Platform.runLater(() -> signalPane.putElement(r.nextDouble()*100));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        dataProducer.setDaemon(true);
        dataProducer.start();


    }
}
