package com.ti.desktop;

import com.ti.dspview.TerminalWebView;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TerminalWebViewExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        TerminalWebView terminal = new TerminalWebView();

        // Устанавливаем обработчик команд
        terminal.setCommandHandler(command -> {
            System.out.println("Получена команда: " + command);

            // Эмулируем задержку ответа
            PauseTransition delay = new PauseTransition(Duration.seconds(0.2));
            delay.setOnFinished(e -> Platform.runLater(() -> {
                terminal.sendResponse("Ответ устройства: " + command.toUpperCase());
            }));
            delay.play();
        });

        VBox root = new VBox(terminal);
        primaryStage.setScene(new Scene(root, 600, 200));
        primaryStage.setTitle("WebView Terminal");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

