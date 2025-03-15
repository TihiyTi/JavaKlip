package com.ti.serial.lite.implem;

import com.ti.serial.*;
import com.ti.serial.command.AbstractCommand;
import com.ti.serial.command.AbstractSawCommand;
import com.ti.serial.common.controller.SerialControllable;
import com.ti.serial.lite.protocol.AbstractProtocolLite;
import com.ti.serial.lite.protocol.CommandSplittableLite;
import com.ti.serial.common.protocol.ProtocolCheckable;
import com.ti.signals.AdvanceSignalBox;
import com.ti.serial.common.protocol.Protocol;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

public class SawUnit<COMMAND_TYPE, COMMAND_IMPL extends AbstractCommand<COMMAND_TYPE>>
        extends AbstractProtocolLite<AbstractCommand<COMMAND_TYPE>, AbstractCommand<COMMAND_TYPE>>
        implements SerialControllable<AbstractCommand<COMMAND_TYPE>, AbstractCommand<COMMAND_TYPE>> {
    protected Supplier<COMMAND_IMPL> supplier;

    protected AdvanceSignalBox box;

    public SawUnit(ProtocolCheckable protocolChecker, CommandSplittableLite commandSplitter, Supplier<COMMAND_IMPL> supplier) {
        super(protocolChecker, commandSplitter);
        this.supplier = supplier;
        controller = this;
    }

    @Override
    public ByteBuffer createResponseToByte(AbstractCommand command) {
        return command.createByteBuffer();
    }

    @Override
    public AbstractCommand createByteToRequest(ByteBuffer buffer) {
        return supplier.get().parseByteBuffer(buffer);
    }

    @Override
    public void serviceRequest(AbstractCommand<COMMAND_TYPE> abstractCommand) {
        box.listOfType.forEach(x-> {
            int data = ((AbstractSawCommand)abstractCommand).getData((Enum) x);
            // TODO: 18.03.2018 вынести фильтрацию в AdvanceSignalBox, добавить еще один list помимо listOfType
            if(((SignalParamGetter)x).isExternal()){
                box.addToQueue((Enum)x, data);
            }
        });
    }

    @Override
    public void toServiceResponse(AbstractCommand abstractCommand) {
        sendResponse(abstractCommand);
    }

    @Override
    public void addProtocol(Protocol<AbstractCommand<COMMAND_TYPE>, AbstractCommand<COMMAND_TYPE>> protocol) {
        System.out.println("Dont apply to UNIT, it's already Controller and Protocol");
    }

    public void setBox(AdvanceSignalBox box) {
        this.box = box;
    }

}
