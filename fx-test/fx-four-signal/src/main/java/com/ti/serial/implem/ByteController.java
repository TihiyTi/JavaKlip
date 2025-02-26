package com.ti.serial.implem;


import com.ti.serial.protocol.AbstractSerialController;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ByteController extends AbstractSerialController<ByteBuffer,ByteBuffer> {
    public BlockingQueue<Byte> testQueue = new LinkedBlockingQueue<>();

    @Override
    public void serviceRequest(ByteBuffer command) {
        command.rewind();
        System.out.println("Add:" + StandardCharsets.UTF_8.decode(command));
        for (byte b : command.array()) {
            testQueue.add(b);
        }
    }

    public void send(byte[] array){
        ByteBuffer buffer = ByteBuffer.wrap(array);
        toServiceResponse(buffer);
    }
    public void send(ByteBuffer buffer){
        toServiceResponse(buffer);
    }
}
