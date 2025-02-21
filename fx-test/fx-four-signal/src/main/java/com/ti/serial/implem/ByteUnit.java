package com.ti.serial.implem;


import com.ti.serial.SerialControllable;
import com.ti.serial.protocol.AbstractProtocolLite;
import com.ti.serial.protocol.Protocol;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ByteUnit
        extends AbstractProtocolLite<Byte,Byte>
        implements SerialControllable<Byte,Byte> {

    public ByteUnit() {
        super(null, null);
    }

    @Override
    public void parse(ConcurrentLinkedDeque<Byte> deque){
        System.out.println("Unsupported");
    }
    @Override
    public ByteBuffer createResponseToByte(Byte aByte) {
        return null;
    }
    @Override
    public Byte createByteToRequest(ByteBuffer buffer) {
        return null;
    }
    @Override
    public void serviceRequest(Byte aByte) {

    }
    @Override
    public void toServiceResponse(Byte aByte) {

    }
    @Override
    public void addProtocol(Protocol<Byte, Byte> protocol) {

    }

    // TODO: 15.04.2018 добавить цепочку отправки единичных байтов
    public void send(Byte b){
        sendWithOutProtocol(ByteBuffer.wrap(new byte[]{b}));
    }
}
