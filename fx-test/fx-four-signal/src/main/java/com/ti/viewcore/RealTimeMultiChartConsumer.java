package com.ti.viewcore;


import com.ti.signals.SignalConsumer;

import java.util.ArrayList;
import java.util.List;

public class RealTimeMultiChartConsumer extends RealTimeMultiChart {

    public List<SignalConsumer<Number>> listeners = new ArrayList<>();

    public RealTimeMultiChartConsumer(int seriesCount) {
        super(seriesCount);
    }

    public RealTimeMultiChartConsumer(int seriesCount, int maxDataPoint, boolean skip10) {
        super(seriesCount, maxDataPoint, skip10);

        listeners.add(element -> addElement(element.doubleValue(), "0"));
        listeners.add(element -> addElement(element.doubleValue(), "1"));
        listeners.add(element -> addElement(element.doubleValue(), "2"));
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
