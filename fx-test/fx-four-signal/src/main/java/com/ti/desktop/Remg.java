package com.ti.desktop;

import com.ti.PropertiesService;
import com.ti.remg.RemgSignalCommand;
import com.ti.remg.RemgSignalManager;
import com.ti.remg.RemgSignalType;
import com.ti.remg.RemgViewController;
import com.ti.serial.checkers.SawProtocolParser;
import com.ti.serial.command.SingleSignalCommand;
import com.ti.serial.command.SingleSignalType;
import com.ti.serial.device.FileByteProducer;
import com.ti.serial.implem.SawUnit;
import com.ti.signals.AdvanceSignalBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Remg extends Application {
    public static final String SCENE_XML = "remg.fxml";
    private static final String TEST_FILE = "2025-02-01 01-05-52 095 Briko-MAX-12.bin";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws URISyntaxException {
        PropertiesService.setGlobalPropertyFileName(Remg.class.getSimpleName());
        String prop = PropertiesService.getGlobalProperty("prop");
        System.out.println(prop);

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(SCENE_XML));
        BorderPane root = null;
        try {
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root,600,600);
        primaryStage.setScene(scene);
        primaryStage.show();

        RemgSignalManager signalManager = new RemgSignalManager();
        AdvanceSignalBox<RemgSignalType> box = signalManager.getBox();

        RemgViewController signalViewController = loader.getController();
        signalManager.linkBoxToView(signalViewController);

        SawProtocolParser parser = new SawProtocolParser(151);
        SawUnit<RemgSignalCommand> unit = new SawUnit<>(parser,parser, RemgSignalCommand::new);
        unit.setBox(box);

//        Path file = Paths.get(this.getClass().getResource(TEST_FILE).toURI());
        Path file = Paths.get(Objects.requireNonNull(FileByteProducerTest.class.getResource(TEST_FILE)).toURI());
        FileByteProducer device = new FileByteProducer(file, FileByteProducer.ReadType.READ_CONTINIOUS);
//        ComPortWorker device = new ComPortWorker("COM10", 115200*4);
//         TODO: 21.01.2018 remove after full migrate to commlite
        device.liteProtocol = true;
        device.setProtocol(unit);
        device.readWithDelay(50, 1000);

//        ByteUnit byteUnit = new ByteUnit();
//        byteUnit.setDevice(device2);
        System.setProperty("javafx.pulseLogger", "true");
    }

    @Override
    public void stop() throws Exception{
        super.stop();
        System.exit(0);
    }
}
