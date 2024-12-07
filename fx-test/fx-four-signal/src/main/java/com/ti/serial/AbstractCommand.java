package com.ti.serial;

public abstract class AbstractCommand<COMMAND_TYPE> implements Requestable, Responsable{

    public AbstractCommand() {
    }

    protected COMMAND_TYPE type;

    public COMMAND_TYPE is(){
        return type;
    }

    public void debugPrint() {
        System.out.println("Not implemented for SawCommand");
    };
}
