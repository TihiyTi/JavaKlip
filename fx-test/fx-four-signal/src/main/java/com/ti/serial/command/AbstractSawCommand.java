package com.ti.serial.command;

//todo Удалить AbstractSawCommand
public abstract class AbstractSawCommand<T  extends Enum<T>> extends AbstractCommand<T> {
    public abstract int getData(T type);
}
