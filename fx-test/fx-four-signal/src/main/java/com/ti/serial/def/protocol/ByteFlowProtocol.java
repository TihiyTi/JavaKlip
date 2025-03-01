package com.ti.serial.def.protocol;


import com.ti.serial.def.AbstractProtocol;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ByteFlowProtocol extends AbstractProtocol<ByteBuffer,ByteBuffer> {

    @Override
    public ByteBuffer createResponseToByte(ByteBuffer buffer) {
        return buffer;
    }

    @Override
    public ByteBuffer createByteToRequest(ByteBuffer buffer) {
        return buffer;
    }

    @Override
    public boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque){
        return true;
    }
    @Override
    public void parseQueue(ConcurrentLinkedDeque<Byte> deque){
        ByteBuffer buffer = ByteBuffer.allocate(deque.size());
        while (!deque.isEmpty()) {
            buffer.put(deque.poll());
        }
        System.out.println("FromUART: " + Arrays.toString(buffer.array()));
        upByteBuffer(buffer);
    }

}
