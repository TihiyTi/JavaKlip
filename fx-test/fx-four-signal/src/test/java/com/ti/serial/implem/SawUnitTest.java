package com.ti.serial.implem;

import com.ti.serial.lite.protocol.SawProtocolParser;
import com.ti.serial.command.TestSawCommand;
import com.ti.serial.command.TestSignalType;
import com.ti.serial.lite.implem.SawUnit;
import com.ti.signals.AdvanceSignalBox;
import com.ti.signals.FinalSaveFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedDeque;

class SawUnitTest {
//    private static final String TEST_FILE = "MechaBin2.bin";
//    @Disabled
    @Test
    public void SawUnitExample(){
        SawProtocolParser parser = new SawProtocolParser(8);
        SawUnit<TestSawCommand> unit = new SawUnit<>(parser,parser, TestSawCommand::new);
        AdvanceSignalBox<TestSignalType> box = new AdvanceSignalBox<>(TestSignalType.class);
        unit.setBox(box);

        ConcurrentLinkedDeque<Byte> deque = new ConcurrentLinkedDeque<>();
        deque.addAll(Arrays.asList(
                (byte)0, (byte)0,
                (byte)0, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0,(byte)0, (byte)0,(byte)4,
                (byte)1, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0,(byte)0, (byte)0,(byte)5,
                (byte)2, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0,(byte)0, (byte)0,(byte)4,
                (byte)3, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0,(byte)0, (byte)0,(byte)4,
                (byte)4, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0,(byte)0, (byte)0,(byte)4,
                (byte)5, (byte)0, (byte)0, (byte)0, (byte)3, (byte)0,(byte)0
        ));

        FinalSaveFilter data1Saver = new FinalSaveFilter();
        FinalSaveFilter data2Saver = new FinalSaveFilter();
        box.addTypedConsumer(data1Saver, TestSignalType.DATA1);
        box.addTypedConsumer(data2Saver, TestSignalType.DATA2);

        unit.parse(deque);
        for (int i = 0; i < 1000000; i++) {}
        Assertions.assertArrayEquals(
                data1Saver.getResult().stream().mapToDouble(Number::doubleValue).toArray(),
                new double[]{3,3,3,3,3}, 0.001);
        Assertions.assertArrayEquals(
                data2Saver.getResult().stream().mapToDouble(Number::doubleValue).toArray(),
                new double[]{4,5,4,4,4}, 0.001);
    }
}