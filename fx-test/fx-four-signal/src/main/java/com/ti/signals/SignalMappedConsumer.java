package com.ti.signals;

public interface SignalMappedConsumer<IN>{
    SignalConsumer<IN> getSignalConsumers();
//    void putElement(IN element, String mapName);
    default void putDoubles(Double ... elements){}
}
