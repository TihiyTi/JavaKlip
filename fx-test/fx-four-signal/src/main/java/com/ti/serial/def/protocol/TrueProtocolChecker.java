package com.ti.serial.def.protocol;

import com.ti.serial.common.protocol.ProtocolCheckable;

import java.util.concurrent.ConcurrentLinkedDeque;

public class TrueProtocolChecker implements ProtocolCheckable {
    @Override
    public boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque) {
        return true;
    }
}
