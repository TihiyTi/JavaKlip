package com.ti.serial.device;

import com.ti.serial.protocol.Protocol;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ByteProducer  implements DeviceInterface {

    private Protocol protocol;

    @Override
    public void sendDataArray(ByteBuffer sendBuffer) {
        System.out.println(Arrays.toString(sendBuffer.array()));
    }

    @Override
    public <RESPONSE, REQUEST> void setProtocol(Protocol<RESPONSE, REQUEST> protocol) {
        this.protocol = protocol;
        protocol.setDevice(this);
    }

    public void startProducer(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(()->{
//            protocol.parse(deque);
        }, 0L, 100, TimeUnit.MILLISECONDS);
    }

}
