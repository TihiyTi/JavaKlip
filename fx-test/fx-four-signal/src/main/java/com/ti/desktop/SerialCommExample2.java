package com.ti.desktop;

import com.ti.serial.device.SerialPortDevice;

import java.nio.ByteBuffer;
import java.util.Random;

public class SerialCommExample2 {
    public static void main(String[] args) {
        SerialPortDevice device = new SerialPortDevice("COM7", 115200);

        System.out.println(device.isOpen());

        // Отправка данных
        String dataToSend = "Hello, Serial Port!";
        device.sendDataArray(dataToSend.getBytes());
        System.out.println("Отправлено: " + dataToSend);


        // Ожидаем завершения
        System.out.println("Нажмите Enter для выхода...");
        try {
            System.in.read();
        } catch (Exception ignored) {}

        device.close();
        System.out.println("Порт закрыт.");


    }
}
