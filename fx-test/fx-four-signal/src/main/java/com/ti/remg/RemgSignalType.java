package com.ti.remg;

import com.ti.serial.SignalParamGetter;

public enum RemgSignalType implements SignalParamGetter {
    EMG1(true), EIT1(true), MTG1(true),
    EMG2(true), EIT2(true), MTG2(true),
    EMG3(true), EIT3(true), MTG3(true),
    EMG4(true), EIT4(true), MTG4(true),
    EMG5(true), EIT5(true), MTG5(true),
    EMG6(true), EIT6(true), MTG6(true),
    EMG7(true), EIT7(true), MTG7(true),
    EMG8(true), EIT8(true), MTG8(true),
    EMG9(true), EIT9(true), MTG9(true),
    EMG10(true), EIT10(true), MTG10(true),
    EMG11(true), EIT11(true), MTG11(true),
    EMG12(true), EIT12(true), MTG12(true),

    ACCX(true), ACCY(true), ACCZ(true),
    GIROX(true), GIROY(true), GIROZ(true);

    final boolean external;

    RemgSignalType(boolean b) {
        external = b;
    }

    @Override
    public boolean isExternal(){
        return external;
    }



}
