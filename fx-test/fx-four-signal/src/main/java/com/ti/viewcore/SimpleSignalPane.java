package com.ti.viewcore;

import com.ti.signals.SignalConsumer;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polyline;

public class SimpleSignalPane<T extends Enum<T>> extends Pane implements SignalConsumer<Number> {
    public T type;

//    Trace ---------------------
    int count = 0;
//    Trace ---------------------

//  View option
    private int max = 2000;
    private int skipRatio = 10;
//  View option

    private Polyline polyline = new Polyline();
    private int pointer = 0;
    private IntegerProperty xpro = new SimpleIntegerProperty(0);
    double scale = 0.5;

    private DoubleProperty minValue = new SimpleDoubleProperty(Double.MAX_VALUE);
    private DoubleProperty maxValue = new SimpleDoubleProperty(Double.MIN_VALUE);

    Bounds bounds = polyline.localToParent(polyline.getBoundsInLocal());

    public SimpleSignalPane(@NamedArg("type") T type){
        super();
        init();
        this.type = type;
        getChildren().add(new Label(type.name()));
    }

    public SimpleSignalPane() {
        super();
        init();

    }

    private void init(){
        this.setStyle("-fx-background-color: rgb(255,252,239)");
        getChildren().add(polyline);

        polyline.boundsInLocalProperty().addListener((observable, oldValue, newValue) -> {
            if(minValue.get() != newValue.getMinY()){
                minValue.setValue(newValue.getMinY());
            }
            if(maxValue.get() != newValue.getMaxY()){
                maxValue.setValue(newValue.getMaxY());
            }
        });

        DoubleProperty deltaValue = new SimpleDoubleProperty();
        deltaValue.bind(maxValue.add(minValue.negate()));
        DoubleProperty scaleY = new SimpleDoubleProperty();
        scaleY.bind(heightProperty().divide(deltaValue));
        polyline.scaleYProperty().bind(scaleY);
//        polyline.translateYProperty().bind(deltaValue.divide(2).multiply(scaleY.add(-1)));
        polyline.translateYProperty().bind(minValue.divide(2).multiply(scaleY.add(1))
                                    .add(maxValue.divide(2).multiply(scaleY.negate().add(1))).negate());


        DoubleProperty scaleX = new SimpleDoubleProperty();
        scaleX.bind(widthProperty().divide(max));
        polyline.scaleXProperty().bind(scaleX);
        polyline.translateXProperty().bind(xpro.divide(2).multiply(scaleX.add(-1)));

        setStyle("-fx-border-color: green");
//        addPoints(10.,20.);

    }

    public void addPoints(Double... points){
        if(!Platform.isFxApplicationThread()){
            Platform.runLater(()->{
                addPointsInnerMethod(points);
            });
        }else{
            addPointsInnerMethod(points);
        }
    }
    private void addPointsInnerMethod(Double... points){
        ObservableList<Double> observableList = polyline.getPoints();
        for (Double point : points) {
//                    if(maxValue.doubleValue() < point + 10){
//                        maxValue.setValue(point + 10);
//                    }
//                    if(minValue.doubleValue() > point-10){
//                        minValue.setValue(point - 10);
//                    }
            if(pointer < max){
                observableList.add((double) pointer);
                xpro.setValue(pointer);
                observableList.add(point);
                pointer++;
            }else {
                observableList.set(pointer%max*2, (double) (pointer%max));
                observableList.set(pointer%max*2+1, point);
                pointer++;
            }
        }
    }

    // TODO: 15.03.2018 Потенциальная проблема производительности: putElement добавляет по одному элементу,
    // каждый раз дергается метод Fx платформы, в
    @Override
    public void putElement(Number element) {
        count++;
        System.out.println( "Print "+ count +" : " + element.toString());
        if(count%skipRatio == 0){
            addPoints(element.doubleValue());
        }
    }
    @Override
    public void putDoubles(Double ... elements){
        addPoints(elements);
    }

}
