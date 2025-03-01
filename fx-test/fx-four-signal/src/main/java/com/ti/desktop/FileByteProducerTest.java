package com.ti.desktop;

import com.ti.serial.common.device.FileByteProducer;
import com.ti.serial.def.controller.ByteController;
import com.ti.serial.def.protocol.ByteFlowProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.Objects;
import java.util.stream.Collectors;


public class FileByteProducerTest {
    private static final Logger logger = LoggerFactory.getLogger(FileByteProducerTest.class);
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

        logger.info("File save to: {}", (new File(".")).getAbsolutePath());
        logger.trace(Arrays.toString(inputController.testQueue.toArray()));
        System.out.println(Arrays.toString(inputController.testQueue.toArray()));

        System.out.println(inputController.testQueue.stream().map(b -> hexFormat.toHexDigits((byte)(b & 0xFF))) // Преобразуем Byte в int и в HEX
                .collect(Collectors.joining(" ")));

    }
}
