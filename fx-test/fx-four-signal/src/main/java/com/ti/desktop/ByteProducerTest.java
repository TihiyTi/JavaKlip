package com.ti.desktop;

import com.ti.serial.common.device.ByteRandomProducer;

public class ByteProducerTest{
    public static void main(String[] args) {

        ByteRandomProducer producer = new ByteRandomProducer();
        producer.startProducer();

        while (!producer.isStopped()){}

    }
}
