package com.ti.serial.lite.implem;

import com.ti.remg.RemgSignalCommand;
import com.ti.serial.SignalParamGetter;
import com.ti.serial.command.AbstractCommand;
import com.ti.serial.command.AbstractSawCommand;
import com.ti.serial.common.controller.SerialControllable;
import com.ti.serial.common.protocol.Protocol;
import com.ti.serial.common.protocol.ProtocolCheckable;
import com.ti.serial.lite.protocol.AbstractProtocolLite;
import com.ti.serial.lite.protocol.CommandSplittableLite;
import com.ti.signals.AdvanceSignalBox;
import com.ti.signals.SignalConsumer;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

public class SawObjectUnit<COMMAND_TYPE, COMMAND_IMPL extends AbstractCommand<COMMAND_TYPE>>
        extends SawUnit<COMMAND_TYPE, COMMAND_IMPL>{

    private SignalConsumer<int[] > signalSaver;

    public SawObjectUnit(ProtocolCheckable protocolChecker, CommandSplittableLite commandSplitter, Supplier<COMMAND_IMPL> supplier, SignalConsumer<int []> signalSaver) {
        super(protocolChecker, commandSplitter, supplier);
        controller = this;
        this.signalSaver = signalSaver;
    }

    @Override
    public void serviceRequest(AbstractCommand<COMMAND_TYPE> abstractCommand) {

//        signalSaver.putElement(((RemgSignalCommand)abstractCommand).getDataArray());
        signalSaver.putElement(new int[] {1,1,2,3,4});

        box.listOfType.forEach(x-> {
            int data = ((AbstractSawCommand)abstractCommand).getData((Enum) x);
            // TODO: 18.03.2018 вынести фильтрацию в AdvanceSignalBox, добавить еще один list помимо listOfType
            if(((SignalParamGetter)x).isExternal()){
                box.addToQueue((Enum)x, data);
            }
        });
    }
}
