package com.ti.serial.command;


import java.nio.ByteBuffer;
import java.util.HexFormat;

public class SingleSignalCommand extends AbstractSawCommand<SingleSignalType> {
    protected int data1 = 0;
    protected String hex;

    private static int count = 0;

    public SingleSignalCommand(){}
    public SingleSignalCommand(int data1){
        this.data1 = data1;
    }

    @Override
    public AbstractCommand<SingleSignalType> parseByteBuffer(ByteBuffer buffer) {
        buffer.rewind();

        data1 =  fromByteBufferLittleEndian(buffer, 1);

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
        buffer.putInt(data1);
        return buffer;
    }

    public int getData1(){return data1;}
    public String getHex(){return hex;}

    @Override
    public int getData(SingleSignalType type){
        if(type.equals(SingleSignalType.SINGLE)){
            return data1;
        }else {
            return 0;
        }
    }

    @Override
    public void debugPrint() {
        System.out.println("Not implemented for SawCommand");
    }

    public static int fromByteBufferLittleEndian(ByteBuffer buffer, int startIndex) {
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
}
