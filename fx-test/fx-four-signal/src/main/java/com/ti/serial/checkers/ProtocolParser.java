package com.ti.serial.checkers;


import com.ti.serial.protocol.CommandSplittableLite;
import com.ti.serial.protocol.ProtocolCheckable;

public interface ProtocolParser extends ProtocolCheckable, CommandSplittableLite {
}
