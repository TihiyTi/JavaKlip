package com.ti.serial.common.protocol;


import com.ti.serial.def.AbstractProtocol;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

public interface CommandSplittable<REQUEST> {
    void setCommandSizeMap(Map<Byte, Integer> map);
    void parseQueue(ConcurrentLinkedDeque<Byte> deque);
    ByteBuffer getSyncSequence();
    void setProtocol(AbstractProtocol protocol);
}
