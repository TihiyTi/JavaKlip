package com.ti.viewcore;


import com.ti.signals.SignalConsumer;

import java.util.ArrayList;
import java.util.List;

public class RealTimeMultiChartConsumer extends RealTimeMultiChart {

    public List<SignalConsumer<Number>> listeners = new ArrayList<>();

    public RealTimeMultiChartConsumer(int seriesCount) {
        super(seriesCount);
    }

    public RealTimeMultiChartConsumer(int seriesCount, int maxDataPoint, boolean skip10, String baseSignlName) {
        super(seriesCount, maxDataPoint, skip10, baseSignlName);

        for (int i = 0; i < seriesCount; i++) {
            var name = "" + i;
            listeners.add(element -> addElement(element.doubleValue(), name));
        }
    }

    public RealTimeMultiChartConsumer(int seriesCount, int maxDataPoint, boolean skip10){
        super(seriesCount, maxDataPoint, skip10);
        for (int i = 0; i < seriesCount; i++) {
            var name = ""+i;
            listeners.add(element -> addElement(element.doubleValue(), name));
        }
    }

    //    public RealTimeMultiChartConsumer() {
//    }
//
//    public RealTimeMultiChartConsumer(int maxDataPoint, boolean skip10, ) {
//        super(maxDataPoint, skip10, );
//    }
//
//    todo Этот метод будет работаь неправильно
//    @Override
    public void putElement(Number element, String type) {
        addElement(element.doubleValue(), type);
    }

    //todo Этот метод должен быть вынесен в интерфейс SignalConsumer  или в новый интерфейс MultiSigalConsumer
    public void putElements(Double... values){
        addElements(values);
    }
}
