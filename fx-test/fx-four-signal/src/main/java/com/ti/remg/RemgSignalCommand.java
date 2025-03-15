package com.ti.remg;


import com.ti.serial.command.AbstractCommand;
import com.ti.serial.command.AbstractSawCommand;

import java.nio.ByteBuffer;

public class RemgSignalCommand extends AbstractSawCommand<RemgSignalType> {

    int[] dataArray;

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

    protected int mtg1 = 0;
    protected int mtg2 = 0;
    protected int mtg3 = 0;
    protected int mtg4 = 0;
    protected int mtg5 = 0;
    protected int mtg6 = 0;
    protected int mtg7 = 0;
    protected int mtg8 = 0;
    protected int mtg9 = 0;
    protected int mtg10 = 0;
    protected int mtg11 = 0;
    protected int mtg12 = 0;

    protected int accx = 0;
    protected int accy = 0;
    protected int accz = 0;

    protected int girox = 0;
    protected int giroy = 0;
    protected int giroz = 0;

    protected String hex;

    private static int count = 0;

    public RemgSignalCommand(){}
    public RemgSignalCommand(int emg1){
        this.emg1 = emg1;
    }

    @Override
    public AbstractCommand<RemgSignalType> parseByteBuffer(ByteBuffer buffer) {
        buffer.rewind();


        emg1 =  fromByteBufferLittleEndian24bit(buffer, 1);
        emg2 =  fromByteBufferLittleEndianOld(buffer, 5);
        emg3 =  fromByteBufferLittleEndian24bit(buffer, 9);
        emg4 =  fromByteBufferLittleEndian24bit(buffer, 13);
        emg5 =  fromByteBufferLittleEndian24bit(buffer, 17);
        emg6 =  fromByteBufferLittleEndian24bit(buffer, 21);
        emg7 =  fromByteBufferLittleEndian24bit(buffer, 25);
        emg8 =  fromByteBufferLittleEndian24bit(buffer, 29);
        emg9 =  fromByteBufferLittleEndian24bit(buffer, 33);
        emg10 =  fromByteBufferLittleEndian24bit(buffer, 37);
        emg11 =  fromByteBufferLittleEndian24bit(buffer, 41);
        emg12 =  fromByteBufferLittleEndian24bit(buffer, 45);

        eit1 =  fromByteBufferLittleEndian24bit(buffer, 49);
        eit2 =  fromByteBufferLittleEndian24bit(buffer, 53);
        eit3 =  fromByteBufferLittleEndian24bit(buffer, 57);
        eit4 =  fromByteBufferLittleEndian24bit(buffer, 61);
        eit5 =  fromByteBufferLittleEndian24bit(buffer, 65);
        eit6 =  fromByteBufferLittleEndian24bit(buffer, 69);
        eit7 =  fromByteBufferLittleEndian24bit(buffer, 73);
        eit8 =  fromByteBufferLittleEndian24bit(buffer, 77);
        eit9 =  fromByteBufferLittleEndian24bit(buffer, 81);
        eit10 =  fromByteBufferLittleEndian24bit(buffer, 85);
        eit11 =  fromByteBufferLittleEndian24bit(buffer, 89);
        eit12 =  fromByteBufferLittleEndian24bit(buffer, 93);

        mtg1 =  fromByteBufferLittleEndian16bit(buffer, 97);
        mtg2 =  fromByteBufferLittleEndian16bit(buffer, 100);
        mtg3 =  fromByteBufferLittleEndian16bit(buffer, 103);
        mtg4 =  fromByteBufferLittleEndian16bit(buffer, 106);
        mtg5 =  fromByteBufferLittleEndian16bit(buffer, 109);
        mtg6 =  fromByteBufferLittleEndian16bit(buffer, 112);
        mtg7 =  fromByteBufferLittleEndian16bit(buffer, 115);
        mtg8 =  fromByteBufferLittleEndian16bit(buffer, 118);
        mtg9 =  fromByteBufferLittleEndian16bit(buffer, 121);
        mtg10 =  fromByteBufferLittleEndian16bit(buffer, 124);
        mtg11 =  fromByteBufferLittleEndian16bit(buffer, 127);
        mtg12 =  fromByteBufferLittleEndian16bit(buffer, 130);

        accx = fromByteBufferLittleEndian16bit(buffer, 133);
        accy = fromByteBufferLittleEndian16bit(buffer, 136);
        accz = fromByteBufferLittleEndian16bit(buffer, 139);
        girox = fromByteBufferLittleEndian16bit(buffer, 142);
        giroy = fromByteBufferLittleEndian16bit(buffer, 145);
        giroz = fromByteBufferLittleEndian16bit(buffer, 148);

        dataArray = new int[]{
                emg1, emg2, emg3, emg4, emg5, emg6, emg7, emg8, emg9, emg10, emg11, emg12,
                eit1, eit2, eit3, eit4, emg5, eit6, eit7, eit8, eit9, eit10, eit11, eit12,
                mtg1, mtg2, mtg3, mtg4, mtg5, mtg6, mtg7, mtg8, mtg9, mtg10, mtg11, mtg12,
                accx, accy, accz, girox, giroy, giroz
        };

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
        }else if(type.equals(RemgSignalType.EIT2)){
            return eit2;
        }else if(type.equals(RemgSignalType.EIT3)){
            return eit3;
        }else if(type.equals(RemgSignalType.EIT4)){
            return eit4;
        }else if(type.equals(RemgSignalType.EIT5)){
            return eit5;
        }else if(type.equals(RemgSignalType.EIT6)){
            return eit6;
        }else if(type.equals(RemgSignalType.EIT7)){
            return eit7;
        }else if(type.equals(RemgSignalType.EIT8)){
            return eit8;
        }else if(type.equals(RemgSignalType.EIT9)){
            return eit9;
        }else if(type.equals(RemgSignalType.EIT10)){
            return eit10;
        }else if(type.equals(RemgSignalType.EIT11)){
            return eit11;
        }else if(type.equals(RemgSignalType.EIT12)){
            return eit12;
        }else if(type.equals(RemgSignalType.MTG1)){
            return mtg1;
        }else if(type.equals(RemgSignalType.MTG2)){
            return mtg2;
        }else if(type.equals(RemgSignalType.MTG3)){
            return mtg3;
        }else if(type.equals(RemgSignalType.MTG4)){
            return mtg4;
        }else if(type.equals(RemgSignalType.MTG5)){
            return mtg5;
        }else if(type.equals(RemgSignalType.MTG6)){
            return mtg6;
        }else if(type.equals(RemgSignalType.MTG7)){
            return mtg7;
        }else if(type.equals(RemgSignalType.MTG8)){
            return mtg8;
        }else if(type.equals(RemgSignalType.MTG9)){
            return mtg9;
        }else if(type.equals(RemgSignalType.MTG10)){
            return mtg10;
        }else if(type.equals(RemgSignalType.MTG11)){
            return mtg11;
        }else if(type.equals(RemgSignalType.MTG12)){
            return mtg12;
        }else if(type.equals(RemgSignalType.ACCX)){
            return accx;
        }else if(type.equals(RemgSignalType.ACCY)){
            return accy;
        }else if(type.equals(RemgSignalType.ACCZ)){
            return accz;
        }else if(type.equals(RemgSignalType.GIROX)){
            return girox;
        }else if(type.equals(RemgSignalType.GIROY)){
            return giroy;
        }else if(type.equals(RemgSignalType.GIROZ)){
            return giroz;
        }else{
            return 0;
        }
    }

    public int[] getDataArray(){
        return dataArray;
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
    public static int fromByteBufferLittleEndian24bit(ByteBuffer buffer, int startIndex) {
        int value = 0;
        for (int j = 0; j < 3; j++) {
            value |= (buffer.get(startIndex + j) & 0xff) << (j * 8 + 8);
        }
        return value;
    }
    public static int fromByteBufferLittleEndian16bit(ByteBuffer buffer, int startIndex) {
        int value = 0;
        for (int j = 0; j < 2; j++) {
            value |= (buffer.get(startIndex + j) & 0xff) << (j * 8 + 8);
        }
        return value;
    }


}
