package com.ti.serial.command;

import com.ti.serial.SignalParamGetter;

public enum TestSignalType  implements SignalParamGetter {
    DATA1(true),
    DATA2(true);

    final boolean external;

    TestSignalType(boolean b) {
        external = b;
    }

    @Override
    public boolean isExternal(){
        return external;
    }
}
