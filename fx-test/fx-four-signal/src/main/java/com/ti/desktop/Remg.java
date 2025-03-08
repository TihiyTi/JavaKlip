package com.ti.desktop;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.status.Status;
import com.ti.PropertiesService;
import com.ti.remg.*;
import com.ti.serial.common.device.FileByteProducer;
import com.ti.serial.lite.implem.SawObjectUnit;
import com.ti.serial.lite.protocol.SawProtocolParser;
import com.ti.serial.common.device.SerialPortDevice;
import com.ti.serial.def.controller.ByteController;
import com.ti.serial.def.protocol.ByteFlowProtocol;
import com.ti.serial.lite.implem.SawUnit;
import com.ti.serial.def.SerialService;
import com.ti.signals.AdvanceSignalBox;
import com.ti.signals.SignalConsumer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class Remg extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Remg.class);

    public static final String SCENE_XML = "remg.fxml";
    private static final String TEST_FILE = "2025-02-01 01-05-52 095 Briko-MAX-12.bin";

    public static void main(String[] args) {
        String configFile = System.getProperty("logback.configurationFile");
        var modules = ModuleLayer.boot()
                .modules()
                .stream()
                .map(Module::getName)
                .sorted()
                .collect(Collectors.joining("\n   ", "\n   ", ""));
        System.out.println(modules);
        System.out.println("!!" + configFile);

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        for (Status status : context.getStatusManager().getCopyOfStatusList()) {
            System.out.println(status);
        }

//        System.out.println("---- DEBUG SLF4J PROVIDER LOADING ----");
//        System.out.println("Current Module: " + Remg.class.getModule());
//        System.out.println("Module Layer Modules:");
//        ModuleLayer.boot()
//                .modules()
//                .forEach(m -> System.out.println(" - " + m.getName()));
//
//        System.out.println("Looking for SLF4JServiceProvider using ServiceLoader:");
//        ServiceLoader<org.slf4j.spi.SLF4JServiceProvider> loader =
//                ServiceLoader.load(org.slf4j.spi.SLF4JServiceProvider.class);
//        boolean found = false;
//        for (var provider : loader) {
//            System.out.println("Found provider: " + provider.getClass().getName());
//            found = true;
//        }
//        if (!found) {
//            System.out.println("NO SLF4J PROVIDERS FOUND!");
//        }
//        System.out.println("-------------------------------------");


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

        logger.info("Stage show");
        String configFile = System.getProperty("logback.configurationFile");

        System.out.println("!!" + configFile);

        RemgSignalManager signalManager = new RemgSignalManager();
        AdvanceSignalBox<RemgSignalType> box = signalManager.getBox();

        RemgViewController signalViewController = loader.getController();
        signalManager.linkBoxToView(signalViewController);

        RemgSaveController saveController = new RemgSaveController();
        signalViewController.setSaveController(saveController);

        SawProtocolParser parser = new SawProtocolParser(151);
        SawObjectUnit<RemgSignalType, RemgSignalCommand> unit = new SawObjectUnit<RemgSignalType, RemgSignalCommand>(parser,parser, RemgSignalCommand::new, saveController);
        unit.setBox(box);

        logger.info("Stage show 2");

////        Path file = Paths.get(this.getClass().getResource(TEST_FILE).toURI());
//        Path file = Paths.get(Objects.requireNonNull(FileByteProducerTest.class.getResource(TEST_FILE)).toURI());
//        FileByteProducer device = new FileByteProducer(file, FileByteProducer.ReadType.READ_CONTINIOUS);
//        SerialPortDevice device = new SerialPortDevice("COM4", 921600);
////         TODO: 21.01.2018 remove after full migrate to commlite
//        device.liteProtocol = true;
//        device.setProtocol(unit);
//        device.readWithDelay(50, 1000);

        String terminalComName = PropertiesService.getGlobalProperty("terminalComName");
        SerialPortDevice terminal = new SerialPortDevice("COM5", 921600);
        ByteFlowProtocol protocol = new ByteFlowProtocol();
        ByteController controller = new ByteController();
        SerialService<ByteBuffer,ByteBuffer> service = new SerialService<>(terminal);
        service.setProtocol(protocol);
        service.addController(controller);
        RemgControlController remgControlController = signalViewController.getRemgControlController();
        remgControlController.setByteController(controller);


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
