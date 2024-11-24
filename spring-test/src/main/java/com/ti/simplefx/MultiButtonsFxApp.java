package com.ti.simplefx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ti")
public class MultiButtonsFxApp extends Application {

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    Rectangle rectangle;
    Polyline polyline;

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Test");
        VBox root = new VBox(30);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        BorderPane border = new BorderPane();
        border.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 1;"
                + "-fx-border-color: yellow;");

        HBox hbox = new HBox(30);
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 1;"
                + "-fx-border-color: red");
        VBox.setVgrow(hbox, Priority.ALWAYS);

        VBox vbox = new VBox(10);
        vbox.setMaxWidth(Double.MAX_VALUE);
        vbox.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 1;"
                + "-fx-border-color: black");
        HBox.setHgrow(vbox, Priority.ALWAYS);


        VBox vbox2 = new VBox(10);
        vbox2.setMaxWidth(Double.MAX_VALUE);
        vbox2.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 1;"
                + "-fx-border-color: blue");
        HBox.setHgrow(vbox2, Priority.ALWAYS);
        VBox.setVgrow(vbox2, Priority.ALWAYS);

        Button bt1 = new Button("1");
        Button bt2 = new Button("I am Button!"); // got it? ;)
        Button bt3 = new Button("I am a veeeeeeeery long button");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToHeight(true);

        polyline = new Polyline(10,20,20,100,30,10,40,200,50,14,60, 12, 500,100) ;
        polyline.setTranslateY(100);
        scrollPane.setContent(polyline);

        ScrollBar bar = new ScrollBar();
        bar.setOpaqueInsets(new Insets(50));

//        vbox.layoutBoundsProperty().addListener(new ChangeListener<Bounds>() {
//            @Override
//            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
//                System.out.println("Rect "+newValue.getWidth() + " "+ newValue.getHeight());
//                polyline.setClip(new Rectangle(newValue.getWidth() , newValue.getHeight()));
//            }
//        });

        Button bt4 = new Button("1");
        bt4.setMaxWidth(Double.MAX_VALUE);
        Button bt5 = new Button("I am Button!");
        bt5.setMaxWidth(Double.MAX_VALUE);
        Button bt6 = new Button("I am a veeeeeeeery long button");
        bt6.setMaxWidth(Double.MAX_VALUE);

        border.setBottom(bar);
        border.setCenter(vbox);

        vbox.getChildren().addAll(bt1, bt2, bt3);
        vbox2.getChildren().addAll(bt4, bt5, bt6, scrollPane);
        hbox.getChildren().addAll(border, vbox2);

        HBox hbox2 = new HBox(20);
        hbox2.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 1;"
                + "-fx-border-color: black");
//        hbox2.setAlignment(Pos.CENTER);
        hbox2.setPrefHeight(50);
        VBox.setVgrow(hbox2, Priority.ALWAYS);


        for (int i = 0; i < 10; i++)
        {
            Button bt = new Button(String.valueOf(i));
            bt.setMaxWidth(Double.MAX_VALUE);
            bt.setMaxHeight(Double.MAX_VALUE);
            hbox2.getChildren().add(bt);
        }

        root.getChildren().addAll(hbox, hbox2);
        Scene scene = new Scene(root, 500, 250);

        stage.setTitle("Sizing Buttons equally inside a VBox or HBox");
        stage.setScene(scene);
        stage.show();
    }
}
