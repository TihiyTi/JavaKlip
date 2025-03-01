package com.ti.remg;

import com.ti.dspview.TerminalWebView;
import com.ti.serial.def.controller.ByteController;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RemgControlController implements Initializable {
    @FXML
    public BorderPane border;

    @FXML
    public TerminalWebView terminal;
    public ComboBox chooseChannel;
    @FXML
    public ListView<String> listView;
    public Button ping;

    private ByteController byteController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        // Добавляем слушатель к списку выбранных элементов
//        listView.addEventHandler((obs, oldSelection, newSelection) -> {
//            ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();
//            System.out.println("Выбрано: " + selectedItems);
//        });
        terminal.setCommandHandler(command -> {
//            System.out.println("Отправлена команда: " + command);
            byteController.send(command.getBytes());
            // Эмулируем задержку ответа
            PauseTransition delay = new PauseTransition(Duration.seconds(0.5));

            delay.setOnFinished(e -> Platform.runLater(() -> {
                List<Byte> byteList = new ArrayList<>();
                byteController.testQueue.drainTo(byteList);
                System.out.println("Длина после drain: "+byteController.testQueue.size());
                String result = byteList.stream()
                        .map(b -> String.valueOf((char) (b.byteValue()))) // Byte → char → String
                        .collect(Collectors.joining());
                System.out.println("Ответ устройства: " + result);
                terminal.sendResponse(result);

            }));
            delay.play();
        });
    }

    public void setByteController(ByteController byteController) {
        this.byteController = byteController;
    }

    public void handleOrder(ActionEvent actionEvent) {

//        byteController.send();
    }

    public void handlePing(ActionEvent actionEvent) {
//        pingChannel(0);
        pingAllChannel(0);
    }
    private void pingChannel(int channel) {
        terminal.typeAndSendEmulate("d "+ channel + " ver");
    }

    private void pingAllChannel(int ch){
        if (ch == 12) {
            return;
        }
        PauseTransition pause = new PauseTransition(Duration.millis(600));
        pause.setOnFinished(event -> pingAllChannel(ch+1));
        pause.play();
        terminal.typeAndSendEmulate("d "+ ch + " ver");
    }
}
