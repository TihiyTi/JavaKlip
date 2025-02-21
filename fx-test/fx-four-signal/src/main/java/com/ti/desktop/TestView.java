package com.ti.desktop;

import com.ti.viewcore.SimpleSignalPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestView extends Application{
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

        SimpleSignalPane pane = new SimpleSignalPane();
        SimpleSignalPane pane2 = new SimpleSignalPane();
        SimpleSignalPane pane3 = new SimpleSignalPane();
        SimpleSignalPane pane4 = new SimpleSignalPane();
        SimpleSignalPane pane5 = new SimpleSignalPane();
        SimpleSignalPane pane6 = new SimpleSignalPane();
        SimpleSignalPane pane7 = new SimpleSignalPane();
        SimpleSignalPane pane8 = new SimpleSignalPane();
        VBox box = new VBox(pane,pane2,pane3,pane4,pane5,pane6,pane7,pane8);
        pane.prefHeightProperty().bind(box.heightProperty().divide(8));
        pane2.prefHeightProperty().bind(box.heightProperty().divide(8));
        pane3.prefHeightProperty().bind(box.heightProperty().divide(8));
        pane4.prefHeightProperty().bind(box.heightProperty().divide(8));
        pane5.prefHeightProperty().bind(box.heightProperty().divide(8));
        pane6.prefHeightProperty().bind(box.heightProperty().divide(8));
        pane7.prefHeightProperty().bind(box.heightProperty().divide(8));
        pane8.prefHeightProperty().bind(box.heightProperty().divide(8));
//        box.setSpacing(10);

//        pane.setScaleX(2);
//        double scale = 0.5;
//        pane.setScaleX(scale);
        root.setCenter(box);
//        root.setBottom(pane);


        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(()->{
            pane.addPoints(200+100*Math.sin(2*Math.PI*1*time/90)+time);
            pane2.addPoints(2000+100*Math.sin(2*Math.PI*1*time/90));
            pane3.addPoints(200.+Math.random()*2);
            pane4.addPoints(200+100*Math.sin(2*Math.PI*1*time/90)+time);
            pane5.addPoints(2000+100*Math.sin(2*Math.PI*1*time/90));
            pane6.addPoints(200.+Math.random()*2);
            pane7.addPoints(200+100*Math.sin(2*Math.PI*1*time/90)+time);
            pane8.addPoints(2000+100*Math.sin(2*Math.PI*1*time/90));

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
