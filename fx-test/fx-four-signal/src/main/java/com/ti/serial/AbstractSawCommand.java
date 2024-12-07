package com.ti.serial;

import com.ti.serial.AbstractCommand;

public abstract class AbstractSawCommand<T  extends Enum<T>> extends AbstractCommand {
    public abstract int getData(T type);
}
