package com.ti;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class IntegerListFileSaver {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    private final BlockingQueue<List<Integer>> queue;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private ExecutorService executor;
    private Writer writer;
    private final String baseFileName;

    public IntegerListFileSaver(BlockingQueue<List<Integer>> queue, String baseFileName) {
        this.queue = queue;
        this.baseFileName = (baseFileName == null || baseFileName.isBlank()) ? "output" : baseFileName;
    }

    public void start() throws IOException {
        if (running.compareAndSet(false, true)) {
            prepareFile();
            executor = Executors.newSingleThreadExecutor();
            executor.submit(this::processQueue);
        }
    }

    public void stop() throws IOException {
        running.set(false);
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        } finally {
            closeFile();
        }
    }

    private void prepareFile() throws IOException {
        String timestamp = LocalDateTime.now().format(DATE_TIME_FORMATTER);
        String fileName = baseFileName + "_" + timestamp + ".txt";

        File file = new File(fileName);
        FileUtils.touch(file);

        writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8);
    }

    private void processQueue() {
        try {
            while (running.get() || !queue.isEmpty()) {
                List<Integer> list = queue.poll(500, TimeUnit.MILLISECONDS);
                if (list != null) {
                    saveList(list);
                }
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        } finally {
            try {
                closeFile();
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии файла: " + e.getMessage());
            }
        }
    }

    private void saveList(List<Integer> list) throws IOException {
        String line = String.join(",", list.stream().map(String::valueOf).toArray(String[]::new));
        writer.write(line + System.lineSeparator());
    }

    private void closeFile() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}
