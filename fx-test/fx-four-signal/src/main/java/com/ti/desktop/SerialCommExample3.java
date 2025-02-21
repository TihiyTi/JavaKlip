package com.ti.desktop;

import com.fazecast.jSerialComm.*;

import java.util.concurrent.*;

public class SerialCommExample3 {
    private static ScheduledExecutorService executor;

    public static void main(String[] args) {
        SerialPort comPort = SerialPort.getCommPort("COM7");
        comPort.setBaudRate(115200);
        comPort.setNumDataBits(8);
        comPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        comPort.setParity(SerialPort.NO_PARITY);

        if (!comPort.openPort()) {
            System.out.println("Не удалось открыть порт.");
            return;
        }

        System.out.println("Открыт порт: " + comPort.getSystemPortName());

        // Добавляем слушателя событий
        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                byte[] newData = event.getReceivedData();
                System.out.println("📥 Получено " + newData.length + " байт: " + new String(newData));
            }
        });

        // Запускаем генерацию данных
        simulateByteByByteTransmission(comPort);

        // Ожидаем завершения
        System.out.println("Нажмите Enter для выхода...");
        try {
            System.in.read();
        } catch (Exception ignored) {}

        // Завершаем поток генерации данных
        if (executor != null) {
            executor.shutdown();  // Останавливаем задачи
            try {
                if (!executor.awaitTermination(2, TimeUnit.SECONDS)) {
                    executor.shutdownNow(); // Принудительно останавливаем, если не завершился
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }

        // Закрываем порт
        comPort.closePort();
        System.out.println("Порт закрыт.");
    }

    private static void simulateByteByByteTransmission(SerialPort comPort) {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            if (!comPort.isOpen()) return;

            // Отправляем строку "HELLO"
            byte[] data = "HELLO".getBytes();
            comPort.writeBytes(data, data.length);
            System.out.println("📤 Отправлено: " + new String(data));
        }, 0, 500, TimeUnit.MILLISECONDS);
    }
}

