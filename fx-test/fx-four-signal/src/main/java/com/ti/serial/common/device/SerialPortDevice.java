package com.ti.serial.common.device;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.ti.PropertiesService;
import com.ti.serial.def.AbstractProtocol;
import com.ti.serial.common.protocol.Protocol;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SerialPortDevice implements DeviceInterface{

    private static String PORT_NAME = "portName";
    private static String BAUD_RATE = "baudRate";

    private SerialPort port;

    private Protocol protocol;
    public boolean liteProtocol = false;

    public SerialPortDevice(){
        String portName = PropertiesService.getGlobalProperty(PORT_NAME);
        if(portName==null){
            portName = "COM7";
//            LOG.info("COM Port name not found in property file. Default name "+ portName);
            PropertiesService.setGlobalProperty(PORT_NAME,portName);
        }
        String baudRateString = PropertiesService.getGlobalProperty(BAUD_RATE);
        Integer baudRate = 9216000;
        if(baudRateString==null){
//            LOG.info("COM Port baud rate not found in property file. Default rate "+ baudRate);
            PropertiesService.setGlobalProperty(BAUD_RATE,String.valueOf(baudRate));
        }else{
            baudRate = Integer.valueOf(baudRateString);
        }
        openPort(portName, baudRate);
        addListener();
    }
    public SerialPortDevice(String portName, int speed){
        openPort(portName, speed);
        addListener();
    }

    public void openPort(String portName, int speed) {
        port = SerialPort.getCommPort(portName);
        port.openPort();
        System.out.println("Port " + port.getSystemPortName() + " open " + isOpen());
//        LOG.info("Port "+portName+" opened with rate "+ speed);
        port.setBaudRate(speed);
        port.setNumDataBits(8);
        port.setNumStopBits(SerialPort.ONE_STOP_BIT);
        port.setParity(SerialPort.NO_PARITY);



//            port.addEventListener(new SimpleProtocolListener());
//        } catch (SerialPortException e) {
//            e.printStackTrace();
//        }
//        finally {
//            try {
//                System.out.println("Close port");
//                port.closePort();
//            } catch (SerialPortException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public boolean isOpen() {
        return port != null && port.isOpen();
    }

//    @Override
    public void close() {
//        try {
            synchronized (this) {
                if (isOpen() && port != null) {
//                    LOGGER.log(Level.INFO, () -> "%s Close connection".formatted(this));
                    port.closePort();
                }
            }
//        }
//        finally {
//            binaryLogChannel.close();
//        }
    }

    private void addListener() {
        port.addDataListener(new SimpleProtocolListener());

    }

    class SimpleProtocolListener implements SerialPortDataListener{
        private ConcurrentLinkedDeque<Byte> deque = new ConcurrentLinkedDeque<Byte>();

        @Override
        public int getListeningEvents() {
            return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
        }

        @Override
        public void serialEvent(SerialPortEvent event)
        {
            byte[] buf = event.getReceivedData();
//            fileService.writeBytes(ByteBuffer.wrap(buf));
            for (byte element: buf){
                deque.add(element);
            }

//            System.out.println(Arrays.toString(buf));
//            System.out.println(new String(buf));
//            LOG.info("Recieve " + buf.length + " bytes from " + port.getPortName());

            if(liteProtocol){
                protocol.parse(deque);
            }else {
                if(protocol.checkProtocol(deque)){
                    protocol.parseQueue(deque);
                }
            }
        }

    }

    @Override
    public void sendDataArray(ByteBuffer sendBuffer) {
        synchronized (this) {
            var countBytes = 0;
            if (isOpen() && port != null) {
                sendBuffer.rewind();
                countBytes = port.writeBytes(sendBuffer.array(), sendBuffer.limit());
            }
//            return countBytes;
        }
    }
    @Override
    public void sendDataArray(byte[] sendArray) {
        synchronized (this) {
            if (isOpen() && port != null) {
                port.writeBytes(sendArray, sendArray.length);
            }
//            return countBytes;
        }
    }


    @Override
    public <RESPONSE, REQUEST> void setProtocol(Protocol<RESPONSE, REQUEST> protocol) {
        this.protocol = protocol;

        if(liteProtocol){
            protocol.setDevice(this);
        }else {
            ((AbstractProtocol)protocol).setSender(this);
        }
    }
}
