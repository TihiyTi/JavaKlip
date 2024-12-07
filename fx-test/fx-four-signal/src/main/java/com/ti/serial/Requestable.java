package com.ti.serial;

import java.nio.ByteBuffer;

public interface Requestable {
    AbstractCommand parseByteBuffer(ByteBuffer buffer);
}
