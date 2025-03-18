package com.ti.remg;

import com.ti.PropertiesService;
import com.ti.dspview.TerminalWebView;
import com.ti.examples.ChooseFilterController;
import com.ti.examples.PushCommandInterface;
import com.ti.serial.def.controller.ByteController;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class RemgControlController implements Initializable, PushCommandInterface {
    @FXML
    public BorderPane border;

    @FXML
    public TerminalWebView terminal;
    public ComboBox chooseChannel;
//    @FXML
//    public ListView<String> listView;
    public Button ping;

    public GridPane commandChoosePanel;
    public ChooseFilterController commandChoosePanelController;

    private ByteController byteController;

    Map<String, String> map;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

//        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        String prop = PropertiesService.getGlobalProperty("commandFileName");
        PropertiesService service = new PropertiesService();
        service.setName(prop);
        map = service.getAllProperties();
        List<String> keys = map.entrySet().stream().map(e->e.getKey()+ " | " +e.getValue()).toList();
//        List<String> keys =  map.keySet().stream().toList();
        System.out.println(map);
        commandChoosePanel.prefHeight(150);
        commandChoosePanelController.setPrintVariants(keys);
        commandChoosePanelController.initPane(this);

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

    @Override
    public void pushCommand(String nameAndCommand) {
        String command = map.get(nameAndCommand.split("\\|")[0].trim());
        if(command.contains("|")){
            List<String> commandList = Arrays.stream(command.split("\\|")).toList();
            System.out.println( "Add command list: " + commandList);
            sendAllCommand(0, commandList);
        }else {
            System.out.println( "Add command: " + command);
            terminal.typeAndSendEmulate(command);
        }
    }


    private void sendAllCommand(int commandIndex, List<String> commands) {
        if (commandIndex == commands.size()) {
            return;
        }
        PauseTransition pause = new PauseTransition(Duration.millis(600));
        pause.setOnFinished(event -> sendAllCommand(commandIndex+1, commands));
        pause.play();
        terminal.typeAndSendEmulate(commands.get(commandIndex));
    }
}
