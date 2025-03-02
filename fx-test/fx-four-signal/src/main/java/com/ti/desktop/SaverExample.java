package com.ti.desktop;

import com.ti.IntegerListFileSaver;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SaverExample {

    public static void main(String[] args) throws IOException, InterruptedException {
        BlockingQueue<List<Integer>> queue = new LinkedBlockingQueue<>();

        IntegerListFileSaver saver = new IntegerListFileSaver(queue, "integer_list");
        saver.start();

        // Генерация данных
        for (int i = 0; i < 5; i++) {
            queue.put(List.of(i, i + 1, i + 2));
            Thread.sleep(500);
        }

        // Остановка и завершение
        saver.stop();
        System.out.println("Сохранение завершено.");
    }
}
