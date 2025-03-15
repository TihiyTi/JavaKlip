package com.ti.examples;

import com.ti.PropertiesService;
import com.ti.viewcore.LineChartWithMarkers;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RealTimeMultiChart {
    private int maxDataPoint = 1000;
    private boolean skip10 = false;
    private final int CLEANUP_THRESHOLD = 25;
    private int seriesCount;

    private Map<String, ConcurrentLinkedQueue<XYChart.Data<Number, Number>>> dataQueues = new HashMap<>();
    private Map<String, XYChart.Series<Number, Number>> seriesMap = new HashMap<>();
    private Map<String, Boolean> seriesVisibilityMap = new HashMap<>();

    private LineChartWithMarkers<Number, Number> chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private int xSeriesData = 0;

    private String baseChartName = "Серия";

    private String w = "1";

    public RealTimeMultiChart(int seriesCount) {
        this(seriesCount, Integer.parseInt(PropertiesService.getGlobalProperty("pointOnView")),
                Boolean.parseBoolean(PropertiesService.getGlobalProperty("skip10")));
    }

    public RealTimeMultiChart(int seriesCount, int maxDataPoint, boolean skip10) {
        this.seriesCount = seriesCount;
        this.maxDataPoint = maxDataPoint;
        this.skip10 = skip10;
        init();
    }

    public RealTimeMultiChart(int seriesCount, int maxDataPoint, boolean skip10, String title) {
        this.seriesCount = seriesCount;
        this.maxDataPoint = maxDataPoint;
        this.skip10 = skip10;
        this.baseChartName = title;
        init();
    }

    private void init() {
        xAxis = new NumberAxis(0, maxDataPoint, maxDataPoint / 10);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(false);
        yAxis = new NumberAxis();
        yAxis.setAutoRanging(true);
        yAxis.setForceZeroInRange(false);

        chart = new LineChartWithMarkers<>(xAxis, yAxis);
        chart.setCreateSymbols(false);
        chart.setAnimated(false);
        chart.setMinSize(100, 50);

        for (int i = 0; i < seriesCount; i++) {
            String seriesName = "" + i;
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(baseChartName + i);
            seriesMap.put(seriesName, series);
            dataQueues.put(seriesName, new ConcurrentLinkedQueue<>());
            seriesVisibilityMap.put(seriesName, true);
            chart.getData().add(series);
            series.getNode().lookup(".chart-series-line").setStyle("-fx-stroke-width: 2px;");
        }

        prepareTimeline();
        Platform.runLater(this::updateLineThickness);
    }

    private void updateLineThickness() {
        Platform.runLater(() -> {
            for (String name : seriesMap.keySet()) {
                XYChart.Series<Number, Number> series = seriesMap.get(name);
                if (series.getNode() != null && visibilityStates.getOrDefault(name, true)) {
                    // Применяем только к ВИДИМЫМ сериям
                    Node line = series.getNode().lookup(".chart-series-line");
                    if (line != null) {
                        line.setStyle("-fx-stroke-width: 2px;"); // Линии становятся тоньше
                    }
                }
            }
        });
    }

    private void prepareTimeline() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (skip10) {
                    updateData();
                } else {
                    updateDataAll();
                }
            }
        }.start();
    }

    private void updateData() {
        int maxNewPoints = 0;

        for (String name : seriesMap.keySet()) {
            ConcurrentLinkedQueue<XYChart.Data<Number, Number>> queue = dataQueues.get(name);
            XYChart.Series<Number, Number> series = seriesMap.get(name);

            if (!seriesVisibilityMap.getOrDefault(name, true)) {
                continue;
            }

            int newPoints = 0;
            while (!queue.isEmpty()) {
                series.getData().add(queue.poll());
                newPoints++;
            }

            if (newPoints > 0) {
                maxNewPoints = Math.max(maxNewPoints, newPoints);
            }

            cleanOldData(series);
        }

        if (maxNewPoints > 0) {
            xSeriesData += maxNewPoints;
        }

        xAxis.setLowerBound((xSeriesData / 10 - maxDataPoint));
        xAxis.setUpperBound((xSeriesData) / 10 - 1);
    }

    private void updateDataAll() {
        int maxNewPoints = 0;

        for (String name : seriesMap.keySet()) {
            ConcurrentLinkedQueue<XYChart.Data<Number, Number>> queue = dataQueues.get(name);
            XYChart.Series<Number, Number> series = seriesMap.get(name);

            if (!seriesVisibilityMap.getOrDefault(name, true)) {
                continue;
            }

            int newPoints = 0;
            while (!queue.isEmpty()) {
                series.getData().add(queue.poll());
                newPoints++;
            }

            if (newPoints > 0) {
                maxNewPoints = Math.max(maxNewPoints, newPoints);
            }

            cleanOldData(series);
        }

        if (maxNewPoints > 0) {
            xSeriesData += maxNewPoints;
        }

        xAxis.setLowerBound((xSeriesData - maxDataPoint));
        xAxis.setUpperBound(xSeriesData);
    }

    private void cleanOldData(XYChart.Series<Number, Number> series) {
        double lowerBound = xAxis.getLowerBound();
        int invisiblePoints = 0;
        while (!series.getData().isEmpty() &&
                series.getData().get(0).getXValue().doubleValue() < lowerBound) {
            invisiblePoints++;
            if (invisiblePoints >= CLEANUP_THRESHOLD) {
                break;
            }
        }
        if (invisiblePoints >= CLEANUP_THRESHOLD) {
            series.getData().remove(0, invisiblePoints);
        }
    }

    public void addElement(Double value, String name) {
        if (dataQueues.containsKey(name)) {
            int xValue = xSeriesData;
            dataQueues.get(name).add(new XYChart.Data<>(xValue, value));
        }
    }

    public void addElements(Double... values) {
        for (int i = 0; i < values.length && i < seriesCount; i++) {
            String seriesName = "" + i;
            if (dataQueues.containsKey(seriesName)) {
                int xValue = xSeriesData;
                dataQueues.get(seriesName).add(new XYChart.Data<>(xValue, values[i]));
            }
        }
    }

    public LineChart<Number, Number> getChart() {
        return chart;
    }

