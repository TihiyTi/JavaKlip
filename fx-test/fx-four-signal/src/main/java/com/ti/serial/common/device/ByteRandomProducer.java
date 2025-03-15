package com.ti.serial.common.device;

import com.ti.serial.common.protocol.Protocol;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ByteRandomProducer implements DeviceInterface {

    private final ConcurrentLinkedDeque<Byte> deque = new ConcurrentLinkedDeque<>();
    private Protocol<?, ?> protocol;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private volatile boolean isStopped = false;

    @Override
    public void sendDataArray(ByteBuffer sendBuffer) {
        System.out.println(Arrays.toString(sendBuffer.array()));
    }

    @Override
    public <RESPONSE, REQUEST> void setProtocol(Protocol<RESPONSE, REQUEST> protocol) {
        this.protocol = protocol;
        protocol.setDevice(this);
    }

    public void startProducer() {
        executorService.scheduleWithFixedDelay(() -> {
            if (deque.size() < 100) { // Ограничение размера очереди
                byte randomByte = (byte) (Math.random() * 256 - 128);
                deque.add(randomByte);
                System.out.println("Added byte: " + String.format("0x%02X", randomByte));
            } else {
                stopProducer();
            }
        }, 0L, 100, TimeUnit.MILLISECONDS);
    }

    public void stopProducer() {
        executorService.shutdown();
        isStopped = true;
        System.out.println("Producer stopped as queue is full. Queue contents: " + formatQueueContents());
    }

    private String formatQueueContents() {
        StringBuilder sb = new StringBuilder("[");
        for (Byte b : deque) {
            sb.append(String.format("0x%02X", b)).append(", ");
        }
        if (!deque.isEmpty()) {
            sb.setLength(sb.length() - 2); // Удаляем последнюю запятую и пробел
        }
        sb.append("]");
        return sb.toString();
    }

    public Byte pollByte() {
        return deque.poll();
    }

    public boolean isStopped() {
        return isStopped;
    }
}
