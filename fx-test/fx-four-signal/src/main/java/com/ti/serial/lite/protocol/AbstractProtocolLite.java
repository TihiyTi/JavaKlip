package com.ti.serial.lite.protocol;

import com.ti.serial.common.protocol.Protocol;
import com.ti.serial.common.device.DeviceInterface;
import com.ti.serial.common.controller.SerialControllable;
import com.ti.serial.common.protocol.ProtocolCheckable;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public abstract class AbstractProtocolLite<RESPONSE, REQUEST> implements Protocol<RESPONSE, REQUEST> {
//    private static final Logger LOG = LogManager.getLogger("TiSerialServiceLogger");

    private final ProtocolCheckable protocolChecker;
    private final CommandSplittableLite commandSplitter;

    protected SerialControllable<RESPONSE, REQUEST> controller;
    private DeviceInterface device;

    int protocolToControllerRequestCounter = 0;

    public AbstractProtocolLite(ProtocolCheckable protocolChecker, CommandSplittableLite commandSplitter) {
        this.protocolChecker = protocolChecker;
        this.commandSplitter = commandSplitter;
    }

    public void addController(SerialControllable<RESPONSE, REQUEST> controller){
        this.controller = controller;
    }

    @Override
    public void parse(ConcurrentLinkedDeque<Byte> deque){
        if(protocolChecker.checkProtocol(deque)){
            List<ByteBuffer> frameQueue = commandSplitter.parseQueue(deque);
            if(!frameQueue.isEmpty()){
                List<REQUEST> listOfRequest = frameQueue.stream().map(this::createByteToRequest).toList();
                listOfRequest.forEach(x->controller.serviceRequest(x));


//-----------------TRACE block  -----------------
                protocolToControllerRequestCounter++;
//                if(protocolToControllerRequestCounter % 10 == 0){
//                    LOG.trace("protocolToControllerRequestCounter = "+protocolToControllerRequestCounter);
//                }
//-----------------TRACE block  -----------------
            }
        }
    }

    @Override
    public void sendResponse(RESPONSE response){
        ByteBuffer bufferWithOutHead = createResponseToByte(response);
        bufferWithOutHead.rewind();
        ByteBuffer buffer = commandSplitter.appendSyncToBuffer(bufferWithOutHead);
        device.sendDataArray(buffer);
    }

    @Override
    public void sendWithOutProtocol(ByteBuffer buffer){
        device.sendDataArray(buffer);
    }

    @Override
    public void setDevice(DeviceInterface device){this.device = device;}



}
