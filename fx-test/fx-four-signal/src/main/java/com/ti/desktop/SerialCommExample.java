package com.ti.desktop;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class SerialCommExample {
    public static void main(String[] args) {
        // Получение списка доступных последовательных портов
        SerialPort[] ports = SerialPort.getCommPorts();
        if (ports.length == 0) {
            System.out.println("Нет доступных COM-портов.");
            return;
        }

        // Вывод списка доступных портов
        System.out.println("Доступные порты:");
        for (SerialPort port : ports) {
            System.out.println(port.getSystemPortName());
        }

        // Выбираем первый доступный порт
        SerialPort serialPort = ports[0];
        serialPort.setBaudRate(115200);
        serialPort.setNumDataBits(8);
        serialPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        serialPort.setParity(SerialPort.NO_PARITY);

        if (!serialPort.openPort()) {
            System.out.println("Не удалось открыть порт " + serialPort.getSystemPortName());
            return;
        }

        System.out.println("Порт " + serialPort.getSystemPortName() + " открыт.");

        // Добавление слушателя для чтения входящих данных
        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;
                }

                byte[] readBuffer = new byte[serialPort.bytesAvailable()];
                int numRead = serialPort.readBytes(readBuffer, readBuffer.length);
                System.out.println("Принято: " + new String(readBuffer, 0, numRead));
            }
        });

        // Отправка данных
        String dataToSend = "Hello, Serial Port!";
        serialPort.writeBytes(dataToSend.getBytes(), dataToSend.length());
        System.out.println("Отправлено: " + dataToSend);

        // Закрытие порта при завершении программы
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (serialPort.isOpen()) {
                serialPort.closePort();
                System.out.println("Порт закрыт.");
            }
        }));
    }
}
