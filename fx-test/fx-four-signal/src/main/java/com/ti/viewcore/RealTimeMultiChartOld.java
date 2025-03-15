package com.ti.viewcore;

import com.ti.PropertiesService;
import javafx.animation.AnimationTimer;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RealTimeMultiChartOld {
    private int maxDataPoint = 1000;
    private boolean skip10 = true;
    private int seriesCount;
    private final int CLEANUP_THRESHOLD = 25;

    private Map<String, ConcurrentLinkedQueue<Double>> dataQueues = new HashMap<>();
    private Map<String, XYChart.Series<Number, Number>> seriesMap = new HashMap<>();

    private LineChartWithMarkers<Number, Number> chart;
    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private int xSeriesData = 0;

    public RealTimeMultiChartOld(int seriesCount) {
        this(seriesCount, Integer.parseInt(PropertiesService.getGlobalProperty("pointOnView")),
                Boolean.parseBoolean(PropertiesService.getGlobalProperty("skip10")));
    }

    public RealTimeMultiChartOld(int seriesCount, int maxDataPoint, boolean skip10) {
        this.seriesCount = seriesCount;
        this.maxDataPoint = maxDataPoint;
        this.skip10 = skip10;
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

        chart.setMinSize(100, 50);  // Минимальный размер

        for (int i = 0; i < seriesCount; i++) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName("Серия " + i);
            seriesMap.put( ""+i, series);
            dataQueues.put(""+i, new ConcurrentLinkedQueue<>());
            chart.getData().add(series);
        }

        prepareTimeline();
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
        int maxNewPoints = 0; // Счётчик количества новых точек

        for (String name : seriesMap.keySet()) {
            ConcurrentLinkedQueue<Double> queue = dataQueues.get(name);
            XYChart.Series<Number, Number> series = seriesMap.get(name);

            int newPoints = 0;

            while (!queue.isEmpty()) {
                double value = queue.poll();
                series.getData().add(new XYChart.Data<>(xSeriesData / 10 + newPoints, value));
                newPoints++;
            }

            if (newPoints > 0) {
                maxNewPoints = Math.max(maxNewPoints, newPoints);
            }

            // Удаление точек, если их стало слишком много
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

        // Увеличиваем xSeriesData на максимальное количество новых точек
        if (maxNewPoints > 0) {
            xSeriesData += maxNewPoints;
        }

        xAxis.setLowerBound((xSeriesData / 10 - maxDataPoint));
        xAxis.setUpperBound((xSeriesData) / 10 - 1);
    }

    private void updateDataAll() {
        int maxNewPoints = 0; // Количество новых точек за такт таймера

        for (String name : seriesMap.keySet()) {
            ConcurrentLinkedQueue<Double> queue = dataQueues.get(name);
            XYChart.Series<Number, Number> series = seriesMap.get(name);

            int newPoints = 0;

            // Добавляем все новые точки в график
            while (!queue.isEmpty()) {
                double value = queue.poll();
                series.getData().add(new XYChart.Data<>(xSeriesData + newPoints, value));
                newPoints++;
            }

            if (newPoints > 0) {
                maxNewPoints = Math.max(maxNewPoints, newPoints);
            }

            // Удаление точек, но начинаем проверку с CLEANUP_THRESHOLD-й точки
            double lowerBound = xAxis.getLowerBound();
            int seriesSize = series.getData().size();
            int removeIndex = CLEANUP_THRESHOLD;

            while (seriesSize > CLEANUP_THRESHOLD) {
                // Берём `CLEANUP_THRESHOLD`-ю точку и проверяем её
                XYChart.Data<Number, Number> checkPoint = series.getData().get(removeIndex);
                double checkXValue = checkPoint.getXValue().doubleValue();

                if (checkXValue >= lowerBound) {
                    // Если `CLEANUP_THRESHOLD`-я точка всё ещё видима, останавливаем удаление
                    break;
                }

                // Если `CLEANUP_THRESHOLD`-я точка уже вышла за пределы видимости — удаляем `CLEANUP_THRESHOLD` точек
                series.getData().remove(0, CLEANUP_THRESHOLD);

                // Обновляем размер списка
                seriesSize = series.getData().size();
            }
        }

        // Увеличиваем xSeriesData только на количество реально пришедших новых точек
        if (maxNewPoints > 0) {
            xSeriesData += maxNewPoints;
        }

        xAxis.setLowerBound((xSeriesData - maxDataPoint));
        xAxis.setUpperBound(xSeriesData);
    }

    public void addElement(Double value, String name) {
        dataQueues.get(name).add(value);

    }

    public void addElements(Double... values) {
        for (int i = 0; i < values.length && i < seriesCount; i++) {
            dataQueues.get(""+i).add(values[i]);
//            System.out.println("В " + i + "  добавили " + values[i]);
        }
    }

    public LineChart<Number, Number> getChart() {
        return chart;
    }
}