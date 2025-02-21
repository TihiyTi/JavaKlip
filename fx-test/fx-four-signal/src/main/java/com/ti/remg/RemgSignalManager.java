package com.ti.remg;

import com.ti.signals.AbstractSignalManager;
import com.ti.signals.SignalConsumer;
import com.ti.viewcore.AbstractViewSignalConsumer;

import java.util.Map;

public class RemgSignalManager  extends AbstractSignalManager<RemgSignalType> {

    public RemgSignalManager() {
        super(RemgSignalType.class);

    }

    public void linkBoxToView(AbstractViewSignalConsumer<RemgSignalType> viewSignalConsumer){
        Map<RemgSignalType, SignalConsumer> map = viewSignalConsumer.getMapOfSignalConsumer();
        map.forEach((key, value) -> box.addTypedConsumer(value, key));
    }
}
