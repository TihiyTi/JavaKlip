package com.ti.desktop;

import com.ti.serial.common.device.SerialPortDevice;
import com.ti.serial.def.controller.ByteController;
import com.ti.serial.def.protocol.ByteFlowProtocol;
import com.ti.serial.def.SerialService;

import java.nio.ByteBuffer;

public class SerialCommandExample {

    public static void main(String[] args) {
        SerialPortDevice device = new SerialPortDevice("COM7", 921600);

        ByteFlowProtocol protocol = new ByteFlowProtocol();
        ByteController controller = new ByteController();
        SerialService<ByteBuffer,ByteBuffer> service = new SerialService<>(device);
        service.setProtocol(protocol);
        service.addController(controller);

        controller.send("Test\n".getBytes());


        // Ожидаем завершения
        System.out.println("Нажмите Enter для выхода...");
        try {
            System.in.read();
        } catch (Exception ignored) {}

        device.close();
        System.out.println("Порт закрыт.");
    }
}
