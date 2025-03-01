package com.ti.serial.def.controller;


import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ByteController extends AbstractSerialController<ByteBuffer,ByteBuffer> {
    public BlockingQueue<Byte> testQueue = new LinkedBlockingQueue<>();

    @Override
    public void serviceRequest(ByteBuffer command) {
        command.rewind();
        System.out.println("Add serviceRequest - "+ command.capacity()+ "  : " + StandardCharsets.UTF_8.decode(command));
//        System.out.println("serviceRequest " + Arrays.toString(command.array()));
        command.rewind();
        for (byte b : command.array()) {
            testQueue.add(b);
        }
    }

    public void send(byte[] array){
        System.out.print("Send:"+StandardCharsets.UTF_8.decode(ByteBuffer.wrap(array)));
//        System.out.println("To send:"+ currentCommand + " -> " + currentCommand.replace("\n", "\\n").replace("\r", "\\r"));

//        System.out.println("send 2 " + Arrays.toString(array));
        ByteBuffer buffer = ByteBuffer.wrap(array);
        toServiceResponse(buffer);
    }
    public void send(ByteBuffer buffer){
        toServiceResponse(buffer);
    }
}
