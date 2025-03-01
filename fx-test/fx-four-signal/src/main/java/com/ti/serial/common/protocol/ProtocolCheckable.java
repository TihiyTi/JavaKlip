package com.ti.serial.common.protocol;

import java.util.concurrent.ConcurrentLinkedDeque;

public interface ProtocolCheckable {
    boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque);
}
