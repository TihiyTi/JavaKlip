package com.ti.serial.lite.protocol;



import com.ti.serial.def.AbstractProtocol;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SawProtocolParser implements ProtocolParser {
//    private static final Logger LOG = LogManager.getLogger("TiSerialServiceLogger");

    private int dataSizeInByte;
    private int wordSizeInByte;
    private int skipByteCount = 0;
    private byte inputCurrentSawValue = 0;
    private byte outputCurrentValue = 0;

    public int numOfOkParse = 0;
    public int numOfCrashParse = 0;
    public int numOfPolls = 0;

    private AbstractProtocol protocol;

    public SawProtocolParser(int dataSizeInByte) {
        this.dataSizeInByte = dataSizeInByte;
        wordSizeInByte = dataSizeInByte + 1; //plus saw byte
    }

    @Override
    public boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque) {
        if(deque.peek() == (byte)(inputCurrentSawValue +1)){
//            inputCurrentSawValue++;
//            System.out.println("First byte "+ deque.peek() +" == "+ (inputCurrentSawValue+1)+" Size = " + deque.size());
//            LOG.trace("First byte "+ deque.peek() +" == "+ (inputCurrentSawValue+1)+" Size = " + deque.size());
            return true;
        }else{
            System.out.println("Crash. First byte "+ deque.peek() +" != "+ (inputCurrentSawValue+1)+" Size = " + deque.size());
//            LOG.trace("Crash. First byte "+ deque.peek() +" != "+ (inputCurrentSawValue+1)+" Size = " + deque.size());
            return findProtocol(deque);
        }
    }

    private boolean findProtocol(ConcurrentLinkedDeque<Byte> deque){
        ByteBuffer buffer = ByteBuffer.allocate(wordSizeInByte *4);
        if(deque.size() > wordSizeInByte *4){
            for (int i = 0; i < wordSizeInByte * 4; i++) {
                buffer.put(deque.poll());
                numOfPolls ++;
            }
            Integer sawIndex = null;
            for (int i = 0; i < wordSizeInByte; i++) {
                if( (buffer.get(i) == (byte)(buffer.get(i + wordSizeInByte)-1))  &
                        (buffer.get(i + wordSizeInByte) == (byte)(buffer.get(i + 2* wordSizeInByte) - 1))  ){
                    sawIndex = i;
                }
            }
            if(sawIndex == null){
                skipByteCount = skipByteCount + wordSizeInByte *4;
                System.out.println("Skip "+ wordSizeInByte *4+" byte. Total skipped - "+skipByteCount);
//                LOG.trace("Skip "+ wordSizeInByte *4+" byte. Total skipped - "+skipByteCount);
                return false;
            }else{
                inputCurrentSawValue = (byte) (buffer.get(sawIndex) - 1);
                for (int i = wordSizeInByte *4 -1; i > sawIndex - 1; i--) {
                    deque.offerFirst(buffer.get(i));
                    numOfPolls--;
                }
                skipByteCount = skipByteCount + sawIndex;
                System.out.println("Protocol found. Skipped - "+skipByteCount +" bytes. Current SAW value " + inputCurrentSawValue);
//                LOG.trace("Protocol found. Skipped - "+skipByteCount +" bytes. Current SAW value " + inputCurrentSawValue);
                return true;
            }
        }else {
            return false;
        }
    }

    @Override
    public List<ByteBuffer> parseQueue(ConcurrentLinkedDeque<Byte> deque) {
        List<ByteBuffer> list = new ArrayList<>();
        while(deque.size() >= wordSizeInByte){

//            System.out.println("_First byte "+ deque.peek() +" == "+ (inputCurrentSawValue+1)+" Size = " + deque.size());
            if(deque.peek() != (byte)(inputCurrentSawValue+1)){
                System.out.println("Crash " + HexFormat.of().toHexDigits(deque.peek()) + "  != " + HexFormat.of().toHexDigits((byte)(inputCurrentSawValue+1)));
//                System.out.println(numOfPolls);
                numOfCrashParse++;
                break;
            }else{
//                System.out.println("Ok " + HexFormat.of().toHexDigits(deque.peek()) + "  != " + HexFormat.of().toHexDigits((byte)(inputCurrentSawValue+1)));
                numOfOkParse++;
            }

            inputCurrentSawValue = deque.poll();
            numOfPolls ++;

//            System.out.println("Saw: " + inputCurrentSawValue);
//            synchronized (deque) {
//            }
            ByteBuffer buffer = ByteBuffer.allocate(dataSizeInByte);
            for (int i = 0; i < dataSizeInByte; i++) {
                buffer.put((byte)deque.poll());
                numOfPolls ++;
            }
            list.add(buffer);
        }
        return list;
    }

    @Override
    public ByteBuffer appendSyncToBuffer(ByteBuffer bufferWithOutHead) {
        outputCurrentValue++;
        bufferWithOutHead.rewind();
        return ByteBuffer.allocate(bufferWithOutHead.limit()+1).put(outputCurrentValue).put(bufferWithOutHead);
    }
}
