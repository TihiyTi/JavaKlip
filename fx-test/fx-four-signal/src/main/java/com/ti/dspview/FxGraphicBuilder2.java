package com.ti.dspview;

import com.ti.viewcore.RealTimeChart;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class FxGraphicBuilder2 extends Application {

    private volatile static boolean IS_FX_CONTEXT = false;
    private static final AtomicBoolean IS_FX_CONTEXT_2 = new AtomicBoolean(false);

    @Override
    public void start(Stage primaryStage) throws Exception {
        IS_FX_CONTEXT = true;
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }

    public static void listLinePlot(List<Number> list){
        LineChart chart = getChart(list);

        Platform.runLater(()->{
            initWindow("Test", chart);
        });
    }
    public static void intListLinePlot(List<Integer> list){

        LineChart chart = getChart(list);
        Platform.runLater(()->{
            initWindow("Test", chart);
        });
    }

    public static void listLinePlot(List<Number> ... arrayOfList){
        LineChart[] charts = Arrays.stream(arrayOfList).map(FxGraphicBuilder2::getChart).toArray(LineChart[]::new);

        Platform.runLater(()->{
            initWindow("Test", charts);
        });
    }

    public static void initFXContext(){
        if(!IS_FX_CONTEXT){
            System.out.println("Fx context init");
            new Thread(Application::launch).start();
        }
    }

    public static void initFXContextTrue() {
        if (IS_FX_CONTEXT_2.get()) {
            System.out.println("Fx context init");
            Platform.startup(() -> {}); // Инициализация JavaFX без запуска UI
        }
    }

    private static void initWindow(String name, LineChart ... charts){
        while (!IS_FX_CONTEXT_2.get()){}

        Stage stage =  new Stage();
        stage.setTitle("DspTest");
        VBox root = new VBox();

        Arrays.stream(charts)
                .peek(x->x.setCreateSymbols(false))
                .forEach(x-> x.prefHeightProperty().bind(root.heightProperty().divide(charts.length)));

        root.getChildren().addAll(charts);
        stage.setScene(new Scene(root, 600, 500));
        stage.show();
    }

    private static <T extends Number> LineChart getChart(List<T> data){
        ConcurrentLinkedQueue<Double> queue = new ConcurrentLinkedQueue<>();

        List<Double> doubleList = data.stream().map(Number::doubleValue).collect(Collectors.toList());
        RealTimeChart chartProvider = new RealTimeChart(data.size(), false);

        queue.addAll(doubleList);
        chartProvider.setQueue(queue);

        return chartProvider.getChart();
    }
}
