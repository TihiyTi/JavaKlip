package com.ti.serial.command;

import java.nio.ByteBuffer;

public interface Responsable {
    ByteBuffer createByteBuffer();
}
