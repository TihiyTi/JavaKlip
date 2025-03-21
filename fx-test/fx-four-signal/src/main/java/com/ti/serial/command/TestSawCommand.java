package com.ti.serial.command;


import java.nio.ByteBuffer;

public class TestSawCommand extends AbstractSawCommand<TestSignalType> {
    protected int data1 = 0;
    protected int data2 = 0;

    public TestSawCommand(){}
    public TestSawCommand(int data1, int data2){
        this.data1 = data1;
        this.data2 = data2;
    }

    @Override
    public AbstractCommand parseByteBuffer(ByteBuffer buffer) {
        buffer.rewind();
        data1 = buffer.getInt();
        data2 = buffer.getInt();
        return this;
    }

    @Override
    public ByteBuffer createByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(data1);
        buffer.putInt(data2);
        return buffer;
    }

    public int getData1(){return data1;}
    public int getData2(){return data2;}
    @Override
    public int getData(TestSignalType type){
        if(type.equals(TestSignalType.DATA1)){
            return data1;
        }else if (type.equals(TestSignalType.DATA2)){
            return data2;
        }else {
            return 0;
        }
    }

    @Override
    public void debugPrint() {
        System.out.println("Not implemented for SawCommand");
    }
}
