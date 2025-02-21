package com.ti.viewcore;


import com.ti.signals.SignalConsumer;

import java.util.HashMap;
import java.util.Map;

abstract public class AbstractViewSignalConsumer<T extends Enum<T>> {
    private final Map<T, SignalConsumer> mapOfSignalConsumer = new HashMap<>();

    public Map<T, SignalConsumer> getMapOfSignalConsumer() {
        return mapOfSignalConsumer;
    }

    protected void addTypedConsumerToMap(T type, SignalConsumer consumer){
        mapOfSignalConsumer.put(type, consumer);
    }
}
