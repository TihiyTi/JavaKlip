package com.ti.viewcore;


import com.ti.signals.SignalConsumer;

public class RealTimeChartConsumer extends RealTimeChart implements SignalConsumer<Number> {


    public RealTimeChartConsumer() {
    }

    public RealTimeChartConsumer(int maxDataPoint, boolean skip10) {
        super(maxDataPoint, skip10);
    }

    @Override
    public void putElement(Number element) {
        addElement(element.doubleValue());
    }
}