//todo легенда NO, масштабирование Ок

//    public void setSeriesVisible(String name, boolean visible) {
//        if (seriesMap.containsKey(name)) {
//            seriesVisibilityMap.put(name, visible);
//            XYChart.Series<Number, Number> series = seriesMap.get(name);
//
//            if (visible) {
//                if (!chart.getData().contains(series)) {
//                    chart.getData().add(series);
//                }
//
//                // Добавляем накопленные точки с правильными X-значениями
//                ConcurrentLinkedQueue<XYChart.Data<Number, Number>> queue = dataQueues.get(name);
//                while (!queue.isEmpty()) {
//                    series.getData().add(queue.poll());
//                }
//            } else {
//                chart.getData().remove(series);
//            }
//        }
//    }

//todo легенда OK, масштабирование NO


//    public void setSeriesVisible(String name, boolean visible) {
//        if (seriesMap.containsKey(name)) {
//            XYChart.Series<Number, Number> series = seriesMap.get(name);
//
//            if (visible) {
//                // Включаем серию (делаем её видимой)
//                series.getNode().setStyle("");
//                for (XYChart.Data<Number, Number> data : series.getData()) {
//                    if (data.getNode() != null) {
//                        data.getNode().setStyle("");
//                    }
//                }
//            } else {
//                // Отключаем серию (делаем её прозрачной, но не удаляем)
//                series.getNode().setStyle("-fx-stroke: transparent;");
//                for (XYChart.Data<Number, Number> data : series.getData()) {
//                    if (data.getNode() != null) {
//                        data.getNode().setStyle("-fx-background-color: transparent;");
//                    }
//                }
//            }
//        }
//    }


    private Map<String, Boolean> visibilityStates = new HashMap<>();

