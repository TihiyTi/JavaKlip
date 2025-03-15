package com.ti.serial.command;

import com.ti.serial.SignalParamGetter;

public enum SingleSignalType implements SignalParamGetter {
    SINGLE(true);

    final boolean external;

    SingleSignalType(boolean b) {
        external = b;
    }

    @Override
    public boolean isExternal(){
        return external;
    }
}
