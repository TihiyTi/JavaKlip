package com.ti.serial.common.device;

import com.ti.serial.common.protocol.Protocol;

import java.nio.ByteBuffer;

public interface DeviceInterface {
    void sendDataArray(ByteBuffer sendBuffer);

    // TODO: 15.04.2018 реализовать во всех device-классах и удалить default определение
    default void sendDataArray(byte[] sendArray){
        System.out.println("Unsupported");
    }

    <RESPONSE, REQUEST> void setProtocol(Protocol<RESPONSE, REQUEST> protocol);
}
