package com.ti.desktop;

import com.ti.serial.device.ByteRandomProducer;
import com.ti.serial.device.DeviceInterface;
import com.ti.serial.device.FileByteProducer;
import com.ti.serial.implem.ByteController;
import com.ti.serial.implem.ByteFlowProtocol;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.Objects;
import java.util.stream.Collectors;


public class FileByteProducerTest {

    private static final String TEST_FILE = "testFile.bin";

    public static void main(String[] args) throws URISyntaxException {

        Path file = Paths.get(Objects.requireNonNull(FileByteProducerTest.class.getResource(TEST_FILE)).toURI());
        FileByteProducer producer = new FileByteProducer(file, FileByteProducer.ReadType.READ_ALL);

        ByteController inputController = new ByteController();
        ByteFlowProtocol inputProtocol =  new ByteFlowProtocol();
        inputController.addProtocol(inputProtocol);
        inputProtocol.addController(inputController);

        inputProtocol.setSender(producer);
        producer.setProtocol(inputProtocol);

        producer.read();

        HexFormat hexFormat = HexFormat.of();

        System.out.println(Arrays.toString(inputController.testQueue.toArray()));
        System.out.println(inputController.testQueue.stream().map(b -> hexFormat.toHexDigits((byte)(b & 0xFF))) // Преобразуем Byte в int и в HEX
                .collect(Collectors.joining(" ")));

    }
}
