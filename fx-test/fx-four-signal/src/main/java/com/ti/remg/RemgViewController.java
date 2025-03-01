package com.ti.remg;

import com.ti.viewcore.AbstractViewSignalConsumer;
import com.ti.viewcore.RealTimeMultiChartConsumer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class RemgViewController extends AbstractViewSignalConsumer<RemgSignalType> implements Initializable {
    @FXML
    private BorderPane remgBorder;

    @FXML
    private BorderPane remgControlPanel;
    @FXML
    private RemgControlController remgControlPanelController;

    @FXML
    public VBox chartHBox;

    List<RealTimeMultiChartConsumer> realTimeMultiChartConsumers;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("RemgViewController initialize");
        addChartsToBox();
    }

    public void addChartsToBox(){
        realTimeMultiChartConsumers = Stream.generate(()-> new RealTimeMultiChartConsumer(12, 2000, false))
                .limit(3)
                .toList();
        realTimeMultiChartConsumers.forEach(consumer -> chartHBox.getChildren().add(consumer.getChart()));

        addTypedConsumerToMap(RemgSignalType.EMG1, realTimeMultiChartConsumers.get(0).listeners.get(0));
        addTypedConsumerToMap(RemgSignalType.EMG2, realTimeMultiChartConsumers.get(0).listeners.get(1));
        addTypedConsumerToMap(RemgSignalType.EMG3, realTimeMultiChartConsumers.get(0).listeners.get(2));
        addTypedConsumerToMap(RemgSignalType.EMG4, realTimeMultiChartConsumers.get(0).listeners.get(3));
        addTypedConsumerToMap(RemgSignalType.EMG5, realTimeMultiChartConsumers.get(0).listeners.get(4));
        addTypedConsumerToMap(RemgSignalType.EMG6, realTimeMultiChartConsumers.get(0).listeners.get(5));
        addTypedConsumerToMap(RemgSignalType.EMG7, realTimeMultiChartConsumers.get(0).listeners.get(6));
        addTypedConsumerToMap(RemgSignalType.EMG8, realTimeMultiChartConsumers.get(0).listeners.get(7));
        addTypedConsumerToMap(RemgSignalType.EMG9, realTimeMultiChartConsumers.get(0).listeners.get(8));
        addTypedConsumerToMap(RemgSignalType.EMG10, realTimeMultiChartConsumers.get(0).listeners.get(9));
        addTypedConsumerToMap(RemgSignalType.EMG11, realTimeMultiChartConsumers.get(0).listeners.get(10));
        addTypedConsumerToMap(RemgSignalType.EMG12, realTimeMultiChartConsumers.get(0).listeners.get(11));

//        addTypedConsumerToMap(RemgSignalType.EIT1, realTimeMultiChartConsumers.get(0).listeners.get(1));
//        addTypedConsumerToMap(RemgSignalType.EIT2, realTimeMultiChartConsumers.get(1).listeners.get(1));
//        addTypedConsumerToMap(RemgSignalType.EIT3, realTimeMultiChartConsumers.get(2).listeners.get(1));
//        addTypedConsumerToMap(RemgSignalType.EIT4, realTimeMultiChartConsumers.get(3).listeners.get(1));
//        addTypedConsumerToMap(RemgSignalType.EIT5, realTimeMultiChartConsumers.get(4).listeners.get(1));
//        addTypedConsumerToMap(RemgSignalType.EIT6, realTimeMultiChartConsumers.get(5).listeners.get(1));
//        addTypedConsumerToMap(RemgSignalType.EIT7, realTimeMultiChartConsumers.get(6).listeners.get(1));
//        addTypedConsumerToMap(RemgSignalType.EIT8, realTimeMultiChartConsumers.get(7).listeners.get(1));
//        addTypedConsumerToMap(RemgSignalType.EIT9, realTimeMultiChartConsumers.get(8).listeners.get(1));
//        addTypedConsumerToMap(RemgSignalType.EIT10, realTimeMultiChartConsumers.get(9).listeners.get(1));
//        addTypedConsumerToMap(RemgSignalType.EIT11, realTimeMultiChartConsumers.get(10).listeners.get(1));
//        addTypedConsumerToMap(RemgSignalType.EIT12, realTimeMultiChartConsumers.get(11).listeners.get(1));

        //        addTypedConsumerToMap(RemgSignalType.EMG1, realTimeMultiChartConsumers.get(0).listeners.get(0));
//        addTypedConsumerToMap(RemgSignalType.EMG1, realTimeMultiChartConsumers.get(0).listeners.get(0));
//        addTypedConsumerToMap(RemgSignalType.EMG1, realTimeMultiChartConsumers.get(0).listeners.get(0));
    }

    public RemgControlController getRemgControlController() {
        return remgControlPanelController;
    }
}
