package com.ti.examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class RealTimeChartApp extends Application {
    private RealTimeMultiChart chart;
    private final Random random = new Random();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        int seriesCount = 6; // Количество линий (можно менять)
        chart = new RealTimeMultiChart(seriesCount, 1500, false);
        LineChart<Number, Number> lineChart = chart.getChart();
        ChartControlPanel controlPanel = new ChartControlPanel(chart);

        HBox root = new HBox(10, controlPanel, lineChart); // Панель слева, график справа
        Scene scene = new Scene(root, 1500, 600);

        primaryStage.setTitle("Real-Time Multi Chart");
        primaryStage.setScene(scene);
        primaryStage.show();

        startDataFeed();
        chart.printSeriesColors();
    }



    private void toggleSeries(String name) {
        boolean currentVisibility = chart.getChart().getData().stream()
                .anyMatch(series -> series.getName().equals("Серия" + name));

        chart.setSeriesVisible(name, !currentVisibility);
    }

    private void startDataFeed() {
        new Thread(() -> {
            double[] frequencies = {0.1, 0.15, 0.2, 0.25, 0.3, 0.35, 0.4, 0.45, 0.5, 0.55, 0.6, 0.65}; // Частоты синусоид
            double time = 0.0; // Время (X)

            while (true) {
                try {
                    Thread.sleep(50); // Частота обновления графика

                    Double[] values = new Double[frequencies.length];
                    for (int i = 0; i < frequencies.length; i++) {
                        values[i] = Math.sin(time * frequencies[i]) * (10 + i); // Разная амплитуда
                    }

                    chart.addElements(values);
                    time += 0.1; // Увеличиваем "время" для плавности синусоид
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
