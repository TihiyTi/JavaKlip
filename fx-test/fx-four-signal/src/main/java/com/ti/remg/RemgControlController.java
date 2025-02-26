package com.ti.remg;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;

import java.net.URL;
import java.util.ResourceBundle;

public class RemgControlController implements Initializable {
    @FXML
    public CodeArea terminal;
    private int areaBackWallPosition; // Граница редактируемой области
    private int promptPosition; // Граница редактируемой области


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        terminal.appendText("Welcome to REMG terminal!\n> ");
        promptPosition = terminal.getLength();
        terminal.moveTo(promptPosition);

        terminal.setOnKeyPressed(this::handleKeyPress);
        terminal.setOnKeyTyped(this::handleKeyTyped);
        terminal.setOnKeyReleased(this::handleKeyReleased);
    }

    private void handleKeyReleased(KeyEvent keyEvent) {
        System.out.println("Released: "+terminal.getCaretPosition());
        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            System.out.println("Released BackSpase: "+terminal.getCaretPosition());
            keyEvent.consume(); // Предотвращаем любые изменения перед границей
//            terminal.moveTo(areaBackWallPosition);
        }

    }

    private void handleKeyPress(KeyEvent event) {
        System.out.println("Press: "+terminal.getCaretPosition());
        if(event.getCode() == KeyCode.ENTER){
            areaBackWallPosition = terminal.getCaretPosition();
        }else if(/*event.getCode() == KeyCode.LEFT || */event.getCode() == KeyCode.BACK_SPACE){
            System.out.println("Press BackSpase: "+terminal.getCaretPosition());
            event.consume();
//            if (terminal.getCaretPosition() <= areaBackWallPosition) {
//                terminal.moveTo(areaBackWallPosition);
//                System.out.println("Возврат к :" + areaBackWallPosition);
//            }
        }
    }
    private void handleKeyTyped(KeyEvent event) {
        System.out.println("Typed: "+terminal.getCaretPosition());
        if (event.getCode() == KeyCode.BACK_SPACE) {
            System.out.println("Typed BackSpase: "+terminal.getCaretPosition());
            event.consume(); // Предотвращаем любые изменения перед границей
//            terminal.moveTo(areaBackWallPosition);
        }
    }

//    private void handleKeyTyped(KeyEvent event) {
//         Запрещаем вставку текста перед границей
//        if (terminal.getCaretPosition() < promptPosition) {
//            event.consume();
//            terminal.moveTo(promptPosition);
//        }
//    }

    private void processCommand() {
        // Безопасное извлечение текста, предотвращающее ошибки с границами
//        if (terminal.getLength() > promptPosition) {
//            String command = terminal.getText(promptPosition, terminal.getLength()).trim();
//            executeCommand(command);
//        }

        // Добавляем новый промпт и запрещаем редактирование старых строк
//        terminal.appendText("\n> ");
//        promptPosition = terminal.getLength();
//        terminal.moveTo(promptPosition);
    }

    private void executeCommand(String command) {
        if (command.equalsIgnoreCase("clear")) {
            terminal.clear();
            terminal.appendText("Welcome to REMG terminal!\n> ");
            promptPosition = terminal.getLength();
        } else {
            terminal.appendText("\nExecuted: " + command);
        }
    }
}
