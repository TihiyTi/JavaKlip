package com.ti.remg;

import com.ti.serial.SignalParamGetter;

public enum RemgSignalType implements SignalParamGetter {
    EMG1(true), EIT1(true),
    EMG2(true), EIT2(true),
    EMG3(true), EIT3(true),
    EMG4(true), EIT4(true),
    EMG5(true), EIT5(true),
    EMG6(true), EIT6(true),
    EMG7(true), EIT7(true),
    EMG8(true), EIT8(true),
    EMG9(true), EIT9(true),
    EMG10(true), EIT10(true),
    EMG11(true), EIT11(true),
    EMG12(true), EIT12(true);

    final boolean external;

    RemgSignalType(boolean b) {
        external = b;
    }

    @Override
    public boolean isExternal(){
        return external;
    }



}
