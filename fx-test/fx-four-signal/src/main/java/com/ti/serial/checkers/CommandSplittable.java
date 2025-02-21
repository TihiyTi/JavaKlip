package com.ti.serial.checkers;


import com.ti.serial.protocol.AbstractProtocol;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public interface CommandSplittable<REQUEST> {
    void setCommandSizeMap(Map<Byte, Integer> map);
    void parseQueue(ConcurrentLinkedDeque<Byte> deque);
    ByteBuffer getSyncSequence();
    void setProtocol(AbstractProtocol protocol);
}
