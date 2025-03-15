package com.ti.remg;

import com.ti.IntegerArrayFileSaver;
import com.ti.IntegerListFileSaver;
import com.ti.signals.SignalConsumer;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class RemgSaveController implements SignalConsumer<int[]> {

    private final IntegerArrayFileSaver saver;
    private final BlockingQueue<int[]> queue = new LinkedBlockingQueue<>();

    private final AtomicBoolean saveflag = new AtomicBoolean(false);

    public RemgSaveController() {
        saver = new IntegerArrayFileSaver(queue,"remg_data_");
    }

    @Override
    public void putElement(int[] element) {
        if(saveflag.get()) {
            queue.add(element);
        }
    }

    public void toogleSaving(){
        if(saveflag.compareAndSet(false,true)) {
            try {
                saver.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                saver.stop();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            saveflag.set(false);
        }
    }
}
