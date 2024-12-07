package com.ti.serial;

import java.util.concurrent.ConcurrentLinkedDeque;

public interface ProtocolCheckable {
    boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque);
}
