package com.ti.serial.def;

import com.ti.PropertiesService;
import com.ti.serial.common.controller.SerialControllable;
import com.ti.serial.common.protocol.CommandSplittable;
import com.ti.serial.def.protocol.OneSynchroByteProtocolChecker;
import com.ti.serial.def.protocol.SawSynchroByteProtocolChecker;
import com.ti.serial.common.protocol.Protocol;
import com.ti.serial.common.device.DeviceInterface;
import com.ti.serial.common.protocol.ProtocolCheckable;


//import javax.xml.bind.DatatypeConverter;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public abstract class AbstractProtocol<RESPONSE, REQUEST> implements Protocol<RESPONSE, REQUEST> {
//    private static final Logger LOG = LogManager.getLogger("serialServiceLogger");
    private ProtocolCheckable protocolChecker = new OneSynchroByteProtocolChecker();
    private CommandSplittable commandSplitter;

    private List<Protocol<RESPONSE, REQUEST>> protocolList = new ArrayList<>(Collections.singletonList(this));

    private DeviceInterface sender;
    protected List<SerialControllable<RESPONSE, REQUEST>> serialControllableList = new ArrayList<>();

    public AbstractProtocol() {
        // TODO: 25.10.2017 удалить автоматическое задание splitter
//        commandSplitter = new CommandSplitter((byte)0xAA);
//        commandSplitter.setProtocol(this);
    }

    public AbstractProtocol(boolean readProperties) {
        if(readProperties){
             String checker = PropertiesService.getGlobalProperty("protocolChecker");
             String dataSize = PropertiesService.getGlobalProperty("dataSize");
             if(checker!=null & dataSize!=null){
                 if(Objects.equals(checker, "sawChecker")){
                     protocolChecker = new SawSynchroByteProtocolChecker(Integer.valueOf(dataSize));
                     commandSplitter = (CommandSplittable) protocolChecker;
                     commandSplitter.setProtocol(this);
                 }
             }else{
//                 LOG.error("ProtocolChecker do not choose or dataSize do not set in properties file." +
//                         "");
             }
        }
    }

    public AbstractProtocol(ProtocolCheckable protocolChecker, CommandSplittable commandSplitter) {
        this.protocolChecker = protocolChecker;
        this.commandSplitter = commandSplitter;
        commandSplitter.setProtocol(this);
    }


    // todo странная зависимость
    public void setCommandMap(Map<Byte,Integer> map) {
        commandSplitter.setCommandSizeMap(map);
    }
    public void addController(SerialControllable<RESPONSE, REQUEST> controller){
        serialControllableList.add(controller);
    }
    public void addProtocol(AbstractProtocol<RESPONSE, REQUEST> protocol){
        protocolList.add(protocol);
        protocol.setSender(sender);
    }

    //Receive methods
    @Override
    public boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque){
        return protocolChecker.checkProtocol(deque);
    }
    @Override
    public void parseQueue(ConcurrentLinkedDeque<Byte> deque){
        commandSplitter.parseQueue(deque);
    }
    public void upByteBuffer(ByteBuffer buffer){
//        LOG.trace("FromUART: " + Arrays.toString(buffer.array()));
        System.out.println("FromUART: " + Arrays.toString(buffer.array()));
        REQUEST request = protocolList.stream().map(x->x.createByteToRequest(buffer)).filter(x->(x!=null)).findFirst().get();
        serialControllableList.forEach(x->x.serviceRequest(request));
    }

    //Transceiver method
    public void setSender(DeviceInterface sender){
        this.sender = sender;
    }
    @Override
    public void sendResponse(RESPONSE response){
        ByteBuffer commandBuffer = protocolList.stream().map(x->x.createResponseToByte(response)).filter(x->(x!=null)).findFirst().get();
        commandBuffer.rewind();
        if(commandSplitter != null){
            ByteBuffer preBuffer = commandSplitter.getSyncSequence();
            preBuffer.rewind();
            ByteBuffer sendBuffer = ByteBuffer.allocate(preBuffer.limit()+commandBuffer.limit()).put(preBuffer).put(commandBuffer);
//            LOG.trace("ToUART: "+DatatypeConverter.printHexBinary(sendBuffer.array()));
            sender.sendDataArray(sendBuffer);
        }else {
//            LOG.trace("ToUART: "+DatatypeConverter.printHexBinary(commandBuffer.array()));
            sender.sendDataArray(commandBuffer);
        }
    }
}
