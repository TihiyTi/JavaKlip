package com.ti.desktop;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RichTextAreaExample2 extends Application {
    @FXML
    private WebView webView; // Теперь единственная панель
    private StringBuilder terminalContent = new StringBuilder("> "); // Храним текст терминала
    private String currentCommand = ""; // Введенная команда

    @Override
    public void start(Stage primaryStage) {
        webView = new WebView();
        webView.setPrefHeight(400); // Полноэкранный режим

        // Отображаем начальный текст терминала
        updateWebView();

        // Добавляем обработчик клавиш
        webView.setOnKeyPressed(this::handleKeyPress);

        VBox root = new VBox(webView);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setTitle("WebView Terminal");
        primaryStage.show();

        // Фокусируем WebView, чтобы он сразу принимал ввод
        Platform.runLater(webView::requestFocus);
    }

    private void handleKeyPress(KeyEvent event) {
        // Обрабатываем символы (только печатные)
        if (event.getText().matches("\\S") || event.getText().equals(" ")) {
            currentCommand += event.getText();
            updateWebView();
        }
        // Обрабатываем Backspace (удаляем последний символ в текущей команде)
        else if (event.getCode() == KeyCode.BACK_SPACE && !currentCommand.isEmpty()) {
            currentCommand = currentCommand.substring(0, currentCommand.length() - 1);
            updateWebView();
        }
        // Запрещаем перемещение вверх (чтобы нельзя было редактировать прошлые строки)
        else if (event.getCode() == KeyCode.UP) {
            event.consume();
        }
        // Обрабатываем ENTER: отправляем команду и ждем ответ
        else if (event.getCode() == KeyCode.ENTER) {
            event.consume(); // Останавливаем стандартную обработку
            processCommand();
        }
    }

    private void processCommand() {
        String command = currentCommand.trim();

        // ❗ Оставляем введенный текст в истории терминала перед очисткой
        terminalContent.append(currentCommand).append("\n");
        currentCommand = ""; // Очищаем поле ввода (но текст остается на экране)

        updateWebView();

        // Отправляем строку в обработчик
        sendString(command);

        // Эмулируем задержку ответа (например, ожидание данных от устройства)
        PauseTransition delay = new PauseTransition(Duration.seconds(1.5)); // 1.5 секунды ожидания
        delay.setOnFinished(e -> {
            String response = "Ответ устройства: " + command.toUpperCase(); // Эмуляция ответа
            Platform.runLater(() -> {
                terminalContent.append(response).append("\n");
                addPrompt();
            });
        });

        delay.play(); // Запускаем задержку
    }

    private void addPrompt() {
        terminalContent.append("> ");
        updateWebView();
    }

    private void sendString(String input) {
        System.out.println("Отправка команды: " + input);
    }

    // Функция для подсветки ключевых слов
    private void updateWebView() {
        Platform.runLater(() -> {
            String highlightedText = terminalContent.toString() + currentCommand;

            // Подсветка ключевых слов
            highlightedText = highlightedText
                    .replaceAll("\\b(if|else|for|while|return)\\b", "<span style='color: blue; font-weight: bold;'>$1</span>")
                    .replaceAll("\n", "<br>");

            // Обновленный HTML для отображения
            String htmlContent = "<html><body style='font-family: monospace; white-space: pre-wrap;'>"
                    + highlightedText +
                    "<span style='background: #ccc;'>&#8203;</span></body></html>"; // Имитация курсора

            webView.getEngine().loadContent(htmlContent);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
