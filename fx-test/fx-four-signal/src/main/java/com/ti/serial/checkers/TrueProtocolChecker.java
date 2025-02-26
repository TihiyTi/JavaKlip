package com.ti.serial.checkers;

import com.ti.serial.protocol.ProtocolCheckable;

import java.util.concurrent.ConcurrentLinkedDeque;

public class TrueProtocolChecker implements ProtocolCheckable {
    @Override
    public boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque) {
        return true;
    }
}
