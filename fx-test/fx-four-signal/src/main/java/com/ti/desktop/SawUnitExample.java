package com.ti.desktop;

import com.ti.dspview.FxGraphicBuilder;
import com.ti.dspview.FxGraphicBuilder2;
import com.ti.serial.checkers.SawProtocolParser;
import com.ti.serial.command.SingleSignalCommand;
import com.ti.serial.command.SingleSignalType;
import com.ti.serial.device.FileByteProducer;
import com.ti.serial.implem.SawUnit;
import com.ti.signals.AdvanceSignalBox;
import com.ti.signals.FinalSaveFilter;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

class SawUnitExample {
//    private static final String TEST_FILE = "Briko-MAX-12.bin";
    private static final String TEST_FILE = "2025-02-01 01-05-52 095 Briko-MAX-12.bin";
//    private static final String TEST_FILE = "2025-02-01 01-01-17 379 Briko-MAX-12.bin";

    public static void main(String[] args) throws URISyntaxException {


        SawProtocolParser parser = new SawProtocolParser(151);
        SawUnit<SingleSignalCommand> unit = new SawUnit<>(parser,parser, SingleSignalCommand::new);
        AdvanceSignalBox<SingleSignalType> box = new AdvanceSignalBox<>(SingleSignalType.class);
        unit.setBox(box);

        FinalSaveFilter data1Saver = new FinalSaveFilter();
        box.addTypedConsumer(data1Saver, SingleSignalType.SINGLE);

        Path file = Paths.get(Objects.requireNonNull(FileByteProducerTest.class.getResource(TEST_FILE)).toURI());
        FileByteProducer producer = new FileByteProducer(file, FileByteProducer.ReadType.READ_ALL);
        producer.liteProtocol = true;
        producer.setProtocol(unit);
        producer.read();
        System.out.println("Read bytes: " + producer.getTotalReadedByte());
//        ConcurrentLinkedDeque<Byte> deque = new ConcurrentLinkedDeque<>(Arrays.asList(
//                (byte) 0, (byte) 0,
//                (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 4,
//                (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 5,
//                (byte) 2, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 4,
//                (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 4,
//                (byte) 4, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 4,
//                (byte) 5, (byte) 0, (byte) 0, (byte) 0, (byte) 3, (byte) 0, (byte) 0
//        ));

        List<Number> list = data1Saver.getResult().stream().toList();
        System.out.println("Ok " + parser.numOfOkParse);
        System.out.println("Crash " + parser.numOfCrashParse);
//        System.out.println(Arrays.toString(list.stream().mapToInt(Number::intValue).toArray()));

        FxGraphicBuilder.initFXContextTrue();
        FxGraphicBuilder.listLinePlot(list.subList(0, 1000));
//        FxGraphicBuilder.listLinePlot(list.subList(1001, 2000));

//        System.exit(0);
    }
}