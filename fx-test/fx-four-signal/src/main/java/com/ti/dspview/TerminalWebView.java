package com.ti.dspview;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.web.WebView;
import javafx.scene.layout.Region;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

import java.util.function.Consumer;

public class TerminalWebView extends Region {
    private final WebView webView;
    private final StringBuilder terminalContent = new StringBuilder("> ");
    private String currentCommand = "";
    private Consumer<String> commandHandler;

    public TerminalWebView() {
        webView = new WebView();

        webView.setPrefHeight(400); // Ограничиваем высоту
        webView.setMaxHeight(500);

        webView.setPrefWidth(Region.USE_COMPUTED_SIZE);
        webView.setMaxWidth(Double.MAX_VALUE);

//        webView.setPrefHeight(400);
        getChildren().add(webView); // Добавляем WebView в панель

        widthProperty().addListener((obs, oldWidth, newWidth) -> webView.setPrefWidth(newWidth.doubleValue()));

        updateWebView("TerminalWebView");
        webView.setOnKeyPressed(this::handleKeyPress);
        Platform.runLater(webView::requestFocus);
    }

    public WebView getWebView() {
        return webView;
    }

    public void setCommandHandler(Consumer<String> handler) {
        this.commandHandler = handler;
    }

    private void handleKeyPress(KeyEvent event) {
        if (event.getText().matches("\\S") || event.getText().equals(" ")) {
            currentCommand += event.getText();
            updateWebView("handleKeyPress simbol");
        }
        else if (event.getCode() == KeyCode.BACK_SPACE && !currentCommand.isEmpty()) {
            currentCommand = currentCommand.substring(0, currentCommand.length() - 1);
            updateWebView("BACK_SPACE");
        }
        else if (event.getCode() == KeyCode.UP) {
            event.consume();
        }
        else if (event.getCode() == KeyCode.ENTER) {
            event.consume();
//            currentCommand += "\n";
            processCommand();
        }
    }

    public void typeAndSendEmulate(String command) {
        currentCommand += command;
        updateWebView("typeAndSendEmulate");
        processCommand();
    }

    private void processCommand() {
        String command = currentCommand;
//        System.out.println("To send:"+ currentCommand + " -> " + currentCommand.replace("\n", "\\n").replace("\r", "\\r"));
//        terminalContent.append(currentCommand).append("\n");
        currentCommand = "";

        updateWebView("processCommand");

        if (commandHandler != null) {
            commandHandler.accept(command + "\n");
        }
    }

    public void sendResponse(String response) {
        Platform.runLater(() -> {
//            System.out.println("Response: " + response);
            terminalContent.append(response);//.append("\n");
            addPrompt();
        });
    }

    private void addPrompt() {
        terminalContent.append("> ");
        updateWebView("addPrompt");
    }

    private void updateWebViewPromt() {
        Platform.runLater(() -> {
            String displayedText = terminalContent.toString();

            String highlightedText = displayedText
                    .replaceAll("\\b(ver|reset|config|save|d)\\b", "<span style='color: blue; font-weight: bold;'>$1</span>")
                    ;

            String htmlContent = "<html><body style='font-family: monospace; white-space: pre-wrap;'>"
                    + highlightedText +
                    "<span style='background: #ccc;'>&#8203;</span></body></html>";

            webView.getEngine().loadContent(htmlContent);
        });
    }

//    private void updateWebView(String from) {
//        System.out.println("From " + from);
//        Platform.runLater(() -> {
//            String displayedText = terminalContent.toString();
//            if (!currentCommand.isEmpty()) {
//                displayedText += currentCommand;
//            }
//
//            String highlightedText = displayedText
//                    .replaceAll("\\b(ver|reset|config|save|d)\\b", "<span style='color: blue; font-weight: bold;'>$1</span>");
//
//            String htmlContent = "<html><body style='font-family: monospace; white-space: pre-wrap;'>"
//                    + highlightedText +
//                    "<span style='background: #ccc;'>&#8203;</span></body></html>";
//
//            webView.getEngine().loadContent(htmlContent);
//        });
//    }

    private void updateWebView(String from) {
//        System.out.println("From " + from);
        Platform.runLater(() -> {
            String displayedText = terminalContent.toString();
            if (!currentCommand.isEmpty()) {
                displayedText += currentCommand;
            }

            String highlightedText = displayedText
                    .replaceAll("\\b(ver|reset|config|save|d)\\b", "<span style='color: blue; font-weight: bold;'>$1</span>");

            // ✅ Автоматическая прокрутка вниз
            String script = "document.body.innerHTML = `" + highlightedText + "`; window.scrollTo(0, document.body.scrollHeight);";

            String htmlContent = "<html><body style='font-family: monospace; white-space: pre-wrap;' onload=\"" + script + "\">"
                    + highlightedText +
                    "<span style='background: #ccc;'>&#8203;</span></body></html>";

            webView.getEngine().loadContent(htmlContent);
        });
    }


}
