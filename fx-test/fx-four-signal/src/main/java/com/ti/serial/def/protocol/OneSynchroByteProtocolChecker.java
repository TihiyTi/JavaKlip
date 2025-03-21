package com.ti.serial.def.protocol;


import com.ti.serial.common.protocol.ProtocolCheckable;

import java.util.concurrent.ConcurrentLinkedDeque;

public class OneSynchroByteProtocolChecker implements ProtocolCheckable {
    private int skipByteCount = 0;

    @Override
    public boolean checkProtocol(ConcurrentLinkedDeque<Byte> deque){
        if(deque.peek()==(byte)0xAA){
            return true;
        }else{
            while(!deque.isEmpty()){
                if(deque.peek()==(byte)0xAA){
                    return true;
                }else{
                    deque.poll();
                    skipByteCount++;
                    if(skipByteCount%10 ==0){
                        System.out.println("Crash Protocol N "+skipByteCount);
                    }
                }
            }
            return false;
        }
    }

}
