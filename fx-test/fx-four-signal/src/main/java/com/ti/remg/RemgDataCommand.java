package com.ti.remg;

import com.ti.serial.command.AbstractCommand;
import com.ti.serial.command.AbstractObjectCommand;

import java.nio.ByteBuffer;

public class RemgDataCommand extends AbstractCommand {

    int [] emg = new int[12];
    int [] eit = new int[12];

    @Override
    public AbstractCommand parseByteBuffer(ByteBuffer buffer) {
        buffer.rewind();

        for (int i = 0; i < 12; i++) {
            emg[i] = fromByteBufferLittleEndian(buffer, 1 + i*4);
            eit[i] = fromByteBufferLittleEndian(buffer, 49 + i*4);
        }

        buffer.rewind();
//        hex = HexFormat.of().formatHex(buffer.array());
//        count++;
//        if (37000 < count & count < 37500){
//            System.out.println(" Pack: " + hex);
//        }
        return this;
    }

    @Override
    public ByteBuffer createByteBuffer() {
        return null;
    }

    public static int fromByteBufferLittleEndian(ByteBuffer buffer, int startIndex) {
        int value = 0;
        for (int j = 0; j < 3; j++) {
            value |= (buffer.get(startIndex + j) & 0xff) << (j * 8 + 8);
        }
        return value;
    }
}
