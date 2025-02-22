package com.ti.desktop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
//import org.fxmisc.richtext.CodeArea;

public class RichTextTerminal /*extends Application*/ {
//
//    @Override
//    public void start(Stage primaryStage) {
//        CodeArea terminal = new CodeArea();
//        terminal.setWrapText(true);
//        terminal.appendText("Welcome to RichTextFX Terminal!\n> ");
//
//        terminal.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                event.consume(); // Предотвращаем перенос строки
//                handleInput(terminal);
//            }
//        });
//
//        VBox root = new VBox(terminal);
//        Scene scene = new Scene(root, 600, 400);
//        primaryStage.setTitle("RichTextFX Terminal");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    private void handleInput(CodeArea terminal) {
//        String text = terminal.getText();
//        int lastNewline = text.lastIndexOf("\n");
//
//        if (lastNewline == -1) return;
//
//        String command = text.substring(lastNewline + 2).trim(); // Убираем '> '
//        if (command.isEmpty()) return;
//
//        System.out.println("Command: " + command); // Вывод в консоль
//
//        String reversed = new StringBuilder(command).reverse().toString();
//
//        terminal.appendText("\n" + reversed + "\n> ");
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
}

