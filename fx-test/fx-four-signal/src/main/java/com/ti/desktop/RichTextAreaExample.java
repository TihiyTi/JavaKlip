package com.ti.desktop;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class RichTextAreaExample extends Application {
    @FXML
    private TextArea taConsole;
    private int minEditablePosition; // Граница редактируемой области

    @Override
    public void start(Stage primaryStage) {
        taConsole = new TextArea();
        taConsole.setWrapText(true);

        // Начальный текст с промптом
        taConsole.setText("> ");
        minEditablePosition = taConsole.getText().length();
        taConsole.positionCaret(minEditablePosition);

        // Добавляем обработчик клавиш
        taConsole.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);

        // Блокируем удаление перед границей с помощью TextFormatter
        taConsole.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getRangeStart() < minEditablePosition) {
                return null; // Запрещаем изменение перед границей
            }
            return change;
        }));

        primaryStage.setScene(new Scene(taConsole, 400, 300));
        primaryStage.show();
    }

    private void handleKeyPress(KeyEvent event) {
        // Запрещаем BACKSPACE перед границей
        if (event.getCode() == KeyCode.BACK_SPACE && taConsole.getCaretPosition() <= minEditablePosition) {
            event.consume();
        }
        // Запрещаем перемещение вверх (чтобы нельзя было редактировать прошлые строки)
        else if (event.getCode() == KeyCode.UP) {
            event.consume();
        }
        // Обрабатываем ENTER: отправляем строку и ждем ответ
        else if (event.getCode() == KeyCode.ENTER) {
            event.consume(); // Останавливаем стандартную обработку
            processCommand();
        }
    }

    private void processCommand() {
        // Получаем текущую строку без `> `
        String fullText = taConsole.getText();
        String command = fullText.substring(minEditablePosition).trim();

        // Отправляем строку в обработчик
        sendString(command);

        // Эмулируем задержку ответа (например, ожидание данных от устройства)
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5)); // 1.5 секунды ожидания
        delay.setOnFinished(e -> {
            String response = "Ответ устройства: " + command.toUpperCase(); // Эмуляция ответа
            Platform.runLater(() -> {
                taConsole.appendText("\n" + response);
                addPrompt(); // Добавляем новый `> ` после задержки
            });
        });

        delay.play(); // Запускаем задержку
    }

    private void addPrompt() {
        taConsole.appendText("\n> ");
        minEditablePosition = taConsole.getText().length();
        taConsole.positionCaret(minEditablePosition);
    }

    private void sendString(String input) {
        System.out.println("Отправка команды: " + input);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