//    public void setSeriesVisible(String name, boolean visible) {
//        if (seriesMap.containsKey(name)) {
//            XYChart.Series<Number, Number> series = seriesMap.get(name);
//            Node node = series.getNode();
//
//            visibilityStates.put(name, visible);
//
//            if (visible) {
//                // Восстанавливаем нормальный стиль и включаем расчёт оси Y
//                if (node != null) {
//                    node.setStyle("");
//                }
//                for (XYChart.Data<Number, Number> data : series.getData()) {
//                    if (data.getNode() != null) {
//                        data.getNode().setStyle("");
//                    }
//                }
//            } else {
//                // Делаем линию прозрачной и исключаем её из масштабирования
//                if (node != null) {
//                    node.setStyle("-fx-stroke: transparent;");
//                }
//                for (XYChart.Data<Number, Number> data : series.getData()) {
//                    if (data.getNode() != null) {
//                        data.getNode().setStyle("-fx-background-color: transparent;");
//                    }
//                }
//            }
//
//            // Пересчитываем ось Y только по видимым сериям
//            updateYAxisBounds();
//        }
//        updateLineThickness();
//    }

    public void setSeriesVisible(String name, boolean visible) {
        if (seriesMap.containsKey(name)) {
            XYChart.Series<Number, Number> series = seriesMap.get(name);
            Node node = series.getNode();

            visibilityStates.put(name, visible);

            if (visible) {
                // Восстанавливаем линию
                if (node != null) {
                    node.setStyle("");
                }
                for (XYChart.Data<Number, Number> data : series.getData()) {
                    if (data.getNode() != null) {
                        data.getNode().setStyle("");
                    }
                }
            } else {
                // Делаем линию прозрачной, но НЕ удаляем её данные
                if (node != null) {
                    node.setStyle("-fx-stroke: transparent;");
                }
                for (XYChart.Data<Number, Number> data : series.getData()) {
                    if (data.getNode() != null) {
                        data.getNode().setStyle("-fx-background-color: transparent;");
                    }
                }
            }

            // Пересчитываем ось Y, чтобы скрытые серии не учитывались
            updateYAxisBounds();

            // Устанавливаем толщину линии после скрытия/включения
            Platform.runLater(() -> updateLineThickness());
        }
    }


    private void updateYAxisBounds() {
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        for (String name : seriesMap.keySet()) {
            if (!visibilityStates.getOrDefault(name, true)) {
                continue; // Игнорируем скрытые серии
            }

            XYChart.Series<Number, Number> series = seriesMap.get(name);
            for (XYChart.Data<Number, Number> data : series.getData()) {
                double y = data.getYValue().doubleValue();
                if (y < minY) minY = y;
                if (y > maxY) maxY = y;
            }
        }

        // Обновляем границы оси Y
        if (minY != Double.MAX_VALUE && maxY != Double.MIN_VALUE) {
            yAxis.setAutoRanging(false);
            yAxis.setLowerBound(minY - 5); // Немного запасов для удобства
            yAxis.setUpperBound(maxY + 5);
        }
    }

    public Map<String, XYChart.Series<Number, Number>> getSeriesMap() {
        return seriesMap;
    }
    public void printSeriesColors() {
        PauseTransition delay = new PauseTransition(Duration.seconds(1)); // Ждём 1 секунду
        delay.setOnFinished(event -> {
            System.out.println("Цвета графиков (из стандартной палитры):");

            // Предопределённые цвета JavaFX
            String[] defaultColors = {
                    "#f3622d", "#fba71b", "#57b757", "#41a9c9",
                    "#4258c9", "#9a42c8", "#c84164", "#888888",
                    "#c8c8c8", "#a4a4a4", "#636363", "#3b3b3b"
            };
            Color color2 = Color.web("#f3622d");
            int index = 0;
            for (String seriesName : seriesMap.keySet()) {
                String color = defaultColors[index % defaultColors.length]; // Берём стандартный цвет
                System.out.println(seriesName + ": " + color);
                index++;
            }
        });
        delay.play();
    }
}
