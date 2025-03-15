package com.ti.serial.command;

import java.nio.ByteBuffer;

public interface Requestable {
    AbstractCommand parseByteBuffer(ByteBuffer buffer);
}
