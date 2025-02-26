package com.ti.dspview;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.util.function.Consumer;

public class TerminalWebView {
    private final WebView webView;
    private final StringBuilder terminalContent = new StringBuilder("> "); // Храним историю терминала
    private String currentCommand = ""; // Введенная команда
    private Consumer<String> commandHandler; // Обработчик команд

    public TerminalWebView() {
        webView = new WebView();
        webView.setPrefHeight(400); // Размер по умолчанию

        // Обновляем отображение
        updateWebView();

        // Добавляем обработчик клавиш
        webView.setOnKeyPressed(this::handleKeyPress);

        // Фокусируем WebView
        Platform.runLater(webView::requestFocus);
    }

    public WebView getWebView() {
        return webView;
    }

    public void setCommandHandler(Consumer<String> handler) {
        this.commandHandler = handler;
    }

    private void handleKeyPress(KeyEvent event) {
        // Обрабатываем символы (только печатные)
        if (event.getText().matches("\\S") || event.getText().equals(" ")) {
            currentCommand += event.getText();
            updateWebView();
        }
        // Обрабатываем Backspace
        else if (event.getCode() == KeyCode.BACK_SPACE && !currentCommand.isEmpty()) {
            currentCommand = currentCommand.substring(0, currentCommand.length() - 1);
            updateWebView();
        }
        // Запрещаем UP для перемещения назад
        else if (event.getCode() == KeyCode.UP) {
            event.consume();
        }
        // Обрабатываем ENTER
        else if (event.getCode() == KeyCode.ENTER) {
            event.consume();
            processCommand();
        }
    }

    private void processCommand() {
        String command = currentCommand.trim();
        terminalContent.append(currentCommand).append("\n");
        currentCommand = ""; // Очищаем ввод

        updateWebView();

        if (commandHandler != null) {
            commandHandler.accept(command); // Передаем команду в обработчик
        }
    }

    public void sendResponse(String response) {
        Platform.runLater(() -> {
            terminalContent.append(response).append("\n");
            addPrompt();
        });
    }

    private void addPrompt() {
        terminalContent.append("> ");
        updateWebView();
    }

    private void updateWebView() {
        Platform.runLater(() -> {
            String highlightedText = terminalContent.toString() + currentCommand;

            // Подсветка ключевых слов
            highlightedText = highlightedText
                    .replaceAll("\\b(if|else|for|while|return)\\b", "<span style='color: blue; font-weight: bold;'>$1</span>")
                    .replaceAll("\n", "<br>");

            String htmlContent = "<html><body style='font-family: monospace; white-space: pre-wrap;'>"
                    + highlightedText +
                    "<span style='background: #ccc;'>&#8203;</span></body></html>"; // Имитация курсора

            webView.getEngine().loadContent(htmlContent);
        });
    }
}
