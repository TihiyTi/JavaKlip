package com.ti.desktop;

import com.ti.viewcore.RealTimeMultiChartConsumer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestView2 extends Application{
    int time = 0;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root =  new BorderPane();
        Scene scene = new Scene(root,1050,600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Test RealTimeView");
        primaryStage.show();

        RealTimeMultiChartConsumer pane = new RealTimeMultiChartConsumer(3,1000, false);
        RealTimeMultiChartConsumer pane2 = new RealTimeMultiChartConsumer(3,1000, false);
        RealTimeMultiChartConsumer pane3 = new RealTimeMultiChartConsumer(3,1000, false);
        RealTimeMultiChartConsumer pane4 = new RealTimeMultiChartConsumer(3,1000, false);
        RealTimeMultiChartConsumer pane5 = new RealTimeMultiChartConsumer(3,1000, false);
        RealTimeMultiChartConsumer pane6 = new RealTimeMultiChartConsumer(3,1000, false);
        RealTimeMultiChartConsumer pane7 = new RealTimeMultiChartConsumer(3,1000, false);
        RealTimeMultiChartConsumer pane8 = new RealTimeMultiChartConsumer(3,1000, false);
//        RealTimeChartConsumer pane2 = new RealTimeChartConsumer(1000, false);
//        RealTimeChartConsumer pane3 = new RealTimeChartConsumer(1000, false);
//        RealTimeChartConsumer pane4 = new RealTimeChartConsumer(1000, false);
//        RealTimeChartConsumer pane5 = new RealTimeChartConsumer(1000, false);
//        RealTimeChartConsumer pane6 = new RealTimeChartConsumer(1000, false);
//        RealTimeChartConsumer pane7 = new RealTimeChartConsumer(1000, false);
//        RealTimeChartConsumer pane8 = new RealTimeChartConsumer(1000, false);

        VBox box = new VBox(pane.getChart(), pane2.getChart(),pane3.getChart(), pane4.getChart(), pane5.getChart(), pane6.getChart(), pane7.getChart(), pane8.getChart());
        pane.getChart().prefHeightProperty().bind(box.heightProperty().divide(8));
        pane2.getChart().prefHeightProperty().bind(box.heightProperty().divide(8));
        pane3.getChart().prefHeightProperty().bind(box.heightProperty().divide(8));
        pane4.getChart().prefHeightProperty().bind(box.heightProperty().divide(8));
        pane5.getChart().prefHeightProperty().bind(box.heightProperty().divide(8));
        pane6.getChart().prefHeightProperty().bind(box.heightProperty().divide(8));
        pane7.getChart().prefHeightProperty().bind(box.heightProperty().divide(8));
        pane8.getChart().prefHeightProperty().bind(box.heightProperty().divide(8));
//
//        box.setSpacing(10);

//        pane.setScaleX(2);
//        double scale = 0.5;
//        pane.setScaleX(scale);
        root.setCenter(box);
//        root.setBottom(pane);


        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(()->{
            pane.putElements(
                    200+100*Math.sin(2*Math.PI*1*time/90)+time,
                    2000+100*Math.sin(2*Math.PI*1*time/90),
                    200.+Math.random()*2
                    );
            pane2.putElements(
                    200+100*Math.sin(2*Math.PI*1*time/90)+time,
                    2000+100*Math.sin(2*Math.PI*1*time/90),
                    200.+Math.random()*2
                    );
            pane3.putElements(
                    200+100*Math.sin(2*Math.PI*1*time/90)+time,
                    2000+100*Math.sin(2*Math.PI*1*time/90),
                    200.+Math.random()*2
                    );
            pane4.putElements(
                    200+100*Math.sin(2*Math.PI*1*time/90)+time,
                    2000+100*Math.sin(2*Math.PI*1*time/90),
                    200.+Math.random()*2
                    );
            pane5.putElements(
                    200+100*Math.sin(2*Math.PI*1*time/90)+time,
                    2000+100*Math.sin(2*Math.PI*1*time/90),
                    200.+Math.random()*2
            );
            pane6.putElements(
                    200+100*Math.sin(2*Math.PI*1*time/90)+time,
                    2000+100*Math.sin(2*Math.PI*1*time/90),
                    200.+Math.random()*2
            );
            pane7.putElements(
                    200+100*Math.sin(2*Math.PI*1*time/90)+time,
                    2000+100*Math.sin(2*Math.PI*1*time/90),
                    200.+Math.random()*2
            );
            pane8.putElements(
                    200+100*Math.sin(2*Math.PI*1*time/90)+time,
                    2000+100*Math.sin(2*Math.PI*1*time/90),
                    200.+Math.random()*2
            );
//            pane2.putElement(2000+100*Math.sin(2*Math.PI*1*time/90));
//            pane3.putElement(200+100*Math.sin(2*Math.PI*1*time/90)+time);
//            pane4.putElement(2000+100*Math.sin(2*Math.PI*1*time/90));
//            pane5.putElement(200+100*Math.sin(2*Math.PI*1*time/90)+time);
//            pane6.putElement(2000+100*Math.sin(2*Math.PI*1*time/90));
//            pane7.putElement(200+100*Math.sin(2*Math.PI*1*time/90)+time);
//            pane8.putElement(2000+100*Math.sin(2*Math.PI*1*time/90));

            time++;
//            pane.addPoints(Math.random()*100);
        }, 0L, 10L, TimeUnit.MILLISECONDS);
    }
    @Override
    public void stop() throws Exception{
        super.stop();
        System.exit(0);
    }

}
