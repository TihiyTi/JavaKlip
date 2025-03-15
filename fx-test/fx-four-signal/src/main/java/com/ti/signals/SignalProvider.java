package com.ti.signals;

public interface SignalProvider<OUT>{
    void addConsumer(SignalConsumer<OUT> consumer);
}
