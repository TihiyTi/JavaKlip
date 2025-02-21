package com.ti.dspview;

import com.ti.viewcore.RealTimeChart;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javafx.stage.Window;

public class FxGraphicBuilder {
    public static final AtomicBoolean IS_FX_CONTEXT = new AtomicBoolean(false);

    /**
     * Инициализирует JavaFX-контекст, если он ещё не был запущен.
     */
    public static void initFXContextTrue() {
        if (IS_FX_CONTEXT.compareAndSet(false, true)) {
            System.out.println("Fx context init");
            Platform.startup(() -> {}); // Запуск JavaFX Application Thread
        }
    }

    /**
     * Отображает график на JavaFX-окне.
     *
     * @param list список чисел для построения графика
     */
    public static void listLinePlot(List<Number> list) {
        LineChart<Number, Number> chart = getChart(list);

        // Запускаем в JavaFX Application Thread
        Platform.runLater(() -> initWindow("Test", chart));
    }

    /**
     * Создаёт график из списка чисел.
     */
    private static <T extends Number> LineChart<Number, Number> getChart(List<T> data) {
        ConcurrentLinkedQueue<Double> queue = new ConcurrentLinkedQueue<>();

        List<Double> doubleList = data.stream().map(Number::doubleValue).collect(Collectors.toList());
        RealTimeChart chartProvider = new RealTimeChart(data.size(), false);

        queue.addAll(doubleList);
        chartProvider.setQueue(queue);

        return chartProvider.getChart();
    }

    /**
     * Создаёт и показывает JavaFX-окно с графиком.
     */
    private static void initWindow(String title, LineChart<Number, Number>... charts) {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setTitle(title);
            VBox root = new VBox();

            // Настройка размеров графиков
            for (LineChart<Number, Number> chart : charts) {
                chart.setCreateSymbols(false);
                chart.prefHeightProperty().bind(root.heightProperty().divide(charts.length));
            }

            root.getChildren().addAll(charts);
            stage.setScene(new Scene(root, 600, 500));

            stage.setOnCloseRequest(event -> {
                if (Stage.getWindows().stream().noneMatch(Window::isShowing)) {
                    System.out.println("Закрыты все окна. Завершаем приложение...");
                    Platform.exit(); // Завершаем JavaFX
                    System.exit(0);   // Полностью завершаем JVM
                }
                IS_FX_CONTEXT.set(false);
            });

            stage.show();
        });
    }
}
