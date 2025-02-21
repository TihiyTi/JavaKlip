package com.ti.remg;


import com.ti.serial.command.AbstractCommand;
import com.ti.serial.command.AbstractSawCommand;

import java.nio.ByteBuffer;

public class RemgSignalCommand extends AbstractSawCommand<RemgSignalType> {
    protected int emg1 = 0;
    protected int emg2 = 0;
    protected int emg3 = 0;
    protected int emg4 = 0;
    protected int emg5 = 0;
    protected int emg6 = 0;
    protected int emg7 = 0;
    protected int emg8 = 0;
    protected int emg9 = 0;
    protected int emg10 = 0;
    protected int emg11 = 0;
    protected int emg12 = 0;

    protected int eit1 = 0;
    protected int eit2 = 0;
    protected int eit3 = 0;
    protected int eit4 = 0;
    protected int eit5 = 0;
    protected int eit6 = 0;
    protected int eit7 = 0;
    protected int eit8 = 0;
    protected int eit9 = 0;
    protected int eit10 = 0;
    protected int eit11 = 0;
    protected int eit12 = 0;


    protected String hex;

    private static int count = 0;

    public RemgSignalCommand(){}
    public RemgSignalCommand(int emg1){
        this.emg1 = emg1;
    }

    @Override
    public AbstractCommand<RemgSignalType> parseByteBuffer(ByteBuffer buffer) {
        buffer.rewind();


        emg1 =  fromByteBufferLittleEndian(buffer, 1);
        emg2 =  fromByteBufferLittleEndianOld(buffer, 5);
        emg3 =  fromByteBufferLittleEndian(buffer, 9);
        emg4 =  fromByteBufferLittleEndian(buffer, 13);
        emg5 =  fromByteBufferLittleEndian(buffer, 17);
        emg6 =  fromByteBufferLittleEndian(buffer, 21);
        emg7 =  fromByteBufferLittleEndian(buffer, 25);
        emg8 =  fromByteBufferLittleEndian(buffer, 29);
        emg9 =  fromByteBufferLittleEndian(buffer, 33);
        emg10 =  fromByteBufferLittleEndian(buffer, 37);
        emg11 =  fromByteBufferLittleEndian(buffer, 41);
        emg12 =  fromByteBufferLittleEndian(buffer, 45);

        eit1 =  fromByteBufferLittleEndian(buffer, 49)-9000000;
        eit2 =  fromByteBufferLittleEndian(buffer, 53)-9000000;
        eit3 =  fromByteBufferLittleEndian(buffer, 57)-9000000;
        eit4 =  fromByteBufferLittleEndian(buffer, 61)-9000000;
        eit5 =  fromByteBufferLittleEndian(buffer, 65)-9000000;
        eit6 =  fromByteBufferLittleEndian(buffer, 69)-9000000;
        eit7 =  fromByteBufferLittleEndian(buffer, 73)-9000000;
        eit8 =  fromByteBufferLittleEndian(buffer, 77)-9000000;
        eit9 =  fromByteBufferLittleEndian(buffer, 81)-9000000;
        eit10 =  fromByteBufferLittleEndian(buffer, 85)-9000000;
        eit11 =  fromByteBufferLittleEndian(buffer, 89)-9000000;
        eit12 =  fromByteBufferLittleEndian(buffer, 93)-9000000;

        buffer.rewind();
//        hex = HexFormat.of().formatHex(buffer.array());
//        count++;
//        if (37000 < count & count < 37500){
//            System.out.println(" Pack: " + hex);
//        }
        return this;
    }

    @Override
    public ByteBuffer createByteBuffer() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(emg1);
        return buffer;
    }

    public int getData1(){return emg1;}
    public String getHex(){return hex;}

    @Override
    public int getData(RemgSignalType type){
        if(type.equals(RemgSignalType.EMG1)){
            return emg1;
        }else if(type.equals(RemgSignalType.EMG2)){
            return emg2;
        }else if(type.equals(RemgSignalType.EMG3)){
            return emg3;
        }else if(type.equals(RemgSignalType.EMG4)){
            return emg4;
        }else if(type.equals(RemgSignalType.EMG5)){
            return emg5;
        }else if(type.equals(RemgSignalType.EMG6)){
            return emg6;
        }else if(type.equals(RemgSignalType.EMG7)){
            return emg7;
        }else if(type.equals(RemgSignalType.EMG8)){
            return emg8;
        }else if(type.equals(RemgSignalType.EMG9)){
            return emg9;
        }else if(type.equals(RemgSignalType.EMG10)){
            return emg10;
        }else if(type.equals(RemgSignalType.EMG11)){
            return emg11;
        }else if(type.equals(RemgSignalType.EMG12)){
            return emg12;
        }else if(type.equals(RemgSignalType.EIT1)){
            return eit1;
        }else{
            return 0;
        }
    }

    @Override
    public void debugPrint() {
        System.out.println("Not implemented for SawCommand");
    }

    public static int fromByteBufferLittleEndianOld(ByteBuffer buffer, int startIndex) {
        if (startIndex + 2 >= buffer.limit()) {
            throw new IllegalArgumentException("Недостаточно данных в буфере для чтения 3 байтов");
        }

        // Читаем байты с заданного индекса
        byte b1 = buffer.get(startIndex);     // Младший байт (0x34)
        byte b2 = buffer.get(startIndex + 1); // Средний байт (0x12)
        byte b3 = buffer.get(startIndex + 2) ; // Старший байт (0xFF, знаковый)

        // Собираем Little-Endian int (младший -> средний -> старший)
        int result = (b1) | (b2 << 8) | (b3 << 16);

        // Проверяем знаковый бит (7-й бит b3)
        if ((b3 & 0x80) != 0) { // Если b3 ≥ 0x80 (отрицательное число)
            result |= 0xFF000000; // Расширяем знак
        }

        return result;
    }
    public static int fromByteBufferLittleEndian(ByteBuffer buffer, int startIndex) {
        int value = 0;
        for (int j = 0; j < 3; j++) {
            value |= (buffer.get(startIndex + j) & 0xff) << (j * 8 + 8);
        }
        return value;
    }

}
