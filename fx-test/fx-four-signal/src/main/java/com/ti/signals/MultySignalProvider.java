package com.ti.signals;

public interface MultySignalProvider<TYPE extends Enum<TYPE>> {
    void addTypedConsumer(SignalConsumer consumer, TYPE type);
    void addToQueue(Enum type, Number element);
}
